package com.example.syncarquitetura;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioEventosConsumidor extends MongoRepository<ConsumidorVerificadoEvent, UUID> {

}
