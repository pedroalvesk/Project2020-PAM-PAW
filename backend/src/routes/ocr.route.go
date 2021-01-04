package routes

import (
	"api/controllers"
	"github.com/gin-gonic/gin"
)

// UpdateInvoice function
func UpdateInvoiceByOCR(c *gin.Context) {
	controllers.UpdateInvoiceByOCR(c)
}