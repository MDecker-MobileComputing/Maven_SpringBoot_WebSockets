package de.eldecker.dhbw.spring.websockets.helferlein;

import static java.lang.String.format;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;


/**
 * Diese Bean liefert eine zufällige Zeitspanne in Millisekunden zurück, die
 * als Zeit zwischen zwei über Websocket verschickten Schlagzeilen genutzt wird.
 */
@Component
public class ZufallsDelay {

    private final static Logger LOG = LoggerFactory.getLogger( ZufallsDelay.class );

    /** Minimale Delay-Zeit von 3 Sekunden. */
    private static final int DELAY_MILLISEKUNDEN_MIN =  3_000;

    /** Maximale Delay-Zeit von 12 Sekunden. */
    private static final int DELAY_MILLISEKUNDEN_MAX = 12_000;


    /** Zufallsgenerator */
    private Random _random = new Random();


    /**
     * Erzeugt eine zufällige Zeit in Millisekunden, die als Delay zwischen
     * zwei Schlagzeilen-Nachrichten genutzt wird.
     *
     * @return Zufällige Zeit in Millisekunden; die Werte ligen zwischen
     *         {@value #DELAY_MILLISEKUNDEN_MIN} und {@value #DELAY_MILLISEKUNDEN_MAX}.
     */
    public synchronized int getZufallsDelayZeit() {

        final int zufallsDelay = _random.nextInt( DELAY_MILLISEKUNDEN_MIN,
                                                  DELAY_MILLISEKUNDEN_MAX );

        LOG.info( format("Zufalls-Delay: %,d ms", zufallsDelay) );

        return zufallsDelay;
    }

}
