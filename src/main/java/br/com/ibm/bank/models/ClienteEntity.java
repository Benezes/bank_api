package br.com.ibm.bank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clientes")
public class ClienteEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private Integer idade;

	@Column(nullable = false, unique = true)
	@Email
	private String email;

	@Column(nullable = false, unique = true)
	private String numeroConta;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<TransacaoEntity> transacoes = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public ClienteEntity() {
	}

	public ClienteEntity(String nome, Integer idade, String email, String numeroConta, List<TransacaoEntity> transacoes, UUID id) {
		this.nome = nome;
		this.idade = idade;
		this.email = email;
		this.numeroConta = numeroConta;
		this.transacoes = transacoes;
		this.id = id;
	}

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public UUID getId() {
		return this.id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public @Email String getEmail() {
		return email;
	}

	public void setEmail(@Email String email) {
		this.email = email;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public List<TransacaoEntity> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<TransacaoEntity> transacoes) {
		this.transacoes = transacoes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClienteEntity that)) return false;
		return Objects.equals(id, that.id) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getIdade(), that.getIdade()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getNumeroConta(), that.getNumeroConta()) && Objects.equals(getTransacoes(), that.getTransacoes()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, getNome(), getIdade(), getEmail(), getNumeroConta(), getTransacoes(), getCreatedAt(), getUpdatedAt());
	}

	@Override
	public String toString() {
		return "ClienteEntity{" + "nome='" + nome + '\'' + ", idade=" + idade + ", email='" + email + '\'' + ", numeroConta='" + numeroConta + '\'' + ", transacoes=" + transacoes + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
	}


}
