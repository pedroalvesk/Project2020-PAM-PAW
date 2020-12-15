package model

import "time"

type Invoice struct {
	ID        uint       `json:"id" swaggerignore:"true" gorm:"primary_key"`
	CreatedAt time.Time  `json:"-" swaggerignore:"true"`
	UpdatedAt time.Time  `json:"-" swaggerignore:"true"`
	DeletedAt *time.Time `json:"-" swaggerignore:"true" sql:"index"`

	UserID   uint   `json:"userID"`
	Filename string `json:"filename"`
	Data     string `json:"data"`
}
