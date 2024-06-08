package de.eldecker.dhbw.spring.websockets.helferlein;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;


@Component
public class ZufallsDelay {

    private static final int DELAY_MILLISEKUNDEN_MIN =  5_000;
    private static final int DELAY_MILLISEKUNDEN_MAX = 10_000;
    
    public int getRandomDelay() {

        return ThreadLocalRandom.current()
                                .nextInt( DELAY_MILLISEKUNDEN_MIN, 
                                          DELAY_MILLISEKUNDEN_MAX ); 
    }

}
