package de.eldecker.dhbw.spring.websockets.service;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.eldecker.dhbw.spring.websockets.db.entities.ChatBeitragEntity;
import de.eldecker.dhbw.spring.websockets.db.entities.ChatKanalEntity;
import de.eldecker.dhbw.spring.websockets.db.repos.ChatBeitragRepo;
import de.eldecker.dhbw.spring.websockets.db.repos.ChatKanalRepo;
import de.eldecker.dhbw.spring.websockets.model.ChatException;
import de.eldecker.dhbw.spring.websockets.model.ChatNachricht;


/**
 * Service-Bean für Datenbankoperationen für Chat. 
 */
@Service
public class ChatService {

    private final static Logger LOG = LoggerFactory.getLogger( ChatService.class );

    /** Repo-Bean für Zugriff auf Datenbanktabelle mit Chat-Beiträgen. */
    @Autowired
    private ChatBeitragRepo _chatBeitragRepo;

    /** Repo-Bean für Zugriff auf Datenbanktabelle mit Chat-Kanälen. */
    @Autowired
    private ChatKanalRepo _chatKanalRepo;


    /**
     * Überprüfen, ob es schon einen Kanal mit Name {@code chatKanalName} gibt.
     * 
     * @param chatKanalName Name Chat-Kanal, der auf Existenz zu prüfen ist
     * 
     * @return {@code true} gdw. es schon einen Kanal mit {@code chatKanalName} gibt 
     */
    public boolean kanalSchonVorhanden( String chatKanalName ) {

        final Optional<ChatKanalEntity> kanalOptional = 
                _chatKanalRepo.findByName( chatKanalName );
        
        return kanalOptional.isPresent();
    }

    
    /**
     * Neuen Chat-Kanal anlegen.
     * 
     * @param chatKanalName Name für neuen Kanal
     */
    public void neuerChatKanal( String chatKanalName ) {

        ChatKanalEntity chatKanal = new ChatKanalEntity( chatKanalName );

        chatKanal = _chatKanalRepo.save( chatKanal );

        LOG.info( "Neuen Chat-Kanal \"{}\" mit UUID={} auf DB gespeichert.",
                  chatKanalName, chatKanal.getId() );
    }

    
    /**
     * Neuen Beiträg in Chat-Kanal {@code chatKanalName} einfügen.
     * 
     * @param chatKanalName Name des Chat-Kanals
     * 
     * @param chatNachricht Neue Nachricht für {@code chatKanalName}
     */
    public void neuerChatBeitrag( String        chatKanalName,
                                  ChatNachricht chatNachricht ) {

        final Optional<ChatKanalEntity> kanalOptional = _chatKanalRepo.findByName( chatKanalName );
        if ( kanalOptional.isEmpty() ) {

            LOG.error( "Kein Chat-Kanal mit Name \"{}\" gefunden, kann Beitrag nicht speichern.",
                       chatKanalName );
            return;
        }

        final ChatKanalEntity chatKanal = kanalOptional.get();

        ChatBeitragEntity beitrag = new ChatBeitragEntity( chatNachricht.getNickname(),
                                                           chatNachricht.getNachricht(),
                                                           chatKanal,
                                                           now() );
        beitrag = _chatBeitragRepo.save( beitrag );

        LOG.info( "Chat-Beitrag gespeichert: {}", beitrag );
    }


    /**
     * Alle Beiträge für einen bestimmten Chat-Kanal zurückliefern.
     * 
     * @param chatKanalName Name des Chat-Kanals
     * 
     * @return Liste aller Beiträge im Kanal {@code chatKanalName}
     */
    public List<ChatBeitragEntity> getAlleBeitraegeKanal( String chatKanalName ) {

        final Optional<ChatKanalEntity> kanalOptional = _chatKanalRepo.findByName( chatKanalName );
        if ( kanalOptional.isEmpty() ) {

            LOG.error( "Kein Chat-Kanal mit Name \"{}\" gefunden, kann nicht Liste aller Beiträge liefern.",
                       chatKanalName );

           return emptyList();
        }

        final ChatKanalEntity chatKanal = kanalOptional.get();

        return chatKanal.getBeitraege();
    }

    
    /**
     * Chat-Kanal löschen.
     * 
     * @param chatKanalName Name von zu löschendem Kanal
     * 
     * @throws ChatException Kein Kanal mit {@code chatKanalName} gefunden.
     */
    public void chatKanalLoeschen( String chatKanalName ) throws ChatException {

        final Optional<ChatKanalEntity> kanalOptional = _chatKanalRepo.findByName( chatKanalName );
        if ( kanalOptional.isEmpty() ) {

            throw new ChatException( "Zu loeschenden Kanal \"" + chatKanalName + "\" nicht gefunden." );
        }

        final ChatKanalEntity loeschKandidat = kanalOptional.get();

        _chatKanalRepo.delete( loeschKandidat );
        LOG.info( "Chat-Kanal \"{}\" gelöscht.", chatKanalName );
    }

}
