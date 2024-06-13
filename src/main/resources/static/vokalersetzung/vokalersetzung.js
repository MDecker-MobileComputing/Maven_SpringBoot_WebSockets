"use strict";

/** UI-Element für Eingabe von Text, in dem Vokale ersetzt werden sollen */
let texteingabeElement = null;

/** ComboBox für Auswahl Zielvokal */
let vokalAuswahl = null;

/** <div>-Element, dem die "Übersetzungsergebnisse" als Kinder hinzugefügt werden */
let ergebnisseElement = null;

/** Objekt für STOMP auf WebSocket */
let stompClient = null;

/** Zähler für "Übersetzungsergebnisse". */
let ergebnisZaehler = 0;


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
        return;
    }

    ergebnisseElement = document.getElementById( "ergebnisse" );
    if ( !ergebnisseElement) {

        alert( "Konnte das Ergebnis-Element nicht finden." );
        return;
    }

    stompClient = new StompJs.Client({
        brokerURL: `ws://${window.location.hostname}:8080/mein_ws`,
        debug: function( text ) { console.log( "STOMP-Debug-Info: " + text ); },
        reconnectDelay   : 5000, // Millisekunden
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000

    });
    stompClient.onConnect = ( frame ) => {

        console.log( "WebSocket-Verbindung aufgebaut: " + frame );
        stompClient.subscribe( "/user/queue/vokalersetzungs_output", ( nachricht ) => {

            texteingabeElement.value = "";

            ergebnisZaehler++;

            const spanNeu = `<span class="fett">Ergebnis Nr. ${ergebnisZaehler}: </span>`;

            const paragraphNeu = `<p>${spanNeu}${nachricht.body}</p>`;

            ergebnisseElement.innerHTML += paragraphNeu;
        });
        stompClient.subscribe( "/user/queue/vokalersetzungs_fehler", ( nachricht ) => {

            alert( "Fehlermeldung vom Server: " + nachricht.body );
        });
    };

    stompClient.onWebSocketError = ( error ) => {

        alert( "WebSocket-Fehler: ", error );
    };
    stompClient.onStompError = ( frame ) => {

        alert( "Fehler beim Stomp-Protokoll: " + frame.headers.message );
    };

    stompClient.activate(); // nicht vergessen!

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

    ergebnisZaehler = 0;

    const erstesKind = ergebnisseElement.firstChild;
    while ( erstesKind ) {

        ergebnisseElement.removeChild( erstesKind );
    }

}
