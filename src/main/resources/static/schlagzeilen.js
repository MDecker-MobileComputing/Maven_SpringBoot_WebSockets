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

    stompClient = new StompJs.Client({ brokerURL: "ws://localhost:8080/mein_ws" });

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

