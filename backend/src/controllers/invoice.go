package controllers

import (
	"api/model"
	"api/services"
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
	if user == nil {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Error with JWT (user)"})
		return
	}

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

	if err := c.SaveUploadedFile(file, "path/on/server/"+newFileName); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"message": "Unable to save the file"})
		return
	}

	//////////////////////////////
	// Create a new invoice on user
	invoice := model.Invoice{}
	invoice.Filename = newFileName

	services.Db.Model(&user).Association("Invoices").Append(invoice)
	services.Db.Save(user)

	c.JSON(http.StatusOK, gin.H{"message": "Success"})
}

// GetAllInvoices function
func GetAllInvoices(c *gin.Context) {

	//////////////////////////////
	// Get user from token
	user := getUserFromToken(c)
	if user == nil {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Error with JWT (user)"})
		return
	}

	//////////////////////////////
	// Get user invoices
	var invoices []model.Invoice
	services.Db.Model(&user).Association("Invoices").Find(&invoices)

	c.JSON(http.StatusOK, gin.H{"message": invoices})
}

// GetInvoiceByID function
func GetInvoiceByID(c *gin.Context) {
}

// UpdateInvoiceByID function
func UpdateInvoiceByID(c *gin.Context) {
}

// DeleteInvoiceByID function
func DeleteInvoiceByID(c *gin.Context) {
}

//
func getUserFromToken(c *gin.Context) *model.User {
	var user *model.User

	// "username" variable in JWT
	uname, exists := c.Get("username")
	if exists == false {
		return nil
	}

	// user in database with that username
	services.Db.Where("username = ?", uname).First(&user)
	if user.ID == 0 {
		return nil
	}

	return user
}
