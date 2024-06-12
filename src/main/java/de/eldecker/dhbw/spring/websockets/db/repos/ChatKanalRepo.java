package de.eldecker.dhbw.spring.websockets.db.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.eldecker.dhbw.spring.websockets.db.entities.ChatKanalEntity;


public interface ChatKanalRepo extends JpaRepository<ChatKanalEntity, UUID> {

    Optional<ChatKanalEntity> findByName( String kanalName );
    
    List<ChatKanalEntity> findAllByOrderByNameAsc();

}
