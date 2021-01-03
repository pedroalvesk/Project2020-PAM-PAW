package model

import "time"

type Invoice struct {
	ID        uint       `json:"id" swaggerignore:"true" gorm:"primary_key"`
	CreatedAt time.Time  `json:"-" swaggerignore:"true"`
	UpdatedAt time.Time  `json:"-" swaggerignore:"true"`
	DeletedAt *time.Time `json:"-" swaggerignore:"true" sql:"index"`

	UserID    uint   `json:"userID"`
	Path      string `json:"path"`
	Filename  string `json:"filename" gorm:"not null"`
	Extension string `json:"extension" gorm:"not null"`

	Type     string `json:"type"`
	FullText string `json:"-"` // `json:"fullText"`
	Data     string `json:"data"`

	Processed bool `json:"processed" gorm:"not null"`
}
