package routes

import (
	"api/controllers"
	"github.com/gin-gonic/gin"
)

func GetAllData(c *gin.Context) {
	controllers.GetAllData(c)
}

func GetAllUsers(c *gin.Context) {
	controllers.GetAllUsers(c)
}

func GetAllInvoices(c *gin.Context) {
	controllers.GetAllInvoices(c)
}