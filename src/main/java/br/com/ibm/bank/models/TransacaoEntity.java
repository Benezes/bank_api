package br.com.ibm.bank.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
public class TransacaoEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private BigDecimal valor;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoTransacaoEnum tipo;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private ClienteEntity cliente;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public TransacaoEntity() {
	}

	public TransacaoEntity(BigDecimal valor, TipoTransacaoEnum tipo, ClienteEntity cliente, UUID id) {
		this.valor = valor;
		this.tipo = tipo;
		this.cliente = cliente;
		this.id = id;
	}

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoTransacaoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransacaoEnum tipo) {
		this.tipo = tipo;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TransacaoEntity that)) return false;
		return Objects.equals(id, that.id) && Objects.equals(getValor(), that.getValor()) && getTipo() == that.getTipo() && Objects.equals(getCliente(), that.getCliente()) && Objects.equals(getCreatedAt(), that.getCreatedAt());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, getValor(), getTipo(), getCliente(), getCreatedAt());
	}

	@Override
	public String toString() {
		return "TransacaoEntity{" +
				"valor=" + valor +
				", tipo=" + tipo +
				", cliente=" + cliente +
				", createdAt=" + createdAt +
				'}';
	}
}
