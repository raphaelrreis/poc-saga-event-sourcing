package com.example.syncarquitetura;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositorioComandas extends JpaRepository<Comanda, UUID> {

    Comanda findByCorrelationId(UUID correlationId);
}
