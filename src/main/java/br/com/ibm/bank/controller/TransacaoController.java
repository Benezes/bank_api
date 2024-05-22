package br.com.ibm.bank.controller;

import br.com.ibm.bank.dto.request.TransacaoRequest;
import br.com.ibm.bank.dto.response.TransacaoResponse;
import br.com.ibm.bank.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {

	@Autowired
	public TransacaoService transacaoService;

	@PostMapping
	public ResponseEntity<TransacaoResponse> postTransacao(@RequestBody @Valid TransacaoRequest transacaoRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.executaTransacao(transacaoRequest));
	}
}
