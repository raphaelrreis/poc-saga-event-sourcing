package com.example.syncarquitetura;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositorioEventosDeVenda extends JpaRepository<Event, UUID> {

}
