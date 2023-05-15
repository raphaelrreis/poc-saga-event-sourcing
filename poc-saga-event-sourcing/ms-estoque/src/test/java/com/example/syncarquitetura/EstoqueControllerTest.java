package com.example.syncarquitetura;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

class EstoqueControllerTest {

    @Mock
    private LeitorDeArquivoDeDecisao leitor;
    
    @Mock
    private HttpServletRequest requisicao;
    
    @Mock
    private Atacadista atacadista;

    @InjectMocks
    private EstoqueController controller;

    @BeforeEach
    void setUp(){
    	openMocks(this);
    }

    @Test
    void executarUmaDecisaoBaseadaNoLeitorDeArquivo(){
        when(leitor.obterDecisaoTomada()).thenReturn(Decisao.SUCESSO);
        when(requisicao.getHeaderNames()).thenReturn(new Vector<String>().elements());

        assertThat(controller.reservarEstoque(new PedidoDeReservaDeEstoque(UUID.randomUUID())))
                .isEqualTo(ResponseEntity.ok().build());
    }
}
