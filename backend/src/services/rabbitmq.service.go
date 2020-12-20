package services

import (
	"log"

	"github.com/streadway/amqp"
)

var queueName string = "NomeDaQueueAqui"
var conn *amqp.Connection
var queue *amqp.Queue
var channel *amqp.Channel

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

func RabbitMQConnect() {
	queueName := "NomeDaQueueAqui"

	var errConn error
	conn, errConn = amqp.Dial("amqp://guest:guest@rabbitmq:5672/")
	failOnError(errConn, "Failed to connect to RabbitMQ")

	var errChannel error
	channel, errChannel = conn.Channel()
	failOnError(errChannel, "Failed to open a channel")

	q, err := channel.QueueDeclare(
		queueName, // name
		false,     // durable
		false,     // delete when unused
		false,     // exclusive
		false,     // no-wait
		nil,       // arguments
	)
	failOnError(err, "Failed to declare a queue:"+q.Name)
}

func RabbitMQSend(body string) {
	err := channel.Publish(
		"",        // exchange
		queueName, // routing key
		false,     // mandatory
		false,     // immediate
		amqp.Publishing{
			ContentType: "text/plain",
			Body:        []byte(body),
		})

	log.Printf(" [x] Sent %s", body)
	failOnError(err, "Failed to publish a message")
}

func RabbitMQClose() {
	defer conn.Close()
	defer channel.Close()
}
