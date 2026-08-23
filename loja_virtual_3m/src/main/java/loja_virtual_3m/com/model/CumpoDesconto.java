package loja_virtual_3m.com.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "cumpom_desconto")
@SequenceGenerator(name = "seq_cumpom_desconto", sequenceName = "seq_cumpom_desconto", allocationSize = 1, initialValue = 1)
public class CumpoDesconto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cumpom_desconto")
	private Long id;

	private String codigoDescricao;

	private BigDecimal valorRealDesconto;

	private BigDecimal valorPorcentagemDesconto;

	@Temporal(TemporalType.DATE)
	private Date dataValidadeCumpom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoDescricao() {
		return codigoDescricao;
	}

	public void setCodigoDescricao(String codigoDescricao) {
		this.codigoDescricao = codigoDescricao;
	}

	public BigDecimal getValorRealDesconto() {
		return valorRealDesconto;
	}

	public void setValorRealDesconto(BigDecimal valorRealDesconto) {
		this.valorRealDesconto = valorRealDesconto;
	}

	public BigDecimal getValorPorcentagemDesconto() {
		return valorPorcentagemDesconto;
	}

	public void setValorPorcentagemDesconto(BigDecimal valorPorcentagemDesconto) {
		this.valorPorcentagemDesconto = valorPorcentagemDesconto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CumpoDesconto other = (CumpoDesconto) obj;
		return Objects.equals(id, other.id);
	}

}
