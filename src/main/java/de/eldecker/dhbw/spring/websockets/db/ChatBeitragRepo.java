package de.eldecker.dhbw.spring.websockets.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatBeitragRepo extends JpaRepository<ChatBeitragEntity, UUID>{

    List<ChatBeitragEntity> findByChatKanal( ChatKanalEntity chatKanalEntity );
}
