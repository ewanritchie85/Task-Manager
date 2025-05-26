#!/bin/bash

echo "Stopping all containers..."
containers=$(docker ps -aq)
if [ -n "$containers" ]; then
    docker stop $containers
    echo "Removing all containers..."
    docker rm $containers
else
    echo "No containers to stop or remove."
fi

echo "Done."
