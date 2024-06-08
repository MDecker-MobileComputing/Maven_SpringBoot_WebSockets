package de.eldecker.dhbw.spring.websockets.logik;

import static java.util.Collections.emptyList;

import de.eldecker.dhbw.spring.websockets.model.Schlagzeile;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * Service-Bean, die zufällige (Negativ-)Schlagzeilen erzeugt.
 */
@Service
public class SchlagzeilenErzeuger {

    private final static Logger LOG = LoggerFactory.getLogger( SchlagzeilenErzeuger.class );
    
    /**
     * Liste von negativen Ereignissen, alphabetisch sortiert.
     */
    public static final String[] EREIGNISSE_ARRAY = {
            "Altersarmut", "Amoklauf", "Ausgangs-Sperre", "Ärztemangel", "Ausschreitungen",
            "Bankrott", "Bildungsnotstand", "Busunfall", "Brandstiftung", "Chemie-Unfall",
            "Cyberangriff", "Doping-Skandal", "Drogenkriminalität", "Dürre", "Entführung",
            "Erdbeben", "Erdrutsch", "Erpressung", "Explosion", "Finanzkrise", "Gasexplosion",
            "Gewaltserie", "Geflügelpest", "Großbrand", "Großschadenslage", "Hausbesetzung",
            "Handwerkermangel", "Hitzewelle", "Korruption", "Lawine", "Lebensmittelskandal",
            "Lehrermangel", "Massenkarambolage", "Massenpanik", "Mord", "Ölkatastrophe",
            "Regierungskrise", "Rinderwahn", "Rohstoffknappheit", "Schiffskollision", "Skandal",
            "Smog-Alarm", "Studierendenproteste", "Stromausfall", "Tierseuche", "Unwetter",
            "Überfall", "Überschwemmung", "Wahlmanipulation", "Waldbrand", "Waldsterben",
            "Wirtschaftskrise", "Vulkanausbruch"
        };

    /**
     * Liste von deutschen Bundesländer in alphabetischer Reihenfolge.
     */    
    public static final String[] ORTE_DEUTSCHLAD_ARRAY = {
            "Baden-Württemberg", "Bayern", "Bremen", "Berlin", "Brandenburg",
            "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen",
            "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen",
            "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen"
        };

    /**
     * Liste von Ländern (Nationen) in alphabetischer Reihenfolge.
     */        
    public static final String[] ORTE_NATIONEN_ARRAY = {
            "Albanien", "Amerika", "Andorra", "Argentinien", "Armenien", "Australien",
            "Brasilien", "Belgien", "Bosnien und Herzegowina", "Bulgarien", "China",
            "Dänemark", "Estland", "Finnland", "Frankreich", "Griechenland", "Großbritannien",
            "Holland", "Iran", "Irak", "Irland", "Island", "Italien", "Japan", "Kanada", "Kosovo",
            "Kolumbien", "Kroatien", "Lettland", "Liechtenstein", "Litauen", "Luxemburg",
            "Malta", "Mazedonien", "Moldawien", "Monaco", "Montenegro",
            "Norwegen", "Österreich", "Panama", "Peru", "Polen", "Pakistan",
            "Portugal", "Rumänien", "Russland", "San Marino", "Schweden",
            "der Schweiz", "Senegal", "Serbien", "Singapur", "Slowakei", "Slowenien",
            "Spanien", "Süd-Afrika", "Taiwan", "Tunesien", "Tschechien", "der Türkei",
            "der Ukraine", "Ungarn", "Vatikanstadt", "Weißrussland"
        };

       
    /** Zufallsgenerator */
    private final static Random _random = new Random();


    /**
     * Konstruktor, schreibt Anzahl Ereignisse und Länder in Log.
     */
    public SchlagzeilenErzeuger() {

        LOG.info( "Anzahl Ereignisse        : {}", EREIGNISSE_ARRAY.length      );
        LOG.info( "Anzahl Orte (Deutschland): {}", ORTE_DEUTSCHLAD_ARRAY.length );
        LOG.info( "Anzahl Orte (Nationen)   : {}", ORTE_NATIONEN_ARRAY.length   );
    }


    /**
     * Methode gibt ein zufällig ausgewähltes Element aus dem als
     * Argument übergebenen String-Array zurück.
     *
     * @param stringArray Array mit Strings, von dem einer zufällig
     *                    ausgewählt werden soll
     *
     * @return Zufällig ausgewählter String aus {@code stringArray};
     *         leerer String, wenn {@code stringArray.length == 0}.
     */
    private String getZufallsElement( String[] stringArray ) {

        final int laenge = stringArray.length;
        if ( laenge == 0) {

            LOG.warn( "Soll zufälligen String aus Array mit 0 Elementen zurückgeben." );
            return "";
        }

        int randomIndex = _random.nextInt( laenge );

        return stringArray[ randomIndex ];
    }


    /**
     * Methode erzeugt eine zufällige Schlagzeile der Form "EREIGNIS in ORT",
     * z.B. "Smog-Alarm in Estland".
     *
     * @return Zufällig erzeugte Schlagzeile; je nach im Text enthaltenen
     *         Ort ist das Flag {@code inland} entsprechend gesetzt.
     */
    public Schlagzeile erzeugeZufallsSchlagzeile() {

        final String ereignis = getZufallsElement( EREIGNISSE_ARRAY );

        String  ort    = "";
        boolean inland = false;

        if ( _random.nextFloat() < .3 ) { // 30% Wahrscheinlichkeit für Inlandsnachricht

            ort = getZufallsElement( ORTE_DEUTSCHLAD_ARRAY );
            inland = true;

        } else {

            ort = getZufallsElement( ORTE_NATIONEN_ARRAY );
        }

        final String schlagzeileText = String.format( "%s in %s", ereignis, ort );

        final Schlagzeile schlagzeile = new Schlagzeile( schlagzeileText, inland );

        return schlagzeile;
    }

}
