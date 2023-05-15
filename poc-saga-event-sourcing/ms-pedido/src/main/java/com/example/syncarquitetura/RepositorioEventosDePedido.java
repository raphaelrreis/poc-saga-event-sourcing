package com.example.syncarquitetura;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEventosDePedido extends MongoRepository<PedidoEvent, String> {

}
