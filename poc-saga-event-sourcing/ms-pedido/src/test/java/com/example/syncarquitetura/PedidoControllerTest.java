package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.informacoesDoPedido;
import static com.example.syncarquitetura.ObjetosParaTestes.toJson;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = PedidoController.class)
class PedidoControllerTest {

    @MockBean
    private PedidoService service;

    @Autowired
    private MockMvc mockMvc;
    
    private final InformacoesDoPedido informacoesDoPedido = informacoesDoPedido(BigDecimal.TEN);

    @Test
    void receberUmPedidoComSucesso() throws Exception {
        mockMvc.perform(
                post("/pedidos")
                        .content(toJson(informacoesDoPedido))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(PedidoController.MENSAGEM_PEDIDO_RECEBIDO_SUCESSO));
        
        verify(service).criarPedido(informacoesDoPedido);
    }

    @Test
    void erroAoReceberUmPedido() throws Exception {
    	doThrow(RuntimeException.class).when(service).criarPedido(informacoesDoPedido);

        mockMvc.perform(
                        post("/pedidos")
                                .content(toJson(informacoesDoPedido))
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(PedidoController.MENSAGEM_ERRO_PEDIDO_RECEBIDO));
    }
}
