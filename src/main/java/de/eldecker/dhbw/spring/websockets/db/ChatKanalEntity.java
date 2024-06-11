package de.eldecker.dhbw.spring.websockets.db;

import static jakarta.persistence.CascadeType.REMOVE;
import static java.time.Month.JANUARY;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
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

    public static final LocalDateTime DUMMY_ZEITPUNKT = LocalDateTime.of( 1970, JANUARY, 1, 0, 0 );

    @Id
    @GeneratedValue
    @Column( name = "id", updatable = false, nullable = false )
    private UUID id;

    @Column( nullable = false, unique = true )
    private String name;

    /** Zeitpunkt (Datum+Zeit), zu dem der Chat-Kanal gestartet wurde. */
    @Column( nullable = false )
    private LocalDateTime gestartet;

    /**
     * Attribut {@code cascade} von Annotation {@code OneToMany} muss
     * auf {@code REMOVE} gesetzt werden, damit die zugehörigen 
     * {@link ChatBeitragEntity}-Objekte auch gelöscht werden, wenn
     * ein {@link ChatKanalEntity}-Objekt gelöscht wird.
     */
    @OneToMany( mappedBy = "chatKanal", cascade = REMOVE )
    @OrderBy( "id ASC" )
    private List<ChatBeitragEntity> beitraege = new ArrayList<>( 10 );


    public ChatKanalEntity() {

        this ( "", DUMMY_ZEITPUNKT );
    }

    public ChatKanalEntity( String kanalName, LocalDateTime zeitpunkt ) {

        this.name      = kanalName;
        this.gestartet = zeitpunkt;
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

    public LocalDateTime getGestartet() {

        return gestartet;
    }

    public void setGestartet( LocalDateTime gestartet ) {

        this.gestartet = gestartet;
    }

    public List<ChatBeitragEntity> getBeitraege() {

        return beitraege;
    }

    public void setBeitraege( List<ChatBeitragEntity> beitraege ) {

        this.beitraege = beitraege;
    }



}