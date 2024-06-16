@REM O Dyno-Container vom Typ "Basic": App ist nicht verfuegbar, 
@REM erzeugt aber -- bis auf evtl. AddOns -- keine Kosten.

heroku ps:scale web=0:basic
