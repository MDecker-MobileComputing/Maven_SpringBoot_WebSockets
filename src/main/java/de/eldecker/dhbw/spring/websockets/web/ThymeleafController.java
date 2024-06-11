package de.eldecker.dhbw.spring.websockets.web;

import de.eldecker.dhbw.spring.websockets.db.ChatBeitragEntity;
import de.eldecker.dhbw.spring.websockets.db.ChatKanalEntity;
import de.eldecker.dhbw.spring.websockets.db.ChatKanalRepo;
import de.eldecker.dhbw.spring.websockets.model.ChatException;
import de.eldecker.dhbw.spring.websockets.service.ChatService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping( "/app/" )
public class ThymeleafController {

    @Autowired
    private ChatKanalRepo _chatKanalRepo;


    @GetMapping( "/chat-kanal-liste" )
    public String chatKanalListe ( Model model ) {

        final List<ChatKanalEntity> chatKanalListe = _chatKanalRepo.findAllByOrderByNameAsc();

        model.addAttribute( "chatKanalListe", chatKanalListe );

        return "chat-kanal-liste";
    }

    
    @GetMapping( "/chat-kanal-historie" )
    public String chatKanalHistorie( Model model,
                                     @RequestParam( value = "uuid", required = true ) UUID uuid ) 
                throws ChatException {

        final Optional<ChatKanalEntity> kanalOptional = _chatKanalRepo.findById( uuid );
        if ( kanalOptional.isEmpty() ) {
            
            throw new ChatException( "Chat-Kanal mit UUID=" + uuid + " nicht gefunden" );
        }
        
        final ChatKanalEntity kanalEntity = kanalOptional.get();

        model.addAttribute( "chatKanal"    , kanalEntity                );
        model.addAttribute( "beitragListe" , kanalEntity.getBeitraege() );

        return "chat-kanal-historie";
    }


    @GetMapping( "/chat-kanal-loeschen" )
    public String chatKanalLoeschen( Model model,
                                     @RequestParam( value = "uuid", required = true ) UUID uuid ) 
                      throws ChatException{
        
        
        final Optional<ChatKanalEntity> kanalOptional = _chatKanalRepo.findById( uuid );
        
        if ( kanalOptional.isEmpty() ) {
            
            throw new ChatException( "Zu loeschender Kanal mit UUID=" + uuid + " nicht gefunden" );
        }
        
        final ChatKanalEntity kanalEntity = kanalOptional.get();
        
        final String chatKanalName = kanalEntity.getName();
        final int anzahlBeitraege  = kanalEntity.getBeitraege().size();
        
        _chatKanalRepo.delete( kanalEntity );
                
        model.addAttribute( "meldung", 
                            "Chat-Kanal \"" + chatKanalName + "\" mit " + anzahlBeitraege + " wurde gelöscht." );
        
        return "chat-kanal-loesch-ergebnis";
    }

}
