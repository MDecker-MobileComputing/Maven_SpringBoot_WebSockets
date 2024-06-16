
@REM One-Off-Dyno starten, um einzelnen Befehl auszuführen, z.B. "ls" zur Anzeige Inhalt Wurzelverzeichnis
@REM ACHTUNG: die entsprechende Dyno-Laufzeit wird in Rechnung gestellt

heroku run ls -l
@REM -l: long format, showing permissions, owner, group, size, and timestamp

@REM Es kann auch eine interaktive Shell geöffnet werden: heroku run bash
