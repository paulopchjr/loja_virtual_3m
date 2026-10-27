package loja_virtual_3m.com.DTO;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SimeiDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String optante;

	@JsonIgnoreProperties("data_opcao")
	private String data_opcao;

	private String data_exclusao;
	private Date ultima_atualizacao;

	public String getOptante() {
		return optante;
	}

	public void setOptante(String optante) {
		this.optante = optante;
	}

	public String getData_opcao() {
		return data_opcao;
	}

	public void setData_opcao(String data_opcao) {
		this.data_opcao = data_opcao;
	}

	public String getData_exclusao() {
		return data_exclusao;
	}

	public void setData_exclusao(String data_exclusao) {
		this.data_exclusao = data_exclusao;
	}

	public Date getUlta_atualizacao() {
		return ultima_atualizacao;
	}

	public void setUlta_atualizacao(Date ulta_atualizacao) {
		this.ultima_atualizacao = ulta_atualizacao;
	}

}
