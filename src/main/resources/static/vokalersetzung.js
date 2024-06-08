"use strict";

let texteingabeElement = null;

let stompClient = null;


/**
 * Nimmt initialisierungen vor, sobald die Seite geladen ist.
 */
document.addEventListener( "DOMContentLoaded", function() {

    texteingabeElement = document.getElementById( "texteingabe" );
    if ( !texteingabeElement ) {

        console.error( "Konnte das Texteingabe-Element nicht finden." );
        return;
    }

    stompClient = new StompJs.Client({ brokerURL: "ws://localhost:8080/ws" });
    console.log( "Stomp-Client erstellt: " + stompClient );
    stompClient.onConnect = ( frame ) => {

        console.log( "WebSocket-Verbindung aufgebaut: " + frame );
        stompClient.subscribe( "/topic/vokalersetzungs_output", (nachricht) => {

            alert( "Antwort vom Server erhalten: " + nachricht.body)
        });
    };

    stompClient.onWebSocketError = ( error ) => {

        alert( "WebSocket-Fehler: ", error );
    };
    stompClient.onStompError = ( frame ) => {

        alert( "Fehler beim Stomp-Protokoll: " + frame.headers.message );
    };

    console.log( "Seite ist initialisiert." );
});


/**
 * Event-Handler für Button "Text umwandeln".
 */
function onUmwandelnButton() {

    let textEingabe = texteingabeElement.value;

    textEingabe = textEingabe.trim();

    if ( textEingabe.length === 0 ) {

        alert( "Bitte geben Sie einen Text ein." );
        return;
    }

    stompClient.publish({
        destination: "/app/vokalersetzung_input",
        body: textEingabe
    });

    console.log( "Text an Server gesendet: " + textEingabe );
}


/**
 * Event-Handler-Funktion für Button "Zurücksetzen".
 */
function onZuruecksetzenButton() {

    texteingabeElement.value = "";
}
