package services

import "regexp"

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
		re = regexp.MustCompile("(?i)" + typeToCheck + "(.pt)?") // (?i): case insensitive // (.pt)?: optional
	}

	found := re.FindAllString(text, -1)

	if found == nil {
		return false
	}

	return true
}

func GetInvoiceType(text string) (invoiceType string) {
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
