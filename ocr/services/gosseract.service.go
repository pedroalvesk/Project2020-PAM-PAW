package services

import (
	"encoding/json"
	"github.com/otiai10/gosseract"
	"github.com/streadway/amqp"
	"log"
	"ocr/models"
	"ocr/services"
)

func DoWork(msg amqp.Delivery) {
	log.Printf("#####################")
	log.Printf("Received a new invoice: %s", msg.Body)
	log.Printf("#####################")

	var invoice models.Invoice
	err := json.Unmarshal(msg.Body, &invoice)
	failOnError(err, "Invalid unmarshal invoice")

	client := gosseract.NewClient()
	err = client.SetPageSegMode(gosseract.PSM_AUTO_OSD)
	client.Languages = []string{"por"} // por
	failOnError(err, "Setting PageSegMod to: PSM_AUTO_OSD")
	defer client.Close()

	// Set image
	err = client.SetImage(invoice.Path)
	failOnError(err, "Invalid image")

	// Full Text
	text, _ := client.Text()

	// Invoice New
	invoice.Processed = true
	invoice.Type = services.GetInvoiceType(text)
	invoice.FullText = text

	// Tell back
	services.SendToServer(invoice)
}

