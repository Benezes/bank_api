package br.com.ibm.bank.models;

public enum TipoTransacaoEnum {
	DEBITO("Débito"),
	CREDITO("Crédito");

	private final String descricao;

	TipoTransacaoEnum(final String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
