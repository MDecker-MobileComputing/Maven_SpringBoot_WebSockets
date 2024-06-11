package de.eldecker.dhbw.spring.websockets.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatBeitragRepo extends JpaRepository<ChatBeitragEntity, UUID>{

}
