"use strict";

/** <input>-Element für Kanalname */
let  eingabeKanalname = null;

/** <input>-Element für Nickname */
let eingabeNickname = null;


/**
 * Nimmt Initialisierungen vor, sobald die Seite geladen ist.
 */
document.addEventListener( "DOMContentLoaded", function() {

    eingabeKanalname = document.getElementById( "input_kanalname" );
    if ( !eingabeKanalname ) {

        alert( "Konnte das Texteingabe-Element für Kanalname nicht finden." );
        return;
    }

    eingabeNickname = document.getElementById( "input_nickname" );
    if ( !eingabeNickname ) {

        alert( "Konnte das Texteingabe-Element für Nickname nicht finden." );
        return;
    }

    console.log( "Seite wurde erfolgreich initialisiert." );
});


/**
 * Event-Handler für Button "Chat starten".
 */
function onChatStartenButton() {
        
    const kanalname = eingabeKanalname.value.trim();
    if ( kanalname.length < 3 ) {

        alert( "Bitte geben Sie einen Kanalnamen mit mindestens drei Zeichen ein." );
        return;
    }

    const nickname  = eingabeNickname.value.trim();
    if ( nickname.length < 3 ) {

        alert( "Bitte geben Sie einen Nickname mit mindestens drei Zeichen ein." );
        return;
    }

    console.log( `Chat wird gestartet: Kanalname=\"${kanalname}\", Nickname=\"${nickname}\".` );

    const url = `chat.html?kanalname=${encodeURIComponent(kanalname)}&nickname=${encodeURIComponent(nickname)}`

    window.location.href = url;
}


/**
 * Event-Handler für Button "Zurücksetzen".
 */
function onZuruecksetzenButton() {

    eingabeKanalname.value = "";
    eingabeNickname.value = "";
}
