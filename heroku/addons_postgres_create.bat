@REM Fügt der App eine Postgresql-Datenbank-Instanz hinzu
@REM https://elements.heroku.com/addons/heroku-postgresql
@REM
@REM Kleinste Instanz "essential-0":
@REM * Kosten: 0,007$/Stunde, 5$/Monat
@REM * Kapazität: 1 GB

@REM Standard-Instanz: essential-0
@REM heroku addons:create heroku-postgresql
heroku addons:create heroku-postgresql:essential-0

@REM Folgende Umgebungsvariable werden gesetzt:
@REM * SPRING_DATASOURCE_URL
@REM * SPRING_DATASOURCE_USERNAME
@REM * SPRING_DATASOURCE_PASSWORD

@REM Nach Erzeugung kann man sich mit den folgenden beiden
@REM Befehlen die URL bzw. weitere Infos anzeigen lassen:
@REM * heroku config
@REM * heroku pg:info
