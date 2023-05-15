package com.example.syncarquitetura;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = "id", callSuper = true)
@Document(collection = "eventos_pedido")
@ToString(callSuper = true, exclude = "id")
abstract class PedidoEvent extends Evento {

    @JsonIgnore
    @Id
    @Getter
    private String id;

    PedidoEvent(UUID correlationId){
        super(correlationId);
    }
}
