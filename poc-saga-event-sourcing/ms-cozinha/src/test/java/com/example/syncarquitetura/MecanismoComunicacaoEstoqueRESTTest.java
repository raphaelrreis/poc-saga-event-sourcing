package com.example.syncarquitetura;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static java.util.UUID.randomUUID;
import static java.util.stream.IntStream.rangeClosed;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MSCozinhaApplication.class)
@DirtiesContext
public class MecanismoComunicacaoEstoqueRESTTest {

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private MecanismoComunicacaoEstoqueREST estoque;

    @Value("${ms-estoque.url}")
    private String urlMsEstoque;

    @Autowired
    private RetryRegistry retryRegistry;

    private static final String MS_ESTOQUE_CB = "ms-estoque-cb";

    private PedidoDeReservaDeEstoque pedido = new PedidoDeReservaDeEstoque(randomUUID());
    private PedidoDeReservaDeEstoque outroPedido = new PedidoDeReservaDeEstoque(randomUUID());

    @BeforeEach
    public void setUp(){
        circuitBreakerRegistry.circuitBreaker(MS_ESTOQUE_CB).reset();
        resetRetry(MS_ESTOQUE_CB);
    }

    private void resetRetry(String name) {
        Retry old = retryRegistry.retry(name);
        retryRegistry.remove(name);
        retryRegistry.retry(name, old.getRetryConfig());
    }

    @Test
    public void abrirEFecharOCircuitoParaVerificarNumeroMinimoDeChamadas() throws InterruptedException {
        when(restTemplate.postForEntity(urlMsEstoque, pedido, Void.class))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        rangeClosed(1,5).forEach((count) -> {
            try{
                estoque.separarIngredientes(pedido);
            }catch(RuntimeException e){}
        });

        estadoAtualDoCircuitBreakerDeveSer(CircuitBreaker.State.OPEN);

        Thread.sleep(2000);

        when(restTemplate.postForEntity(urlMsEstoque, outroPedido, Void.class))
                .thenReturn(ResponseEntity.ok().build());
        rangeClosed(1,3).forEach((contador) -> estoque.separarIngredientes(outroPedido));

        estadoAtualDoCircuitBreakerDeveSer(CircuitBreaker.State.CLOSED);
    }

//    @Test
//    public void shouldOpenAndCloseBackendACircuitBreakerToCheckSlidingWindowSize() throws InterruptedException {
//        // When
//        Stream.rangeClosed(1,10).forEach((count) -> produceSuccess(MS_ESTOQUE_CB));
//        Stream.rangeClosed(1,4).forEach((count) -> produceFailure(MS_ESTOQUE_CB));
//        Stream.rangeClosed(1,10).forEach((count) -> produceSuccess(MS_ESTOQUE_CB));
//        checkHealthStatus(MS_ESTOQUE_CB, CircuitBreaker.State.CLOSED);
//        Stream.rangeClosed(1,5).forEach((count) -> produceFailure(MS_ESTOQUE_CB));
//        // Then
//        checkHealthStatus(MS_ESTOQUE_CB, CircuitBreaker.State.OPEN);
//        //waitDurationInOpenState
//        Thread.sleep(2000);
//        // Check Change to closed again
//        Stream.rangeClosed(1,3).forEach((count) -> produceSuccess(MS_ESTOQUE_CB));
//        checkHealthStatus(MS_ESTOQUE_CB, CircuitBreaker.State.CLOSED);
//    }
//
//    @Test
//    public void shouldRetryTwoTimes() {
//        produceFailure(MS_ESTOQUE_CB);
//        checkNumberOfFailedCallsMetrics(MS_ESTOQUE_CB, 1l);
//    }
//
//    @Test
//    public void shouldRetryThreeTimesAndOpenCircuitBreaker() {
//        Stream.rangeClosed(1,2).forEach((count) -> produceSuccess(MS_ESTOQUE_CB));
//        Stream.rangeClosed(1,2).forEach((count) -> produceFailure(MS_ESTOQUE_CB));
//        checkHealthStatus(MS_ESTOQUE_CB, CircuitBreaker.State.OPEN);
//        checkNumberOfFailedCallsMetrics(MS_ESTOQUE_CB, 2l);
//    }

    private void checkNumberOfFailedCallsMetrics(String backend, Long count) {
        assertThat(retryRegistry.retry(backend).getMetrics().getNumberOfFailedCallsWithRetryAttempt()).isEqualTo(count);
    }

    private void checkHealthStatus(String circuitBreakerName, CircuitBreaker.State state) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName);
        assertThat(circuitBreaker.getState()).isEqualTo(state);
    }

    private void estadoAtualDoCircuitBreakerDeveSer(CircuitBreaker.State estadoAtual) {
        assertThat(circuitBreakerRegistry.circuitBreaker(MS_ESTOQUE_CB).getState()).isEqualTo(estadoAtual);
    }

    private void produceFailure(String backend) {
        ResponseEntity<String> response = restTemplate.getForEntity("/" + backend + "/failure", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void produceSuccess(String backend) {
        ResponseEntity<String> response = restTemplate.getForEntity("/" + backend + "/success", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
