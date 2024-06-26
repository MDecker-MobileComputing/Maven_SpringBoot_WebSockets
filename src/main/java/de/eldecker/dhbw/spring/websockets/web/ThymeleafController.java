package de.eldecker.dhbw.spring.websockets.web;

import static java.lang.String.format;

import de.eldecker.dhbw.spring.websockets.db.entities.ChatKanalEntity;
import de.eldecker.dhbw.spring.websockets.db.repos.ChatKanalRepo;
import de.eldecker.dhbw.spring.websockets.model.ChatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller-Klasse für Thymeleaf (Thymeleaf Engine).  
 */
@Controller
@RequestMapping( "/app/" )
public class ThymeleafController {

    private final static Logger LOG = LoggerFactory.getLogger( ThymeleafController.class );

    /** Repo-Bean für Zugriff auf DB-Tabelle mit Chat-Kanälen. */
    @Autowired
    private ChatKanalRepo _chatKanalRepo;

    /** Bean für formatierung von Datum-/Zeitwert für Anzeige. */
    @Autowired
    private DateTimeFormatter _datumZeitFormatierer;


    /**
     * Controller-Methode für Anzeige der Liste aller Kanäle.
     * 
     * @param model Objekt für Platzhalterwerte für Template.
     * 
     * @return Name anzuzeigender Template-Datei ohne Datei-Endung.
     */
    @GetMapping( "/chat-kanal-liste" )
    public String chatKanalListe ( Model model ) {

        final List<ChatKanalEntity> chatKanalListe = _chatKanalRepo.findAllByOrderByNameAsc();

        model.addAttribute( "chatKanalListe", chatKanalListe );

        return "chat-kanal-liste";
    }

    
    /**
     * Controller-Methode für Anzeige aller Chat-Beiträge in einem Chat-Kanal
     * 
     * @param model Objekt für Platzhalterwerte für Template.
     * 
     * @param uuid Primärschlüssel des Chat-Kanals
     * 
     * @return Name anzuzeigender Template-Datei ohne Datei-Endung.
     * 
     * @throws ChatException Kanal mit {@code uuid} wurde nicht gefunden
     */
    @GetMapping( "/chat-kanal-historie" )
    public String chatKanalHistorie( Model model,
                                     @RequestParam( value = "uuid", required = true ) UUID uuid )
                throws ChatException {

        final Optional<ChatKanalEntity> kanalOptional = _chatKanalRepo.findById( uuid );
        if ( kanalOptional.isEmpty() ) {

            throw new ChatException( "Chat-Kanal mit UUID=" + uuid + " nicht gefunden" );
        }

        final ChatKanalEntity kanalEntity = kanalOptional.get();

        final Optional<LocalDateTime> zeitpunktOptional = kanalEntity.getFruehesterBeitragZeitpunkt();
        if ( zeitpunktOptional.isPresent() ) {

            final LocalDateTime zeitpunkt = zeitpunktOptional.get();
            final String zeitpunktStr = _datumZeitFormatierer.format( zeitpunkt );

            model.addAttribute( "ersterBeitragZeitpunkt", zeitpunktStr );
        }
        else {

            model.addAttribute( "ersterBeitragZeitpunkt", "???" );
        }

        model.addAttribute( "chatKanal"    , kanalEntity                );
        model.addAttribute( "beitragListe" , kanalEntity.getBeitraege() );

        return "chat-kanal-historie";
    }


    /**
     * Controller-Methode für Löschen eines Chat-Kanals.
     * 
     * @param model Objekt für Platzhalterwerte für Template.
     * 
     * @param uuid Primärschlüssel des Chat-Kanals
     * 
     * @return Name anzuzeigender Template-Datei ohne Datei-Endung.
     * 
     * @throws ChatException Kanal mit {@code uuid} wurde nicht gefunden
     */
    @GetMapping( "/chat-kanal-loeschen" )
    public String chatKanalLoeschen( Model model,
                                     @RequestParam( value = "uuid", required = true ) UUID uuid )
                      throws ChatException {

        final Optional<ChatKanalEntity> kanalOptional = _chatKanalRepo.findById( uuid );
        if ( kanalOptional.isEmpty() ) {

            throw new ChatException( "Zu loeschender Kanal mit UUID=" + uuid + " nicht gefunden" );
        }

        final ChatKanalEntity kanalEntity = kanalOptional.get();

        final String chatKanalName = kanalEntity.getName();
        final int anzahlBeitraege  = kanalEntity.getBeitraege().size();

        _chatKanalRepo.delete( kanalEntity );

        final String str = format( "Chat-Kanal \"%s\" mit %d Beiträgen wurde gelöscht.",
                                    chatKanalName, anzahlBeitraege);
        LOG.info( str );
        model.addAttribute( "meldung", str );


        return "chat-kanal-loesch-ergebnis";
    }

}
