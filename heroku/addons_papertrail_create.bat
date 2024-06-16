
@REM https://elements.heroku.com/addons/papertrail

heroku addons:create papertrail:choklad
@REM choklad: Kostenloser Tarif (Search Duration: 2 Tage; Archive Duration: 7 Tage)

@REM Danach noch Papertrail-Plugin für Heroku installieren:
@REM heroku plugins:install heroku-papertrail
@REM
@REM Logfile herunterladen: heroku pt > papertrail.log
@REM In Logfile suchen: heroku pt -t "search string"
@REM 
@REM Mit dem in Heroku eingebauten Log (siehe logTails.bat) sind nur die letzten 1500 Zeilen verfügbar.
