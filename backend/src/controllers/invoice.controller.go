package controllers

import (
	"api/model"
	"api/services"
	"fmt"
	"net/http"
	"path/filepath"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
)

// CreateInvoice function
func CreateInvoice(c *gin.Context) {

	//////////////////////////////
	// Get user from token
	user := getUserFromToken(c)
	//userID, _ := c.Get("userID")

	//////////////////////////////
	// Handle the file
	file, err := c.FormFile("file")
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"message": "File not found"})
		return
	}

	// Retrieve file information
	extension := filepath.Ext(file.Filename)

	// Generate random filename
	newFileName := uuid.New().String() + extension

	if err := c.SaveUploadedFile(file, "/userfiles/"+newFileName); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"message": "Unable to save the file"})
		return
	}

	//////////////////////////////
	// Create a new invoice on user
	invoice := model.Invoice{
		Filename:  newFileName,
		Extension: extension,
	}

	services.OpenDatabase()
	services.Db.Model(&user).Association("Invoices").Append(invoice)
	defer services.Db.Close()

	//////////////////////////////
	// RabbitMQ
	services.RabbitMQConnect()
	services.RabbitMQSend("/userfiles/" + newFileName)
	services.RabbitMQClose()

	c.JSON(http.StatusOK, gin.H{"message": invoice})
}

// GetUserInvoices function
func GetUserInvoices(c *gin.Context) {

	//////////////////////////////
	// Get user from token
	user := getUserFromToken(c)
	if user.ID == 0 {
		return
	}

	//////////////////////////////
	// Get user invoices
	var invoices []model.Invoice

	services.OpenDatabase()
	services.Db.Model(&user).Association("Invoices").Find(&invoices)
	defer services.Db.Close()

	c.JSON(http.StatusOK, gin.H{"message": invoices})
}

// GetInvoiceByID function
func GetInvoiceByID(c *gin.Context) {

	//////////////////////////////
	// Get invoice id
	invoiceID := c.Param("id")

	//////////////////////////////
	// Get user from token
	user := getUserFromToken(c)

	//////////////////////////////
	// Validate user invoice
	var invoice model.Invoice
	services.Db.Find(&invoice, invoiceID)

	if invoice.UserID != user.ID {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Invalid invoice id!"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": invoice})
}

// UpdateInvoiceByID function
func UpdateInvoiceByID(c *gin.Context) {
}

// DeleteInvoiceByID function
func DeleteInvoiceByID(c *gin.Context) {

	//////////////////////////////
	// Get invoice id
	invoiceID := c.Param("id")

	//////////////////////////////
	// Get user from token
	user := getUserFromToken(c)

	//////////////////////////////
	// Validate user invoice
	var invoice model.Invoice
	services.Db.Find(&invoice, invoiceID)

	if invoice.UserID != user.ID {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Invalid invoice id!"})
		return
	}

	services.Db.Delete(&invoice)
	c.JSON(http.StatusOK, gin.H{"message": "Success"})
}

////////////////////////////////////////////////
// Helper functions
////////////////////////////////////////////////

// Gets the username from JWT token
func getUserFromToken(c *gin.Context) model.User {
	var user model.User

	// "username" variable in JWT
	uname, exists := c.Get("username")
	if exists == false {
		fmt.Println("Username not found on token!")
		return user
	}

	// user in database with that username
	services.Db.Where("username = ?", uname).Find(&user)

	return user
}
