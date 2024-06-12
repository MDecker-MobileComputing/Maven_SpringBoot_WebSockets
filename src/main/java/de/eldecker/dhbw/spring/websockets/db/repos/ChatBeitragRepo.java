package de.eldecker.dhbw.spring.websockets.db.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.eldecker.dhbw.spring.websockets.db.entities.ChatBeitragEntity;
import de.eldecker.dhbw.spring.websockets.db.entities.ChatKanalEntity;


public interface ChatBeitragRepo extends JpaRepository<ChatBeitragEntity, UUID>{

    List<ChatBeitragEntity> findByChatKanal( ChatKanalEntity chatKanalEntity );
}
