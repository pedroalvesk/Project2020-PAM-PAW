package main

import (
	"api/model"
	"api/routes"
	"api/services"

	"github.com/gin-gonic/gin"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

var identityKey = "id"

func init() {

	services.OpenDatabase()
	services.Db.AutoMigrate(&model.User{})

	defer services.Db.Close()
}

func initialiseRoutes(router *gin.Engine) {

	// Auth
	auth := router.Group("/api/v1/auth")
	{
		auth.POST("/login", routes.GenerateToken)
		auth.POST("/register", routes.RegisterUser)
		auth.PUT("/refresh_token", services.AuthorizationRequired(), routes.RefreshToken)
	}

	// Invoices
	invoices := router.Group("/api/v1")
	invoices.Use(services.AuthorizationRequired())
	{
		invoices.POST("/invoices", routes.CreateInvoice)
		invoices.GET("/invoices", routes.GetAllInvoices)
		invoices.GET("/invoices/:id", routes.GetInvoiceByID)
		invoices.PUT("/invoices/:id", routes.UpdateInvoiceByID)
		invoices.DELETE("/invoices/:id", routes.DeleteInvoiceByID)
	}

	// Swagger
	router.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))
}

func main() {
	//services.FormatSwagger()

	router := gin.New()
	router.Use(gin.Logger())
	router.Use(gin.Recovery())

	initialiseRoutes(router)

	router.Run(":8090")
}
