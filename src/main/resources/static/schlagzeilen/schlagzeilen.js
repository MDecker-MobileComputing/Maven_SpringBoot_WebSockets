"use strict";

let nachrichtenElement = null;

let stompClient = null;


/**
 * Nimmt Initialisierungen vor, sobald die Seite geladen ist.
 */
document.addEventListener( "DOMContentLoaded", function() {

    nachrichtenElement = document.getElementById( "nachrichten" );
    if ( !nachrichtenElement ) {

        alert( "Konnte das DOM-Element mit ID \"nachrichten\" zur Anzeige der Schlagzeilen nicht finden." );
        return;
    }

    const protocol      = ( window.location.protocol === "https:" ) ? "wss" : "ws";
    const portNummer    = window.location.port;
    const urlFuerBroker = `${protocol}://${window.location.hostname}:${portNummer}/mein_ws`;
    console.log( "Broker-URL: " + urlFuerBroker )

    stompClient = new StompJs.Client({ brokerURL: urlFuerBroker });

    stompClient.onConnect = ( frame ) => {

        console.log( "WebSocket-Verbindung aufgebaut: " + frame );
        stompClient.subscribe( "/topic/schlagzeilen", (nachricht) => {

            const nachrichtJSON = nachricht.body;

            const nachrichtObjekt = JSON.parse( nachrichtJSON );

            const inOderAusland = nachrichtObjekt.istInland ? "[Inland] " : "[Ausland] ";

            const text = inOderAusland + nachrichtObjekt.schlagzeile;

            const paragraphNeu = "<p>" + text + "</p>";

            nachrichtenElement.innerHTML += paragraphNeu;
        });
    };

    stompClient.onWebSocketError = ( error ) => {

        alert( "WebSocket-Fehler: ", error );
    };
    stompClient.onStompError = ( frame ) => {

        alert( "Fehler beim STOMP-Protokoll: " + frame.headers.message );
    };

    stompClient.activate();

    console.log( "Seite ist initialisiert." );
});

