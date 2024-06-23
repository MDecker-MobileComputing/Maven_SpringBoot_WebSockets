#!/bin/bash

# neues Log-Nachrichten verfolgen bis Programm mit CTRL+C beendet wird
heroku logs --tail

# Nur Logs von Heroku-Router ausgeben: heroku logs -tp router
