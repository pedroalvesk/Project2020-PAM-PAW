package main

import (
	"ocr/models"
	"log"
	"regexp"
	"time"

	"github.com/otiai10/gosseract"
	"github.com/streadway/amqp"
)

func main() {
	queueName := "NomeDaQueueAqui"

	//////////////////////////////
	// RabbitMQ

	// Wait for the rabbitmq service to start
	time.Sleep(20 * time.Second)

	// Connect to RabbitMQ
	conn, err := amqp.Dial("amqp://guest:guest@rabbitmq:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	// Open a channel
	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	// Declare Queue
	q, err := ch.QueueDeclare(
		queueName, // name
		false,     // durable
		false,     // delete when unused
		false,     // exclusive
		false,     // no-wait
		nil,       // arguments
	)
	failOnError(err, "Failed to declare a queue")

	// Messages consumed
	msgs, err := ch.Consume(
		q.Name, // queue
		"",     // consumer
		true,   // auto-ack
		false,  // exclusive
		false,  // no-local
		false,  // no-wait
		nil,    // args
	)
	failOnError(err, "Failed to register a consumer")

	//////////////////////////////
	// Infinite loop waiting for messages
	forever := make(chan bool)

	go func() {
		for d := range msgs {
			doWork(d)
		}
	}()
	log.Printf(" [*] Waiting for messages. To exit press CTRL+C")

	<-forever
}

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

func doWork(msg amqp.Delivery) {
	log.Printf("#####################")
	log.Printf("Received a new invoice: %s", msg.Body)
	log.Printf("#####################")

	client := gosseract.NewClient()
	client.Languages = []string{"por"}
	err := client.SetPageSegMode(gosseract.PSM_AUTO_OSD)
	failOnError(err, "Gosseract failed setting PageSegMode: AUTO_OSD")
	defer client.Close()

	// TODO: edit this
	// client.SetImage(string(msg.Body))
	client.SetImage()

	// Full Text
	text, _ := client.Text()

	// Invoice New
	var invoice models.Invoice
	invoice.Processed = true
	invoice.Type = getInvoiceType(text)
	invoice.FullText = text

	// Tell back
	
}

func invoiceIsMeo(text string) (isMeo bool) {
	return invoiceIs("meo", text)
}

func invoiceIsNos(text string) (isMeo bool) {
	return invoiceIs("NOS", text)
}

func invoiceIsEdp(text string) (isMeo bool) {
	return invoiceIs("edp", text)
}

func invoiceIs(typeToCheck string, text string) (isType bool) {

	var re *regexp.Regexp
	if typeToCheck == "NOS" {
		re = regexp.MustCompile(typeToCheck + "(.pt)?")
	} else {

		re = regexp.MustCompile("(?i)" + typeToCheck + "(.pt)?")
	}

	found := re.FindAllString(text, -1)

	//  fmt.Printf("%q\n", found)

	if found == nil {
		return false
	}

	return true
}

func getInvoiceType(text string) (invoiceType string) {
	if invoiceIsEdp(text) {
		return "EDP"
	}

	if invoiceIsMeo(text) {
		return "MEO"
	}

	if invoiceIsNos(text) {
		return "NOS"
	}

	return "GENERIC"
}
