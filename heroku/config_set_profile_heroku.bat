
@REM Spring-Profil "heroku" aktvieren, damit Datei "application-heroku.properties" 
@REM aus dem Ordner "src/main/resources" verwendet wird

heroku config:set SPRING_PROFILES_ACTIVE=heroku
