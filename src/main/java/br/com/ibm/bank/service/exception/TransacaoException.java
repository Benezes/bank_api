package br.com.ibm.bank.service.exception;

public class TransacaoException extends RuntimeException {

	public TransacaoException() {
	}

	public TransacaoException(String message) {
		super(message);
	}
}
