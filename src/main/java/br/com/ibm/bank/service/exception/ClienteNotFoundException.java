package br.com.ibm.bank.service.exception;

public class ClienteNotFoundException extends RuntimeException {

	public ClienteNotFoundException() {
	}

	public ClienteNotFoundException(String message) {
		super(message);
	}
}
