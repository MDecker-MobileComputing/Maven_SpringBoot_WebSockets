@REM App anlegen (aber noch nicht deployen).
@REM Es wird auch ein (noch) leeres Repo auf git.heroku.com angelegt.
@REM siehe auch: git remove -v

heroku create spring-websocket-demo --region eu

@REM --region: "eu" oder "us" (default: "us")
@REM Name muss Heroku-weit eindeutig sein