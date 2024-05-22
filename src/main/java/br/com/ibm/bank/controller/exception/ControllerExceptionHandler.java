package br.com.ibm.bank.controller.exception;

import br.com.ibm.bank.service.exception.ClienteException;
import br.com.ibm.bank.service.exception.ClienteNotFoundException;
import br.com.ibm.bank.service.exception.TransacaoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<ErroPadrao> clienteNotFoundException(ClienteNotFoundException ex, HttpServletRequest request) {
		ErroPadrao erroPadrao = new ErroPadrao(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
	}

	@ExceptionHandler(ClienteException.class)
	public ResponseEntity<ErroPadrao> clienteBadRequestException(ClienteException ex, HttpServletRequest request) {
		ErroPadrao erroPadrao = new ErroPadrao(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}

	@ExceptionHandler(TransacaoException.class)
	public ResponseEntity<ErroPadrao> transacaoValueException(TransacaoException ex, HttpServletRequest request) {
		ErroPadrao erroPadrao = new ErroPadrao(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}
}
