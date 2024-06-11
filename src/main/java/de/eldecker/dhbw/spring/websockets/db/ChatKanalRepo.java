package de.eldecker.dhbw.spring.websockets.db;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatKanalRepo extends JpaRepository<ChatKanalEntity, UUID> {

    Optional<ChatKanalEntity> findByName( String kanalName );

}
