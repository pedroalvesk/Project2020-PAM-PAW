package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"

	"github.com/otiai10/gosseract"
)

func main() {

	if len(os.Args) != 2 {
		fmt.Fprintf(os.Stderr, "usage: %s [image path]\n", os.Args[0])
		return
	}

	client := gosseract.NewClient()
	defer client.Close()
	client.Languages = []string{"eng"} // por
	client.SetImage(os.Args[1])
	text, _ := client.Text()
	fmt.Println()
	fmt.Println(text)
	fmt.Println()

	err := ioutil.WriteFile(os.Args[1]+".txt", []byte(text), 0777)
	if err != nil {
		log.Fatal(err)
	}
}
