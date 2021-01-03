package main

import (
	"encoding/json"
	"fmt"
	"os"
	"regexp"

	"github.com/otiai10/gosseract"
)

//////////////////////////////
// Structs
//////////////////////////////
type invoice struct {
	ID       uint              `json:"invoiceID"`
	Type     string            `json:"type"`
	FullText string            `json:"-"` // `json:"fullText"`
	Data     map[string]string `json:"data"`
}

//////////////////////////////
// Main
//////////////////////////////
func main() {

	if len(os.Args) != 2 {
		fmt.Fprintf(os.Stderr, "usage: %s [image path]\n", os.Args[0])
		return
	}

	client := gosseract.NewClient()
	client.SetPageSegMode(gosseract.PSM_AUTO_OSD)

	defer client.Close()
	client.Languages = []string{"por"} // por
	client.SetImage(os.Args[1])
	text, _ := client.Text()

	doWork(text)
}

//////////////////////////////
// Funcs
//////////////////////////////
func doWork(text string) {
	var inv invoice

	inv.Type = getInvoiceType(text)
	fmt.Println(text)
	analiseText(text)
	inv.FullText = text

	var data = make(map[string]string)
	data["Valor Total"] = "€ 57,60"
	data["Numero Contribuinte"] = "123 123 123"
	inv.Data = data

	fmt.Println("////////////////")

	printInvoice(inv)

	fmt.Println("////////////////")

	fmt.Println("JSON Format")
	b, _ := invoiceToJSON(inv)
	fmt.Println(string(b))

	fmt.Println("////////////////")

}

func printInvoice(inv invoice) {
	fmt.Println("Image Name:", os.Args[1])
	fmt.Println("Image Type:", inv.Type)
	fmt.Println("Image Data:", inv.Data)

	//fmt.Println("Image Text:", inv.FullText)
}

//////////////////////////////
// Json Binding
//////////////////////////////
func invoiceToJSON(inv invoice) ([]byte, error) {
	b, err := json.Marshal(inv)
	if err != nil {
		return nil, err
	}

	return b, nil
}

func jsonToInvoice(b []byte, inv *invoice) (success bool) {
	err := json.Unmarshal(b, inv)

	if err == nil {
		return false
	}

	return true
}

//////////////////////////////
// Invoice Type
//////////////////////////////
func regexAnalise(text string) {
	// (?i)		:case insensitive
	// (es)?	:"es" characters might be included zero times or once
	// re := regexp.MustCompile("(?i)fox(es)?")
	re := regexp.MustCompile("(?i)meo(.pt)?")

	found := re.FindAllString(text, -1)

	fmt.Printf("%q\n", found)

	if found == nil {
		fmt.Printf("no match found\n")
		os.Exit(1)
	}

	for _, word := range found {
		fmt.Printf("%s\n", word)
	}
}

func invoiceIsMeo(text string) (isMeo bool) {
	return invoiceIs("meo", text)
}

func invoiceIsNos(text string) (isMeo bool) {
	return invoiceIs("NOS", text)
}

func invoiceIsEdp(text string) (isMeo bool) {
	return invoiceIs("edp", text)
}

func invoiceIs(typeToCheck string, text string) (isType bool) {

	var re *regexp.Regexp
	if typeToCheck == "NOS" {
		re = regexp.MustCompile(typeToCheck + "(.pt)?")
	} else {

		re = regexp.MustCompile("(?i)" + typeToCheck + "(.pt)?")
	}

	found := re.FindAllString(text, -1)

	//  fmt.Printf("%q\n", found)

	if found == nil {
		return false
	}

	return true
}

func getInvoiceType(text string) (invoiceType string) {
	if invoiceIsEdp(text) {
		return "EDP"
	}

	if invoiceIsMeo(text) {
		return "MEO"
	}

	if invoiceIsNos(text) {
		return "NOS"
	}

	return "GENERIC"
}

//////////////////////////////
// Invoice Data
//////////////////////////////
func analiseText(text string) {
	analiseTextMeo(text)
}

func analiseTextMeo(text string) {
	var keys = map[string]string{
		"montante": "Montante(...)?[0-9][0-9],[0-9][0-9]",
		"entidade": "Entidade [0-9][0-9][0-9][0-9]([0-9])?",

		"referencia": "Refer(.)?ncia [0-9][0-9][0-9] [0-9][0-9][0-9] [0-9][0-9][0-9]",

		"valor": "Valor a Pagar(.)?(.)?(.)?[0-9][0-9],[0-9][0-9]",
		"€":     "€(.)?[0-9][0-9],[0-9][0-9]",

		"nFatura":       "(Fatura: )?.. ../.........",
		"nReferencia":   "Refer(.)?ncia: [0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]",
		"nContribuinte": "Contribuinte: [0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]",
		"nConta":        "Conta: [0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]([0-9])?",
		"dataEmissao":   "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]",
	}

	for k, v := range keys {

		re := regexp.MustCompile(v)
		data := re.FindAllString(text, -1)

		fmt.Print(k, ":")
		fmt.Printf("%q\n", data)

	}

	os.Exit(1)

}
