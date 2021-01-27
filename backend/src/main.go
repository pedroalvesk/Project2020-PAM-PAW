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

func init() {
	services.OpenDatabase()

	// Drop tables
	//services.Db.DropTableIfExists(&model.User{})
	//services.Db.DropTableIfExists(&model.Invoice{})

	// Create tables
	services.Db.AutoMigrate(&model.User{})
	services.Db.AutoMigrate(&model.Invoice{})
	services.CreateMockupData()

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
	invoices := router.Group("/api/v1/invoices")
	invoices.Use(services.AuthorizationRequired())
	{
		invoices.POST("/", routes.CreateInvoiceUser)
		invoices.GET("/", routes.GetUserInvoices)
		invoices.GET("/:id", routes.GetInvoiceByID)
		invoices.PUT("/:id", routes.UpdateInvoiceByID)
		invoices.DELETE("/:id", routes.DeleteInvoiceByID)
	}

	// Backoffice
	backoffice := router.Group("/api/v1/backoffice")
	{
		backoffice.GET("/all", routes.GetAllData)
		backoffice.GET("/users", routes.GetAllUsers)
		backoffice.PUT("/users/:id", routes.UpdateUser)
		backoffice.DELETE("/users/:id", routes.DeleteUser)

		backoffice.GET("/invoices", routes.GetAllInvoices)
		backoffice.POST("/invoices/:id", routes.CreateInvoice)
		backoffice.PUT("/invoices/:id", routes.UpdateInvoice)
		backoffice.DELETE("/invoices/:id", routes.DeleteInvoice)

	}

	// OCR
	router.POST("/api/v1/ocr", routes.UpdateInvoiceByOCR)

	// Swagger
	router.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))
}

func main() {
	router := gin.Default()
	router.Use(services.GinMiddleware("*"))
	initialiseRoutes(router)
	_ = router.Run(":8090")
}
