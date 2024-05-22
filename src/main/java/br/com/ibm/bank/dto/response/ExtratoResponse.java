package br.com.ibm.bank.dto.response;

import br.com.ibm.bank.models.ClienteEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ExtratoResponse(
		UUID clienteId,
		String nome,
		Integer idade,
		String email,
		String numeroConta,
		List<TransacaoResponse> transacoes,
		BigDecimal saldo
) {

	public ExtratoResponse(ClienteEntity clienteEntity, BigDecimal saldo) {
		this(
				clienteEntity.getId(),
				clienteEntity.getNome(),
				clienteEntity.getIdade(),
				clienteEntity.getEmail(),
				clienteEntity.getNumeroConta(),
				clienteEntity.getTransacoes().stream().map(transacaoResponse -> new TransacaoResponse(
						transacaoResponse.getValor(),
						transacaoResponse.getTipo(),
						transacaoResponse.getCreatedAt()
				)).collect(Collectors.toList()),
				saldo
		);
	}

}