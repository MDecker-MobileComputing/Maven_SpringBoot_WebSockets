#!/bin/bash

# Datenbank-Instanz löschen.
# Als Sicherheitsabfrage muss man den Namen der Anwendung eingeben
heroku addons:destroy heroku-postgresql
