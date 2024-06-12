package de.eldecker.dhbw.spring.websockets.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Diese Klasse sorgt dafür, dass die Attribute vom Typ {@code LocalDateTime}
 * als String im gut lesbaren Format gespeichert werden, z.B. {@code 23. Mai 2024, 23:10:03}.
 */
@Converter( autoApply = true )
public class LocalDateTimeStringConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd.MM.yyyy, HH:mm:ss" );

    @Override
    public String convertToDatabaseColumn( LocalDateTime attribute ) {
        
        return attribute != null ? attribute.format( formatter ) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute( String dbData ) {
        
        try {
            
            return dbData != null ? LocalDateTime.parse( dbData, formatter ) : null;
            
        } catch ( DateTimeParseException ex ) {
            
            throw new IllegalArgumentException( "Ungültiges Datumsformat", ex);
        }
    }
    
}