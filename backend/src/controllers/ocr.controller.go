package controllers

import (
	"api/model"
	"api/services"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

func UpdateInvoiceByOCR(c *gin.Context) {
	services.OpenDatabase()

	//////////////////////////////
	// Get received invoice
	var receivedInvoice model.Invoice
	err := c.BindJSON(&receivedInvoice)
	if err != nil {
		panic(err.Error())
	}

	fmt.Println(receivedInvoice)

	//////////////////////////////
	// Get saved invoice
	var databaseInvoice model.Invoice
	services.Db.Find(&databaseInvoice, receivedInvoice.ID)
	if databaseInvoice.ID == 0 {
		panic("OCR send invalid invoice")
	}

	//////////////////////////////
	// Update invoice
	databaseInvoice.Processed = true
	databaseInvoice.Data = receivedInvoice.Data
	databaseInvoice.Type = receivedInvoice.Type
	databaseInvoice.FullText = receivedInvoice.FullText

	services.Db.Save(receivedInvoice)

	c.JSON(http.StatusOK, gin.H{"message": "success"})
	defer services.Db.Close()
}
