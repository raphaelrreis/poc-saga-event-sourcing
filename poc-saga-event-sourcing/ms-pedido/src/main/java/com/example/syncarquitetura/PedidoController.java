package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pedidos")
class PedidoController {

    private PedidoService pedidoService;

    static final String MENSAGEM_PEDIDO_RECEBIDO_SUCESSO = "Pedido recebido com sucesso";
    static final String MENSAGEM_ERRO_PEDIDO_RECEBIDO = "Erro";

    @Autowired
    PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    ResponseEntity<String> criarPedido(@RequestBody InformacoesDoPedido informacoesDoPedido){
    	log.info("Recebendo informações do pedido " + informacoesDoPedido);
    	try {
    		pedidoService.criarPedido(informacoesDoPedido);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MENSAGEM_PEDIDO_RECEBIDO_SUCESSO);
    	} catch(RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MENSAGEM_ERRO_PEDIDO_RECEBIDO);
    	}
    }
    
}
