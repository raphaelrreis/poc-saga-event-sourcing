package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ConsumidorServiceTest {

    @Mock
    private GerenciadorDaSaga saga;
    
    @Mock
    private RepositorioEventosConsumidor repositorioEventosConsumidor;

    @InjectMocks
    private ConsumidorService service;

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    void verificarUmConsumidor(){
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent();
        service.verificarConsumidor(evento);

        verify(repositorioEventosConsumidor).save(evento.toConsumidorVerificadoEvent());
        verify(saga).consumidorVerificado(evento.toConsumidorVerificadoEvent());
    }
}
