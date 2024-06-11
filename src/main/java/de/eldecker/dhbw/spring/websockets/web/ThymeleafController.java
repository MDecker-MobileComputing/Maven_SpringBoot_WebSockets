package de.eldecker.dhbw.spring.websockets.web;

import de.eldecker.dhbw.spring.websockets.db.ChatBeitragEntity;
import de.eldecker.dhbw.spring.websockets.db.ChatKanalEntity;
import de.eldecker.dhbw.spring.websockets.db.ChatKanalRepo;
import de.eldecker.dhbw.spring.websockets.service.ChatService;

import java.util.List;

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
    private ChatService _chatService;
    
    @Autowired
    private ChatKanalRepo _chatKanalRepo;
    
    
    @GetMapping( "/chat-kanal-historie" )
    public String chatKanalHistorie( Model model,
                                     @RequestParam( value = "kanalname" ) String kanalname ) {
        
        final List<ChatBeitragEntity> beitragListe =  _chatService.getAlleBeitraegeKanal( kanalname );
        
        model.addAttribute( "chatKanalName", kanalname    );
        model.addAttribute( "beitragListe" , beitragListe );
                        
        return "chat-kanal-historie";
    }
    
    
    @GetMapping( "/chat-kanal-liste" )
    public String chatKanalListe ( Model model ) {
    
        final List<ChatKanalEntity> chatKanalListe = _chatKanalRepo.findAllByOrderByNameAsc();
                
        model.addAttribute( "chatKanalListe", chatKanalListe );
        
        return "chat-kanal-liste";
    }
    
}
