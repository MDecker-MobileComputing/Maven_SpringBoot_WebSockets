package de.eldecker.dhbw.spring.websockets.db;

import static jakarta.persistence.FetchType.EAGER;
import static java.lang.String.format;
import static java.time.Month.JANUARY;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;


@Entity
@Table( name = "CHAT_BEITRAEGE" )
public class ChatBeitragEntity {

    public static final LocalDateTime DUMMY_ZEITPUNKT = LocalDateTime.of( 1970, JANUARY, 1, 0, 0 );

    @Id
    @GeneratedValue
    @Column( name = "id", updatable = false, nullable = false )
    private UUID id;

    @Column( nullable = false )
    private String nickname;

    @Column( nullable = false )
    private String nachricht;

    /** 
     * Zeitpunkt des Beitrags, wird benötigt für Sortierung der Beiträge auf Übersichts-Seite zu Kanal
     * und zur Anzeige erste Beitrag.
     */
    @Column( nullable = false )
    private LocalDateTime zeitpunkt;


    @ManyToOne( fetch = EAGER )
    @JoinColumn( name = "kanal_fk", referencedColumnName = "id" )
    private ChatKanalEntity chatKanal;


    public ChatBeitragEntity() {

        this( "", "", null, DUMMY_ZEITPUNKT );
    }

    public ChatBeitragEntity( String nickName, String nachricht,
                              ChatKanalEntity chatKanal,
                              LocalDateTime   zeitpunkt ) {

        this.nickname  = nickName;
        this.nachricht = nachricht;
        this.chatKanal = chatKanal;
        this.zeitpunkt = zeitpunkt;
    }

    public UUID getId() {

        return id;
    }

    public void setId( UUID id ) {

        this.id = id;
    }

    public String getNickname() {

        return nickname;
    }

    public void setNickname( String nickname ) {

        this.nickname = nickname;
    }

    public String getNachricht() {

        return nachricht;
    }

    public void setNachricht( String nachricht ) {

        this.nachricht = nachricht;
    }

    public void setZeitpunkt( LocalDateTime zeitpunkt ) {

        this.zeitpunkt = zeitpunkt;
    }

    public LocalDateTime getZeitpunkt() {

        return zeitpunkt;
    }

    public ChatKanalEntity getChatKanal() {

        return chatKanal;
    }

    public void setChatKanal( ChatKanalEntity chatKanal ) {

        this.chatKanal = chatKanal;
    }

    @Override
    public String toString() {

        final String str = format( "Chat-Beitrag von \"%s\" im Kanal \"%s\": \"%s\"",
                                   nickname, chatKanal.getName(), nachricht );
        return str;
    }

    @Override
    public int hashCode() {

        return Objects.hash( nickname, nachricht, chatKanal, zeitpunkt );
    }

    @Override
    public boolean equals( Object obj ) {

        if ( obj == this ) { return true; }

        if ( obj == null ) { return false; }

        if ( obj instanceof ChatBeitragEntity andere ) {

            return Objects.equals( nickname , andere.nickname  ) &&
                   Objects.equals( nachricht, andere.nachricht ) &&
                   Objects.equals( chatKanal, andere.chatKanal ) &&
                   Objects.equals( zeitpunkt, andere.zeitpunkt );
        } else {

            return false;
        }
    }
}
