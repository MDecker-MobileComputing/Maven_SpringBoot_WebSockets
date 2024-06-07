package de.eldecker.dhbw.spring.websockets.helferlein;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;


@Component
public class ZufallsDelay {

    public int getRandomDelay() {

        return ThreadLocalRandom.current().nextInt(5_000, 10_001); // Random delay between 5 and 10 seconds
    }

}
