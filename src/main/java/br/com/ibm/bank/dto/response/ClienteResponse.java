package br.com.ibm.bank.dto.response;

import br.com.ibm.bank.models.ClienteEntity;

import java.util.UUID;

public record ClienteResponse(UUID clienteId, String nome, Integer idade, String email, String numeroConta) {

	public ClienteResponse(ClienteEntity clienteEntity) {
		this(clienteEntity.getId(), clienteEntity.getNome(), clienteEntity.getIdade(), clienteEntity.getEmail(), clienteEntity.getNumeroConta());
	}

}
