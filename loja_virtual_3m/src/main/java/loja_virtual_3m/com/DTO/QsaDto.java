package loja_virtual_3m.com.DTO;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QsaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;

	@JsonProperty("qual")
	private String qual;

	@JsonProperty("pais_origem")
	private String pais_Origem;

	@JsonProperty("nome_rep_legal")
	private String nome_repres_Legal;

	@JsonProperty("qual_rep_legal")
	private String qual_repres_Legal;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getQual() {
		return qual;
	}

	public void setQual(String qual) {
		this.qual = qual;
	}

	public String getPais_Origem() {
		return pais_Origem;
	}

	public void setPais_Origem(String pais_Origem) {
		this.pais_Origem = pais_Origem;
	}

	public String getNome_repres_Legal() {
		return nome_repres_Legal;
	}

	public void setNome_repres_Legal(String nome_repres_Legal) {
		this.nome_repres_Legal = nome_repres_Legal;
	}

	public String getQual_repres_Legal() {
		return qual_repres_Legal;
	}

	public void setQual_repres_Legal(String qual_repres_Legal) {
		this.qual_repres_Legal = qual_repres_Legal;
	}

	
	
}
