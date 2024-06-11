package de.eldecker.dhbw.spring.websockets.db;

import static jakarta.persistence.FetchType.EAGER;

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

    @Id
    @GeneratedValue
    @Column( name = "id", updatable = false, nullable = false )
    private UUID id;
        
    @Column( nullable = false )
    private String nickname;
    
    @Column( nullable = false )
    private String nachricht;

    @ManyToOne( fetch = EAGER )
    @JoinColumn( name = "kanal_fk", referencedColumnName = "id" )
    private ChatKanalEntity chatKanal;

    
    public ChatBeitragEntity() {
        
        this( "", "", null );
    }
    
    public ChatBeitragEntity( String nickName, String nachricht, ChatKanalEntity chatKanal ) {
    
        this.nickname  = nickName;
        this.nachricht = nachricht;
        this.chatKanal = chatKanal;
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

    public ChatKanalEntity getChatKanal() {
        
        return chatKanal;
    }

    public void setChatKanal( ChatKanalEntity chatKanal ) {
        
        this.chatKanal = chatKanal;
    }
        
    
}
