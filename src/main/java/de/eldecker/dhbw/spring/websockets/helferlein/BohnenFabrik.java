package de.eldecker.dhbw.spring.websockets.helferlein;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;


/**
 * Diese Klassen enthält verschiedene mit {@code Bean} annotierte Methoden, die
 * konfigurierte Beans bereitstellen.
 */
@Configuration
public class BohnenFabrik {

    /**
     * Liefert konfiguriertes ObjectMapper-Objekt zurück, welches für Object-nach-JSON 
     * (Serialisierung) oder JSON-nach-Objekt (Deserialisierung) benötigt wird.
     * <br><br>
     *
     * Konfiguration:
     * <ul>
     * <li>Kein Fehler, wenn beim Deserialisierung ein Feld im JSON gefunden wird, das nicht 
     *     in der Zielklasse definiert ist</li>
     *  <li>Das erzeugte JSOn wird für bessere Lesbarkeit durch Einrückungen formatiert.</li>
     * </ul>
     *
     * @return Konfigurierter Object-Mapper
     */
    @Bean
    public ObjectMapper objectMapper() {

        return JsonMapper.builder()
                         .disable( FAIL_ON_UNKNOWN_PROPERTIES )
                         .enable(  INDENT_OUTPUT              )
                         .build();
    }

    
    /**
     * Methode liefert Datumsformatierer für Anzeigedatum.
     * 
     * @return Datumsformatierer für Anzeigedatum im dt. Format.
     */
    @Bean
    public DateTimeFormatter dateTimeFormatter() {

        return DateTimeFormatter.ofPattern( "d. MMMM yyyy, HH:mm 'Uhr'",
                                            new Locale("de") );
    }

}
