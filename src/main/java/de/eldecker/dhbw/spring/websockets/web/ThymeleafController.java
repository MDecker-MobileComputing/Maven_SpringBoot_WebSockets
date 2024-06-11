package de.eldecker.dhbw.spring.websockets.web;

import de.eldecker.dhbw.spring.websockets.db.ChatBeitragEntity;
import de.eldecker.dhbw.spring.websockets.db.ChatBeitragRepo;
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
    
    @GetMapping( "/chat-kanal-historie" )
    public String chatKanalHistorie( Model model,
                                     @RequestParam( value = "kanalname" ) String kanalname ) {
        
        final List<ChatBeitragEntity> beitragListe =  _chatService.getAlleBeitraegeKanal( kanalname );
        
        model.addAttribute( "chatKanalName", kanalname    );
        model.addAttribute( "beitragListe" , beitragListe );
                        
        return "chat-kanal-historie";
    }
    
}
