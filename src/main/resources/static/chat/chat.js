"use strict";

/** <input>-Element, in dem Nutzer Nachrichten eingibt */
let inputNachricht = null;

/** <span>-Element, in dem der Kanalname angezeigt wird. */
let spanKanalname = null;

/** <span>-Element, in dem der Nickname angezeigt wird. */
let spanNickname = null;

/** <div>-Element, in dem Chatverlauf angezeigt wird. */
let divChatverlauf = null;

/** Kanal, dem beigetreten wurde */
let kanalname = "";

/** Nickname des Chat-Nutzers */
let nickname = "";

/** Objekt für STOMP auf WebSocket */
let stompClient = null;

/** Ziel (Destination) für STOMP-Nachrichten */
let stompZiel = "";


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
    inputNachricht = document.getElementById( "input_nachricht" );
    if ( !inputNachricht ) {

        alert( "Konnte das Eingabe-Element für die Nachrichten nicht finden." );
        return;
    }   
    divChatverlauf = document.getElementById( "chatverlauf" );
    if ( !divChatverlauf ) {

        alert( "Konnte das Anzeige-Element für den Chatverlauf nicht finden." );
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
 * Nachricht von Nutzer an Chat senden.
 * 
 * @param {string} nachricht Nachricht des Nutzers
 */
function nachrichtSenden( nachricht ) {

    const payloadObjekt = {
        nickname  : nickname,
        nachricht : nachricht
    };

    const payloadString = JSON.stringify( payloadObjekt );

    stompClient.publish({
        destination: stompZiel,
        body       : payloadString
    });

    console.log( `Nachricht an Kanal gesendet: \"${nachricht}\"` );
}


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

    stompZiel = `/app/chat/${kanalname}`;

    console.log( "Abonniere STOMP-Topic: " + subscribeTopic );

    stompClient.onConnect = ( frame ) => {

        console.log( "WebSocket-Verbindung aufgebaut: " + frame );
        stompClient.subscribe( subscribeTopic, ( nachricht ) => {

            const jsonPayload = JSON.parse( nachricht.body );

            const spanNickname  = `<span class=\"fett\">${jsonPayload.nickname} : </span>`;

            const paragraphNeu = `<p>${spanNickname}${jsonPayload.nachricht}</p>`;
    
            divChatverlauf.innerHTML += paragraphNeu;     
        });
        
        nachrichtSenden( "... ist dem Chat beigetreten." );
    };    

    stompClient.activate(); // nicht vergessen!
}


/**
 * Event-Handler für den Button "Senden", um die aktuell
 * eingegebene Nachricht an den Chat zu senden.
 */
function onNachrichtSendenButton() {

    const nachricht = inputNachricht.value.trim();

    nachrichtSenden( nachricht );

    inputNachricht.value = "";
}
