"use strict";

const socket      = new SockJS( "/ws" );
const stompClient = Stomp.over( socket );

const nachrichtenElement = document.getElementById( "nachrichten" );

stompClient.connect( {}, function( frame ) {

    console.log( "Verbunden: " + frame );

    stompClient.subscribe( "/topic/schlagzeilen", function( nachricht ) {

        const nachrichtPayload = nachricht.body;
        const paragraphNeu     = "<p>" + nachrichtPayload + "</p>";

        nachrichtenElement.innerHTML += paragraphNeu;
    });
});