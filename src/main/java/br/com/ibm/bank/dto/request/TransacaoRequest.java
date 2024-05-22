package br.com.ibm.bank.dto.request;

import br.com.ibm.bank.models.TipoTransacaoEnum;

import java.math.BigDecimal;

public record TransacaoRequest(
		BigDecimal valor,
		TipoTransacaoEnum tipoTransacao,
		String emailDestino
) {
}