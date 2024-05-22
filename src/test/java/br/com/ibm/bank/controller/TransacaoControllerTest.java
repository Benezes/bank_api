package br.com.ibm.bank.controller;

import br.com.ibm.bank.dto.request.ClienteRequest;
import br.com.ibm.bank.dto.request.TransacaoRequest;
import br.com.ibm.bank.dto.response.ClienteResponse;
import br.com.ibm.bank.dto.response.TransacaoResponse;
import br.com.ibm.bank.models.TipoTransacaoEnum;
import br.com.ibm.bank.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransacaoControllerTest {

	public static final String API_TRANSACOES = "/api/transacoes";
	public static final String API_CLIENTES = "/api/clientes";

	private UUID clienteId;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	public void testPostTransacaoCredito() {
		ClienteRequest request = new ClienteRequest(UUID.randomUUID(), "teste", 24, "teste@teste.com", "123987623");
		ResponseEntity<ClienteResponse> response = restTemplate.postForEntity(API_CLIENTES, request, ClienteResponse.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(request.nome(), response.getBody().nome());

		TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("100.0"), TipoTransacaoEnum.CREDITO, response.getBody().clienteId());
		ResponseEntity<TransacaoResponse> responseTransacao = restTemplate.postForEntity(API_TRANSACOES, transacaoRequest, TransacaoResponse.class);

		assertEquals(HttpStatus.CREATED, responseTransacao.getStatusCode());
		assertNotNull(responseTransacao.getBody());
		assertEquals(transacaoRequest.valor(), responseTransacao.getBody().valor());

		clienteId = response.getBody().clienteId();
	}

	@Test
	public void testPostTransacaoCreditoValorNegativo() {
		ClienteRequest request = new ClienteRequest(UUID.randomUUID(), "teste", 24, "teste@teste.com", "123987623");
		ResponseEntity<ClienteResponse> response = restTemplate.postForEntity(API_CLIENTES, request, ClienteResponse.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(request.nome(), response.getBody().nome());

		TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("-100.0"), TipoTransacaoEnum.CREDITO, response.getBody().clienteId());
		ResponseEntity<TransacaoResponse> responseTransacao = restTemplate.postForEntity(API_TRANSACOES, transacaoRequest, TransacaoResponse.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseTransacao.getStatusCode());

		clienteId = response.getBody().clienteId();
	}

	@Test
	public void testPostTransacaoCreditoEmailInexistente() {
		TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("100.0"), TipoTransacaoEnum.CREDITO, UUID.randomUUID());
		ResponseEntity<TransacaoResponse> responseTransacao = restTemplate.postForEntity(API_TRANSACOES, transacaoRequest, TransacaoResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, responseTransacao.getStatusCode());
	}

	@AfterEach
	public void tearDown() {
		if(clienteId != null) {
			clienteRepository.deleteById(clienteId);
		}
	}
}