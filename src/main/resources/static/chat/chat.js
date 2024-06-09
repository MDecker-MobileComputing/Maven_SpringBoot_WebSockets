"use strict";


/** <span>-Element, in dem der Kanalname angezeigt wird. */
let spanKanalname = null;

let kanalname = "";


/**
 * Wert von URL-Parameter holen.
 * 
 * @param {*} nameParameter Name des URL-Parameters, z.B. "kanalname"
 * 
 * @returns Wert von URL-Parameter oder leerer String, falls Parameter nicht gefunden.
 */
function holeUrlParameter( nameParameter ) {

    nameParameter = nameParameter.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp( "[\\?&]" + nameParameter + "=([^&#]*)" );
    const results = regex.exec( window.location.search );
    if (!results) {

        console.error( `URL-Parameter ${nameParameter} wurde nicht gefunden.` );
        return "";
    }
    return decodeURIComponent(results[1].replace(/\+/g, ' '));
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

    kanalname = holeUrlParameter( "kanalname" );
    if ( kanalname.length === 0 ) {

        alert( "Kanalname wurde nicht in URL-Parametern gefunden." );
        spanKanalname.innerText = "???";
        return;

    } else {

        spanKanalname.innerText = kanalname;
    }

    console.log( "Seite wurde erfolgreich initialisiert." );
});