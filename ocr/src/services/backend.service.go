package services

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"ocr/models"
	"strings"
)

//TODO: add autorization token
func SendToServer(inv models.Invoice) {
	url := "http://backend:8090/api/v1/ocr/"
	method := "POST"

	byteArray, err := json.Marshal(inv)
	if err != nil {
		panic(err.Error())
		return
	}
	
	payload := strings.NewReader(string(byteArray))

	client := &http.Client{}
	req, err := http.NewRequest(method, url, payload)

	if err != nil {
		fmt.Println(err)
		return
	}
	req.Header.Add("Content-Type", "application/json")

	res, err := client.Do(req)
	if err != nil {
		fmt.Println(err)
		return
	}
	defer res.Body.Close()

	body, err := ioutil.ReadAll(res.Body)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(body))
}
