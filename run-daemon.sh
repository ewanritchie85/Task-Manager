#!/bin/bash

# Start daemon container (removes old one first, if exists)
docker rm -f taskmanager-daemon 2>/dev/null

echo "Starting daemon container..."
docker run -d \
  --name taskmanager-daemon \
  -v "$PWD/task-manager-data":/home/pi/task-manager-data \
  -e EVENTS_CSV=/home/pi/task-manager-data/events.csv \
  vinylritchie85/taskmanager-daemon:latest

echo "Showing daemon logs (Ctrl+C to exit)..."
docker logs -f taskmanager-daemon
