package de.eldecker.dhbw.spring.websockets.db.entities;

import static jakarta.persistence.CascadeType.REMOVE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;


@Entity
@Table( name = "CHAT_KANAL" )
public class ChatKanalEntity {

    /** UUID: 128-Bit-Wert, z.B. {@code b69fefb7-4ccf-4b7d-90a9-dedfed01b4cf} */
    @Id
    @GeneratedValue
    @Column( name = "id", updatable = false, nullable = false )
    private UUID id;

    /**
     * Kanalname, der eindeutig sein muss; wegen {@code unique = true} wird
     * automatisch ein Index auf die Spalte gelegt, der die Suche nach einem
     * Kanalnamen beschleunigt.
     */
    @Column( nullable = false, unique = true )
    private String name;

    /**
     * Attribut {@code cascade} von Annotation {@code OneToMany} muss
     * auf {@code REMOVE} gesetzt werden, damit die zugehörigen
     * {@link ChatBeitragEntity}-Objekte auch gelöscht werden, wenn
     * ein {@link ChatKanalEntity}-Objekt gelöscht wird.
     */
    @OneToMany( mappedBy = "chatKanal", cascade = REMOVE )
    @OrderBy( "zeitpunkt ASC" )
    private List<ChatBeitragEntity> beitraege = new ArrayList<>( 10 );


    public ChatKanalEntity() {

        this ( "" );
    }

    public ChatKanalEntity( String kanalName ) {

        this.name = kanalName;
    }

    public UUID getId() {

        return id;
    }

    public void setId( UUID id ) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName( String name ) {

        this.name = name;
    }

    public List<ChatBeitragEntity> getBeitraege() {

        return beitraege;
    }

    public void setBeitraege( List<ChatBeitragEntity> beitraege ) {

        this.beitraege = beitraege;
    }


    public Optional<LocalDateTime> getFruehesterBeitragZeitpunkt() {

        return this.beitraege.stream()
                             .map( ChatBeitragEntity::getZeitpunkt )
                             .min( LocalDateTime::compareTo );
    }

    @Override
    public String toString() {

        return String.format( "Chat-Kanal mit Name \"%s\" und %d Beiträgen.",
                              name, beitraege.size() );
    }

    @Override
    public int hashCode() {

        return Objects.hash( name, beitraege );
    }

    @Override
    public boolean equals( Object obj ) {

        if ( obj == this ) { return true; }

        if ( obj == null ) { return false; }

        if ( obj instanceof ChatKanalEntity anderer ) {

            return Objects.equals( name     , anderer.name      ) &&
                   Objects.equals( beitraege, anderer.beitraege );

        } else {

                return false;
            }
    }

}
