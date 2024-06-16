#!/bin/bash

# App anlegen (aber noch nicht deployen).
# Es wird auch ein (noch) leeres Repo auf git.heroku.com angelegt.
# siehe auch: git remove -v

heroku create spring-websocket-demo --region eu

# --region: "eu" oder "us" (default: "us")
# Name muss Heroku-weit eindeutig sein
