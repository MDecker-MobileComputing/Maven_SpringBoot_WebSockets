"use strict";

let texteingabeElement = null;

let vokalAuswahl = null;

let stompClient = null;


/**
 * Nimmt Initialisierungen vor, sobald die Seite geladen ist.
 */
document.addEventListener( "DOMContentLoaded", function() {

    texteingabeElement = document.getElementById( "texteingabe" );
    if ( !texteingabeElement ) {

        alert( "Konnte das Texteingabe-Element nicht finden." );
        return;
    }

    vokalAuswahl = document.getElementById( "vokalAuswahl" );
    if ( !vokalAuswahl ) {

        alert( "Konnte das Vokal-Auswahl-Element nicht finden." );
    }

    stompClient = new StompJs.Client({ brokerURL: "ws://localhost:8080/mein_ws" });
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

    stompClient.activate();

    console.log( "Seite ist initialisiert." );
});


/**
 * Event-Handler für Button "Text umwandeln".
 */
function onUmwandelnButton() {

    const textEingabe = texteingabeElement.value.trim();
    if ( textEingabe.length === 0 ) {

        alert( "Bitte geben Sie einen Text ein." );
        return;
    }    

    const vokal = vokalAuswahl.value;

    const payloadObjekt = {
        text : textEingabe,
        vokal: vokal
    };

    const payloadString = JSON.stringify( payloadObjekt );

    stompClient.publish({
        destination: "/app/vokalersetzung_input",
        body       : payloadString
    });

    console.log( "Text an Server gesendet: " + textEingabe );
}


/**
 * Event-Handler-Funktion für Button "Zurücksetzen".
 */
function onZuruecksetzenButton() {

    texteingabeElement.value = "";
    vokalAuswahl.value       = "a";
}
