package br.com.ibm.bank.service;

import br.com.ibm.bank.dto.request.ClienteRequest;
import br.com.ibm.bank.dto.response.ClienteResponse;
import br.com.ibm.bank.dto.response.ExtratoResponse;
import br.com.ibm.bank.models.ClienteEntity;
import br.com.ibm.bank.models.TransacaoEntity;
import br.com.ibm.bank.repository.ClienteRepository;
import br.com.ibm.bank.service.exception.ClienteException;
import br.com.ibm.bank.service.exception.ClienteNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ClienteService {

	@Autowired
	public ClienteRepository clienteRepository;

	private static BigDecimal calculaSaldo(ClienteEntity clienteEntity) {
		BigDecimal saldo = BigDecimal.ZERO;
		for(TransacaoEntity transacoes : clienteEntity.getTransacoes()) {
			saldo = saldo.add(transacoes.getValor());
		}
		return saldo;
	}

	@Transient
	public ClienteResponse createCliente(ClienteRequest clienteRequest) {
		if(clienteRepository.findByEmail(clienteRequest.email()).isPresent() ||
				clienteRepository.findByNumeroConta(clienteRequest.numeroConta()).isPresent()
		) {
			throw new ClienteException("E-mail já cadastrado");
		}

		ClienteEntity clienteEntity = new ClienteEntity(clienteRequest.nome(), clienteRequest.idade(), clienteRequest.email(), clienteRequest.numeroConta(), null, UUID.randomUUID());

		clienteEntity = clienteRepository.save(clienteEntity);

		return new ClienteResponse(clienteEntity);
	}

	public ExtratoResponse getExtratoByEmailCliente(String email) {
		ClienteEntity clienteEntity = clienteRepository.findByEmail(email).orElseThrow(() -> new ClienteNotFoundException("E-mail não encontrado"));

		BigDecimal saldo = calculaSaldo(clienteEntity);

		return new ExtratoResponse(clienteEntity, saldo);
	}

	@Transient
	public ClienteResponse updateCliente(String email, ClienteRequest clienteRequest) {
		ClienteEntity clienteEntity = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new ClienteNotFoundException("E-mail não encontrado"));

		BeanUtils.copyProperties(clienteRequest, clienteEntity, "email");
		clienteRepository.save(clienteEntity);

		return new ClienteResponse(clienteEntity);
	}

	@Transactional
	public void deleteCliente(String email) {
		ClienteEntity clienteEntity = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new ClienteException("E-mail não encontrado"));

		clienteRepository.deleteById(clienteEntity.getId());
	}

	public Page<ClienteResponse> getAllClientes(Pageable pageable) {
		return clienteRepository.findAll(pageable).map(ClienteResponse::new);
	}
}
