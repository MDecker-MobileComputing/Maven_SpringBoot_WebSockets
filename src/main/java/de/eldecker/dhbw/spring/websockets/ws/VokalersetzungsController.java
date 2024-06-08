package de.eldecker.dhbw.spring.websockets.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


/**
 * Controller für STOMP auf Basis von WebSockets, der zu jeder Textnachricht vom
 * Client (Browser) eine Antwort zurückgesendet wird.
 * <br><br>
 *
 * siehe auch <a href="https://spring.io/guides/gs/messaging-stomp-websocket/">dieses Tutorial</a>
 */
@Controller
public class VokalersetzungsController {

  @MessageMapping("/vokalersetzung_input")
  @SendTo("/topic/vokalersetzungs_output")
  public String vokaleErsetzen( String textVomClient ) throws Exception {

        final String ergebnisString = textVomClient.replaceAll( "[aeiou]", "o" );
        return ergebnisString;
  }

}
