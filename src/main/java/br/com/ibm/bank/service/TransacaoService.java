package br.com.ibm.bank.service;

import br.com.ibm.bank.dto.request.TransacaoRequest;
import br.com.ibm.bank.dto.response.TransacaoResponse;
import br.com.ibm.bank.models.ClienteEntity;
import br.com.ibm.bank.models.TransacaoEntity;
import br.com.ibm.bank.repository.ClienteRepository;
import br.com.ibm.bank.repository.TransacaoRepository;
import br.com.ibm.bank.service.exception.ClienteNotFoundException;
import br.com.ibm.bank.service.exception.TransacaoException;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Transient
	public TransacaoResponse executaTransacao(TransacaoRequest transacaoRequest) {
		if(transacaoRequest.valor().doubleValue() <= 0.0) {
			throw new TransacaoException("Insira um valor maior que zero");
		}

		ClienteEntity clienteEntity = clienteRepository
				.findByEmail(transacaoRequest.emailDestino())
				.orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado"));

		TransacaoEntity transacaoEntity = new TransacaoEntity(
				transacaoRequest.valor(),
				transacaoRequest.tipoTransacao(),
				clienteEntity,
				UUID.randomUUID());

		return new TransacaoResponse(transacaoRepository.save(transacaoEntity));
	}
}
