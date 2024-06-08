"use strict";

const socket      = new SockJS( "/mein_ws" );
const stompClient = Stomp.over( socket );

const nachrichtenElement = document.getElementById( "nachrichten" );


stompClient.connect( {}, function( frame ) {

    console.log( "Verbunden: " + frame );

    stompClient.subscribe( "/topic/schlagzeilen", function( nachricht ) {

        const nachrichtJSON = nachricht.body;

        const nachrichtObjekt = JSON.parse( nachrichtJSON );

        const inOderAusland = nachrichtObjekt.istInland ? "[Inland] " : "[Ausland] ";

        const text = inOderAusland + nachrichtObjekt.schlagzeile;

        const paragraphNeu = "<p>" + text + "</p>";

        nachrichtenElement.innerHTML += paragraphNeu;
    });
}, function(error) {

    alert( "Fehler bei WebSocket-Verbindung aufgetreten: " + error );
});

