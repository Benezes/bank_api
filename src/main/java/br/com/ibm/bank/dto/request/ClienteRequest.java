package br.com.ibm.bank.dto.request;

import java.util.UUID;

public record ClienteRequest(
		UUID id,
		String nome,
		Integer idade,
		String email,
		String numeroConta
) {
}
