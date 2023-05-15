package com.example.syncarquitetura;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaixaDeSaida extends MongoRepository<CaixaDeSaidaEvent, String> {

}
