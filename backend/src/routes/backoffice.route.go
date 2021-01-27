package routes

import (
	"api/controllers"
	"github.com/gin-gonic/gin"
)

func GetAllData(c *gin.Context) {
	controllers.GetAllData(c)
}

/*
 *	USERS
 */
func GetAllUsers(c *gin.Context) {
	controllers.GetAllUsers(c)
}

func UpdateUser(c *gin.Context) {
	controllers.UpdateUser(c)
}

func DeleteUser(c *gin.Context) {
	controllers.DeleteUser(c)
}

/*
 *	INVOICES
 */
func GetAllInvoices(c *gin.Context) {
	controllers.GetAllInvoices(c)
}

func CreateInvoice(c *gin.Context) {
	controllers.CreateInvoice(c)
}

func UpdateInvoice(c *gin.Context) {
	controllers.UpdateInvoice(c)
}

func DeleteInvoice(c *gin.Context) {
	controllers.DeleteInvoice(c)
}