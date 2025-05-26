#!/bin/bash

# Set your data directory relative to project root
DATA_DIR="$PWD/task-manager-data"

# Start daemon container (removes old one first, if exists)
docker rm -f taskmanager-daemon 2>/dev/null

echo "Starting daemon container..."
docker run -d --name taskmanager-daemon \
  -v "$DATA_DIR":/home/pi/task-manager-data \
  vinylritchie85/taskmanager-daemon:latest

echo "Showing daemon logs (Ctrl+C to exit)..."
docker logs -f taskmanager-daemon
