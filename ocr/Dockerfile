FROM golang:1.13-buster

##############################
# Packages for Golang
##############################

# RabbitMQ
RUN go get github.com/streadway/amqp

# Hot reload (linux)
RUN go get -u github.com/githubnemo/CompileDaemon


##############################
# Configuration
##############################

WORKDIR /go/src/api

# Copy everything from the current directory to the Working Directory inside the container
COPY . .

# Build the Go app
RUN go build -o main .

# Hot reload daemon
ENTRYPOINT CompileDaemon -log-prefix=false -build="go build ./main.go" -command="./main"