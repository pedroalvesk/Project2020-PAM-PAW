package controllers

import (
	"api/model"
	"api/services"
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"net/http"
	"path/filepath"
)

func GetAllData(c *gin.Context) {
	services.OpenDatabase()

	//////////////////////////////
	// Get all users
	var users []model.User
	services.Db.Find(&users)

	// Get 'Invoices' for each user
	for i, user := range users {
		services.Db.Model(&user).Association("Invoices").Find(&users[i].Invoices)
	}

	c.JSON(http.StatusOK, gin.H{"users": users})
	defer services.Db.Close()
}

func GetAllUsers(c *gin.Context) {
	services.OpenDatabase()

	//////////////////////////////
	// Get all users
	var users []model.User
	services.Db.Find(&users)

	c.JSON(http.StatusOK, gin.H{"users": users})
	defer services.Db.Close()
}

func GetAllInvoices(c *gin.Context) {
	services.OpenDatabase()

	//////////////////////////////
	// Get all invoices
	var invoices []model.Invoice
	services.Db.Find(&invoices)

	c.JSON(http.StatusOK, gin.H{"invoices": invoices})
	defer services.Db.Close()
}

func UpdateUser(c *gin.Context) {
	services.OpenDatabase()

	// Find user
	var user model.User
	id := c.Param("id")

	services.Db.Find(&user, id)
	if user.ID == 0 {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Invalid user id!"})
		return
	}

	// Update data
	var newData model.User
	err := c.BindJSON(&newData)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Invalid body data!"})
		return
	}

	fmt.Println(user);
	fmt.Println(newData);


	// Save
	user.Username = newData.Username
	user.Password = newData.Password
	services.Db.Save(&user)
	c.JSON(http.StatusOK, gin.H{"user": user})
	defer services.Db.Close()
}

func DeleteUser(c *gin.Context) {
	services.OpenDatabase()

	// Find user
	var user model.User
	id := c.Param("id")

	services.Db.Find(&user, id)
	if user.ID == 0 {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Invalid user id!"})
		return
	}

	services.Db.Delete(&user)
	c.JSON(http.StatusOK, gin.H{"message": "success"})
	defer services.Db.Close()
}

func CreateInvoice(c* gin.Context) {
	services.OpenDatabase()

	//////////////////////////////
	// Find user
	var user model.User
	id := c.Param("id")

	services.Db.Find(&user, id)
	if user.ID == 0 {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Invalid user id!"})
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
	basePath := "/userfiles/"
	newFileName := uuid.New().String() + extension

	if err := c.SaveUploadedFile(file, basePath+newFileName); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"message": "Unable to save the file"})
		return
	}

	//////////////////////////////
	// Create a new invoice on user
	invoice := model.Invoice{
		Path:      basePath + newFileName,
		Filename:  newFileName,
		Extension: extension,
	}

	// Save
	services.Db.Model(&user).Association("Invoices").Append(&invoice)
	defer services.Db.Close()

	//////////////////////////////
	// toJson
	inv, _ := json.Marshal(invoice)

	//////////////////////////////
	// RabbitMQ
	services.RabbitMQConnect()
	services.RabbitMQSend(string(inv))
	services.RabbitMQClose()


	c.JSON(http.StatusOK, gin.H{"message": invoice})
}

func UpdateInvoice(c *gin.Context) {

}

func DeleteInvoice(c *gin.Context) {
	services.OpenDatabase()

	// Find user
	var invoice model.Invoice
	id := c.Param("id")

	services.Db.Find(&invoice, id)
	if invoice.ID == 0 {
		c.JSON(http.StatusBadRequest, gin.H{"message": "Invalid invoice id!"})
		return
	}

	services.Db.Delete(invoice)
	c.JSON(http.StatusOK, gin.H{"message": "success"})
	defer services.Db.Close()
}

