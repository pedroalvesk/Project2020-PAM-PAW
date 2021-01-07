package models

type Invoice struct {
	ID        uint       `json:"id"`

	UserID    uint   `json:"userID"`
	Filename  string `json:"filename"`
	Extension string `json:"extension"`
	Path      string `json:"path"`

	Type     string `json:"type"`
	FullText string `json:"fullText"` // `json:"-"`
	Data     string `json:"data"`

	Processed bool `json:"processed"`
}
