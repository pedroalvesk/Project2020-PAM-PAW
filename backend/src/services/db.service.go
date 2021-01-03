package services

import (
	"api/model"
	"fmt"

	"github.com/jinzhu/gorm"
)

const username = "test"
const password = "passw0rd"
const dbHost = "database"
const dbPort = "5432"
const dbName = "apidb"

var Db *gorm.DB

func OpenDatabase() {

	var err error

	fmt.Print(1)
	Db, err = gorm.Open("postgres", "postgres://"+username+":"+password+"@"+dbHost+":"+dbPort+"/"+dbName+"?sslmode=disable")
	if err != nil {
		panic("failed to connect database")
	}

}

func CreateMockupData(){

	users := []model.User {
		{Username: "admin", Password: "password"},
		{Username: "ines", Password: "password"},
		{Username: "pedro", Password: "password"},
	}

	invoices := []model.Invoice {
		{Filename: "Invoice 1", Extension: ".png"},
		{Filename: "Invoice 2", Extension: ".png"},
		{Filename: "Invoice 3", Extension: ".png"},
		{Filename: "Invoice 4", Extension: ".png"},
		{Filename: "Invoice 5", Extension: ".png"},

	}


	OpenDatabase()

	Db.Save(users[0])
	Db.Save(users[1])
	Db.Save(users[2])

	Db.Model(&users[0]).Association("Invoices").Append(invoices[0])
	Db.Model(&users[1]).Association("Invoices").Append(invoices[1])
	Db.Model(&users[1]).Association("Invoices").Append(invoices[2])
	Db.Model(&users[2]).Association("Invoices").Append(invoices[3])
	Db.Model(&users[2]).Association("Invoices").Append(invoices[4])

	defer Db.Close()
}