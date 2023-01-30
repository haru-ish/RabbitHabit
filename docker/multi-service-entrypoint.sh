#!/bin/bash

set -Eeuo pipefail


if [ "$1" = "rabbit-habit" ]; then
    # start postgresql
    docker-entrypoint.sh postgres &

    sleep 2

    # start rabbit habit
    java-rabbit.sh

    exit $?
else
    exec docker-entrypoint.sh "$@"
fi
