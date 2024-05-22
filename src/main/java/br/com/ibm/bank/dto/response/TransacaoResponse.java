package br.com.ibm.bank.dto.response;

import br.com.ibm.bank.models.TipoTransacaoEnum;
import br.com.ibm.bank.models.TransacaoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(
		BigDecimal valor,
		TipoTransacaoEnum tipoTransacao,
		LocalDateTime createdAt
) {
	public TransacaoResponse(TransacaoEntity transacaoEntity) {
		this(transacaoEntity.getValor(), transacaoEntity.getTipo(), transacaoEntity.getCreatedAt());
	}
}
