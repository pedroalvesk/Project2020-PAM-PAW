FROM golang:latest

RUN apt-get update -qq

# Install tesseract c/c++
RUN apt-get install -y -qq libtesseract-dev libleptonica-dev

# Specify the directory where "tessdata" is located.
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata/

# Load languages.
# These {lang}.traineddata would b located under ${TESSDATA_PREFIX}/tessdata.
RUN apt-get install -y -qq tesseract-ocr-eng tesseract-ocr-por

# Install tesseract golang wrapper.
RUN go get -t github.com/otiai10/gosseract

# Set your project folder
WORKDIR /go/src/pawb-project/
