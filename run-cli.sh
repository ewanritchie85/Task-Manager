echo "Starting CLI container..."
docker run -it --rm \
  -v "$PWD/task-manager-data":/home/pi/task-manager-data \
  -e EVENTS_CSV=/home/pi/task-manager-data/events.csv \
  vinylritchie85/taskmanager-cli:latest

