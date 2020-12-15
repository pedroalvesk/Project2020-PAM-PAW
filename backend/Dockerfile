FROM golang:1.13-alpine

RUN apk update && apk upgrade && apk add --no-cache bash git openssh gcc libc-dev

##############################
# Packages for Golang
##############################

# Database
RUN go get -u github.com/jinzhu/gorm
RUN go get -u github.com/jinzhu/gorm/dialects/postgres

# Web server
RUN go get -u github.com/gin-gonic/gin
RUN go get -u github.com/gin-contrib/cors
RUN go get -u github.com/rs/cors/wrapper/gin

# Documentation
RUN go get -u github.com/swaggo/gin-swagger
RUN go get -u github.com/swaggo/swag/cmd/swag
RUN go get -u github.com/alecthomas/template
RUN go get -u github.com/swaggo/files
RUN go get -u github.com/swaggo/http-swagger

# Tests and Mocking
RUN go get -u github.com/stretchr/testify

# JWT Token Authentication
RUN go get -u github.com/dgrijalva/jwt-go

# Socket.io
RUN go get github.com/googollee/go-socket.io

# Hot reload (linux)
RUN go get -u github.com/githubnemo/CompileDaemon

# Random filenames
RUN go get github.com/google/uuid

##############################
# Configuration
##############################

# HOT RELOAD
RUN go get -u github.com/githubnemo/CompileDaemon

# Set the Current Working Directory inside the container
WORKDIR /go/src/api

# Copy everything from the current directory to the Working Directory inside the container
COPY src/ .

# Generate Documentation (run swagger)
RUN /go/bin/swag init

# Build the Go app
RUN go build -o main .

# Expose port 8090 to the outside world
EXPOSE 8090

# Run the executable DEPLOYMENT
# CMD ["./main"]

# Enable hot reload daemon
ENTRYPOINT CompileDaemon -log-prefix=false -build="go build ./main.go" -command="./main"
