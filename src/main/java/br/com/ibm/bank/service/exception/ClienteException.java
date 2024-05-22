package br.com.ibm.bank.service.exception;

public class ClienteException extends RuntimeException {

	public ClienteException() {
	}

	public ClienteException(String message) {
		super(message);
	}
}
