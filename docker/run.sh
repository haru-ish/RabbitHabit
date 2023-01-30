#!/usr/bin/env bash

docker run -it --rm --name=rabbit-habit -e POSTGRES_PASSWORD=postgres -e FIREBASE_ID=rabbit-tracker-51700 -v rabbitdb:/var/lib/postgresql/data -p 4278:4278 rabbit-habit
