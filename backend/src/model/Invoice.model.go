package model

import "time"

type Invoice struct {
	ID        uint       `json:"id" gorm:"primary_key"`
	CreatedAt time.Time  `json:"-"`
	UpdatedAt time.Time  `json:"-"`
	DeletedAt *time.Time `json:"-" sql:"index"`

	UserID    uint   `json:"userID"`
	Path      string `json:"path"`
	Filename  string `json:"filename" gorm:"not null"`
	Extension string `json:"extension" gorm:"not null"`

	Type     string `json:"type"`
	FullText string `json:"fullText"`
	Data     string `json:"data"`

	Processed bool `json:"processed" gorm:"not null"`
}
