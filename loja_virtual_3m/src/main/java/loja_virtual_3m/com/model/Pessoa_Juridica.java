package loja_virtual_3m.com.model;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pessoa_juridica")
public class Pessoa_Juridica extends Pessoa {

	private static final long serialVersionUID = 1L;

	@CNPJ(message = "CNPJ está inválido")
	@Column(nullable = false)
	private String cnpj;
	
	@Size(max = 20, message = "A inscrição deve ter no máximo 14 Digitos.")
	@Column(nullable = false)
	private String inscricaoEstadual;

	private String inscricaoMunicipal;

	@Size(min = 3, message = "O nome fantasia deve pelo menos 3 caracteres")
	@NotBlank(message = "Preencha o nome Fantasia")
	@Column(nullable = false, name = "nome_fantasia", length = 60)
	private String nomeFantasia;

	@Column(nullable = false)
	private String razaoSocial;

	private String categoria;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
