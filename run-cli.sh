DATA_DIR="$PWD/task-manager-data"

echo "Starting CLI container..."
docker run -it --rm \
  -v "$DATA_DIR":/home/pi/task-manager-data \
  vinylritchie85/taskmanager-cli:latest

