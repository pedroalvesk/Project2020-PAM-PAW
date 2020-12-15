package model

import "time"

type User struct {
	ID        uint       `json:"id" swaggerignore:"true" gorm:"primary_key"`
	CreatedAt time.Time  `json:"-" swaggerignore:"true"`
	UpdatedAt time.Time  `json:"-" swaggerignore:"true"`
	DeletedAt *time.Time `json:"-" swaggerignore:"true" sql:"index"`

	Username string `json:"username"`
	Password string `json:"password"`

	Invoices []Invoice `json:"-" gorm:"foreignkey:UserID"`
}

/*

Db.Model(&user).Related(&invoices)

var user User
db.Model(&user).Association("Invoices")
db.Model(&user).Association("Invoices").Find
db.Model(&user).Association("Invoices").Append
db.Model(&user).Association("Invoices").Clear
db.Model(&user).Association("Invoices").Delete

*/
