package main

import (
	"fmt"

	"github.com/otiai10/gosseract"
)

func main() {
	client := gosseract.NewClient()
	defer client.Close()
	client.SetImage("image.png")
	text, _ := client.Text()
	fmt.Println()
	fmt.Println(text)
	fmt.Println()
	// Hello, World!
}
