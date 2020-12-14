DOCKER_NAME="pawb/ocr-tesseract"

sudo docker build -t "${DOCKER_NAME}" .
sudo docker run -it --rm -v "$PWD/src:/go/src/pawb-project" --name "golang-ocr-tesseract" "${DOCKER_NAME}"
