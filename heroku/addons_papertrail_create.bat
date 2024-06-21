
@REM https://elements.heroku.com/addons/papertrail

@REM Mit dem in Heroku eingebauten Log (siehe logTails.bat) sind nur die letzten 1500 Zeilen verfügbar.

heroku addons:create papertrail:choklad
@REM choklad: Kostenloser Tarif (Search Duration: 2 Tage; Archive Duration: 7 Tage)

@REM Über Dashboard kann zu Web-UI von Papertrail auf https://my.papertrailapp.com/
@REM abgesprungen werden, oder über den folgenden CLI-Befehl: heroku addons:open papertrail

@REM Danach kann man noch Papertrail-Plugin für Heroku-CLI installieren:
@REM heroku plugins:install heroku-papertrail
@REM
@REM Logfile herunterladen: heroku pt > papertrail.log
@REM In Logfile suchen: heroku pt -t "search string"
