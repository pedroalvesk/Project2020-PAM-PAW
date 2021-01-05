FROM golang:1.13-buster

##############################
# Tesseract
##############################

RUN apt-get update -qq

# Install tesseract c/c++
RUN apt-get install -y -qq libtesseract-dev libleptonica-dev

# Specify the directory where "tessdata" is located.
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata/

# Load languages.
# These {lang}.traineddata would b located under ${TESSDATA_PREFIX}/tessdata.
RUN apt-get install -y -qq tesseract-ocr-eng tesseract-ocr-por


##############################
# Packages for Golang
##############################

# RabbitMQ
RUN go get github.com/streadway/amqp

# Hot reload (linux)
RUN go get -u github.com/githubnemo/CompileDaemon

# Gosseract (tesseract wrapper)
RUN go get -t github.com/otiai10/gosseract


##############################
# Configuration
##############################

WORKDIR /go/src/ocr

# Copy everything from the current directory to the Working Directory inside the container
COPY src/ .

# Build the Go app
RUN go build -o main .

# Hot reload daemon
ENTRYPOINT CompileDaemon -log-prefix=false -build="go build ./main.go" -command="./main"