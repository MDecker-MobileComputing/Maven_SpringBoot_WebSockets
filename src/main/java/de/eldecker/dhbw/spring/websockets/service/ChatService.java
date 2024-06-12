package de.eldecker.dhbw.spring.websockets.service;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.eldecker.dhbw.spring.websockets.db.ChatBeitragEntity;
import de.eldecker.dhbw.spring.websockets.db.ChatBeitragRepo;
import de.eldecker.dhbw.spring.websockets.db.ChatKanalEntity;
import de.eldecker.dhbw.spring.websockets.db.ChatKanalRepo;
import de.eldecker.dhbw.spring.websockets.model.ChatException;
import de.eldecker.dhbw.spring.websockets.model.ChatNachricht;


@Service
public class ChatService {

    private final static Logger LOG = LoggerFactory.getLogger( ChatService.class );

    @Autowired
    private ChatBeitragRepo _chatBeitragRepo;

    @Autowired
    private ChatKanalRepo _chatKanalRepo;


    public void neuerChatKanal( String chatKanalName ) {

        ChatKanalEntity chatKanal = new ChatKanalEntity( chatKanalName );

        chatKanal = _chatKanalRepo.save( chatKanal );

        LOG.info( "Neuen Chat-Kanal \"{}\" mit UUID={} auf DB gespeichert.",
                  chatKanalName, chatKanal.getId() );
    }


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

        LOG.info( "Chat-Beitrag von \"{}\" auf Kanal \"{}\" gespeichert.",
                  chatNachricht.getNickname(), chatKanalName );
    }


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
