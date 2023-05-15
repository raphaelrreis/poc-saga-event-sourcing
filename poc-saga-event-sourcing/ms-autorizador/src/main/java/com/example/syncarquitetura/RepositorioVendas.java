package com.example.syncarquitetura;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioVendas extends JpaRepository<Venda, UUID> {
}
