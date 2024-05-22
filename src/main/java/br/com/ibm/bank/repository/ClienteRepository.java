package br.com.ibm.bank.repository;

import br.com.ibm.bank.models.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
	Optional<ClienteEntity> findByEmail(String email);

	Optional<ClienteEntity> findByNumeroConta(String numeroConta);
}
