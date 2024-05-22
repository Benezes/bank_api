package br.com.ibm.bank.controller;

import br.com.ibm.bank.dto.request.ClienteRequest;
import br.com.ibm.bank.dto.response.ClienteResponse;
import br.com.ibm.bank.dto.response.ExtratoResponse;
import br.com.ibm.bank.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
	public ResponseEntity<ClienteResponse> postCliente(@RequestBody @Valid ClienteRequest clienteRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.createCliente(clienteRequest));
	}

	@GetMapping
	public ResponseEntity<Page<ClienteResponse>> getExtratoByEmail(
			@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC)
			Pageable pageable) {
		return ResponseEntity.ok(clienteService.getAllClientes(pageable));
	}

	@GetMapping(value = "/{email}")
	public ResponseEntity<ExtratoResponse> getExtratoByEmail(@PathVariable String email) {
		return ResponseEntity.ok(clienteService.getExtratoByEmailCliente(email));
	}

	@PutMapping(value = "/{email}")
	public ResponseEntity<ClienteResponse> putCliente(
			@PathVariable String email,
			@RequestBody @Valid ClienteRequest clienteRequest) {
		return ResponseEntity.ok(clienteService.updateCliente(email, clienteRequest));
	}

	@DeleteMapping(value = "/{email}")
	public ResponseEntity<Void> deleteCliente(@PathVariable String email) {
		clienteService.deleteCliente(email);
		return ResponseEntity.noContent().build();
	}
}
