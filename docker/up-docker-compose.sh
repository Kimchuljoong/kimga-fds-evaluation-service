#!/bin/bash

PROJECT_NAME="fds-docker-compose"
running_containers=$(docker-compose -p $PROJECT_NAME ps -q)

if [ -n "$running_containers" ]; then
  echo "'$PROJECT_NAME' is already running."
else
  echo "Starting '$PROJECT_NAME'..."
  docker-compose -p $PROJECT_NAME up -d
  if [ $? -eq 0 ]; then
    echo "'$PROJECT_NAME' started successfully."
  else
    echo "Failed to start '$PROJECT_NAME'."
  fi
fi