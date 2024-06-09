"use strict";


/** <span>-Element, in dem der Kanalname angezeigt wird. */
let spanKanalname = null;

/** <span>-Element, in dem der Nickname angezeigt wird. */
let spanNickname = null;

/** Kanal, dem beigetreten wurde */
let kanalname = "";

/** Nickname des Chat-Nutzers */
let nickname = "";

/** Objekt für STOMP auf WebSocket */
let stompClient = null;

let destination = "";


/**
 * Wert von URL-Parameter holen.
 * 
 * @param {*} nameParameter Name des URL-Parameters, z.B. "kanalname"
 * 
 * @returns Wert von URL-Parameter oder leerer String, falls Parameter nicht gefunden.
 */
function holeUrlParameter( nameParameter ) {

    nameParameter = nameParameter.replace( /[\[]/, "\\[").replace( /[\]]/, "\\]" );
    const regex = new RegExp( "[\\?&]" + nameParameter + "=([^&#]*)" );
    const results = regex.exec( window.location.search );
    if (!results) {

        console.error( `URL-Parameter ${nameParameter} wurde nicht gefunden.` );
        return "";
    }
    return decodeURIComponent( results[1].replace( /\+/g, " " ));
}


/**
 * Nimmt Initialisierungen vor, sobald die Seite geladen ist.
 */
document.addEventListener( "DOMContentLoaded", function() {

    spanKanalname = document.getElementById( "span_kanalname" );
    if ( !spanKanalname ) {

        alert( "Konnte das Anzeige-Element für den Kanalnamen nicht finden." );
        return;
    }
    spanNickname = document.getElementById( "span_nickname" );
    if ( !spanNickname ) {
            
        alert( "Konnte das Anzeige-Element für den Nicknamen nicht finden." );
        return;
    }

    kanalname = holeUrlParameter( "kanalname" );
    if ( kanalname.length === 0 ) {

        alert( "Kanalname wurde nicht in URL-Parametern gefunden." );
        spanKanalname.innerText = "???";
        return;

    } else {

        spanKanalname.innerText = kanalname;
    }

    nickname = holeUrlParameter( "nickname" );
    if ( nickname.length === 0 ) {

        alert( "Nickname wurde nicht in URL-Parametern gefunden." );
        spanKanalname.innerText = "???";
        return;

    } else {

        spanNickname.innerText = nickname;
    }

    console.log( "Seite wurde erfolgreich initialisiert." );

    verbindungAufbauen();
});


/**
 * Stomp-Verbindung aufbauen: Topic abonnieren und erste Nachricht senden.
 */
function verbindungAufbauen() {
    
    stompClient = new StompJs.Client({
        brokerURL: "ws://localhost:8080/mein_ws" ,
        debug: function( text ) { console.log( "STOMP-Debug-Info: " + text ); },
        reconnectDelay   : 5000, // Millisekunden
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000
    });    

    const subscribeTopic = `/topic/unterhaltung/${kanalname}`;

    destination = `/app/chat/${kanalname}`;

    console.log( "Abonniere STOMP-Topic: " + subscribeTopic );

    stompClient.onConnect = ( frame ) => {

        console.log( "WebSocket-Verbindung aufgebaut: " + frame );
        stompClient.subscribe( subscribeTopic, ( nachricht ) => {

        });

        stompClient.activate(); // nicht vergessen!

        ersteNachrichtSenden();
    };    
}

/**
 * Erste Nachricht von Nutzer an Chat senden
 */
function ersteNachrichtSenden() {

    const payloadObjekt = {
        nickname  : nickname,
        nachricht : "(ist jetzt auch dabei)"
    };

    const payloadString = JSON.stringify( payloadObjekt );

    stompClient.publish({
        destination: destination,
        body       : payloadString
    });

}