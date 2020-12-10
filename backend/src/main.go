package main

import "github.com/gin-gonic/gin"

func main() {
	r := gin.Default()      // Default config that already includes Logger and Recovery
	gin.ForceConsoleColor() // Enable color output

	r.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})
	r.Run(":8090")
}
