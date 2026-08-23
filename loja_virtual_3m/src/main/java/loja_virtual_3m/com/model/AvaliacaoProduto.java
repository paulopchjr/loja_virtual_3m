package loja_virtual_3m.com.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "avaliacao_Produto")
@SequenceGenerator(name = "seq_avaliacao_produto", sequenceName ="seq_avaliacao_produto", allocationSize = 1, initialValue = 1 )
public class AvaliacaoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avaliacao_produto")
	private Long id;
	
	private String descricao;
	
	private int nota;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pepssoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoas;
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name="produto_fk"))
	private Produto produtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public Pessoa getPessoas() {
		return pessoas;
	}

	public void setPessoas(Pessoa pessoas) {
		this.pessoas = pessoas;
	}

	public Produto getProdutos() {
		return produtos;
	}

	public void setProdutos(Produto produtos) {
		this.produtos = produtos;
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
		AvaliacaoProduto other = (AvaliacaoProduto) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	
	
	
	
	
}
