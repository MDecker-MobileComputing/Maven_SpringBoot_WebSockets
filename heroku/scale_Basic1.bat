@REM 1 Dyno-Container vom Typ "Basic": billigster Container
@REM ohne Abo, mehr als ein Container geht nicht
@REM Kosten ca.: 0,01$/Stunde, 7$/Monat

heroku ps:scale web=1:basic
