package controllers

import (
	"api/model"
	"api/services"
	"github.com/gin-gonic/gin"
	"net/http"
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
