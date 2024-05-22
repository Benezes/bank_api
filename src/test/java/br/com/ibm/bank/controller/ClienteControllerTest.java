package br.com.ibm.bank.controller;

import br.com.ibm.bank.dto.request.ClienteRequest;
import br.com.ibm.bank.dto.response.ClienteResponse;
import br.com.ibm.bank.dto.response.ExtratoResponse;
import br.com.ibm.bank.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

	public static final String API_CLIENTES = "/api/clientes";

	private UUID clienteId;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private ClienteRepository clienteRepository;


	@Test
	public void testPostCliente() {
		ClienteRequest request = new ClienteRequest(UUID.randomUUID(), "teste", 24, "teste@teste.com", "123987623");

		ResponseEntity<ClienteResponse> response = restTemplate.postForEntity(API_CLIENTES, request, ClienteResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(request.nome(), response.getBody().nome());

		clienteId = response.getBody().clienteId();
	}

	@Test
	public void testPostClienteEmailExistente() {
		ClienteRequest request = new ClienteRequest(UUID.randomUUID(), "teste", 24, "teste@teste.com", "123987623");
		ResponseEntity<ClienteResponse> response = restTemplate.postForEntity(API_CLIENTES, request, ClienteResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());

		ClienteRequest requestBad = new ClienteRequest(UUID.randomUUID(), "teste", 24, request.email(), "123987623");
		ResponseEntity<ClienteResponse> responseBad = restTemplate.postForEntity(API_CLIENTES, requestBad, ClienteResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseBad.getStatusCode());
		clienteId = response.getBody().clienteId();
	}

	@Test
	public void testGetExtratoByEmailCliente() {
		ClienteRequest request = new ClienteRequest(UUID.randomUUID(), "teste", 24, "teste@teste.com", "123987623");
		ResponseEntity<ClienteResponse> response = restTemplate.postForEntity(API_CLIENTES, request, ClienteResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());

		ResponseEntity<ExtratoResponse> responseExtrato = restTemplate.getForEntity(API_CLIENTES + "/" + response.getBody().email(), ExtratoResponse.class);

		assertNotNull(responseExtrato.getBody());
		assertEquals(HttpStatus.OK, responseExtrato.getStatusCode());
		clienteId = response.getBody().clienteId();
	}

	@Test
	public void testGetExtratoByEmailClienteInvalido() {
		ResponseEntity<ExtratoResponse> response = restTemplate.getForEntity(API_CLIENTES + "/email_inexistente@email.com", ExtratoResponse.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testUpdateCliente() {
		ClienteRequest request = new ClienteRequest(UUID.randomUUID(), "teste", 24, "teste@teste.com", "123987623");
		ResponseEntity<ClienteResponse> response = restTemplate.postForEntity(API_CLIENTES, request, ClienteResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());

		ClienteRequest novoRequest = new ClienteRequest(UUID.randomUUID(), "outro teste", 24, "outro_teste@teste.com", "0123");
		ResponseEntity<ClienteResponse> responseCliente = restTemplate.exchange(API_CLIENTES + "/" + response.getBody().email(), HttpMethod.PUT, new HttpEntity<>(novoRequest), ClienteResponse.class);

		assertNotNull(responseCliente.getBody());
		assertEquals(HttpStatus.OK, responseCliente.getStatusCode());
		clienteId = response.getBody().clienteId();
	}

	@Test
	public void testUpdateClienteEmailInexistente() {
		ClienteRequest novoRequest = new ClienteRequest(UUID.randomUUID(), "outro teste", 24, "outro_teste@teste.com", "0123");
		ResponseEntity<ClienteResponse> responseCliente = restTemplate.exchange(API_CLIENTES + "/email_inexistente@email.com", HttpMethod.PUT, new HttpEntity<>(novoRequest), ClienteResponse.class);

		assertEquals(HttpStatus.NOT_FOUND, responseCliente.getStatusCode());
	}

	@Test
	public void testDeleteCliente() {
		ClienteRequest request = new ClienteRequest(UUID.randomUUID(), "teste", 24, "teste@teste.com", "123987623");
		ResponseEntity<ClienteResponse> response = restTemplate.postForEntity(API_CLIENTES, request, ClienteResponse.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());

		restTemplate.delete(API_CLIENTES + "/" + response.getBody().email());

		clienteId = response.getBody().clienteId();
	}

	@Test
	public void testDeleteClienteInexistente() {
		restTemplate.delete(API_CLIENTES + "/email_inexistente@email.com");
	}

	@AfterEach
	public void tearDown() {
		if(clienteId != null) {
			clienteRepository.deleteById(clienteId);
		}
	}
}