package loja_virtual_3m.com.DTO;

import java.io.Serializable;
import java.util.Date;

public class SimplesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Boolean optante;
	private String data_opcao;
	private String data_exclusao;
	private Date ultima_Atulaziacao;

	public Boolean getOptante() {
		return optante;
	}

	public void setOptante(Boolean optante) {
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

	public Date getUltima_Atulaziacao() {
		return ultima_Atulaziacao;
	}

	public void setUltima_Atulaziacao(Date ultima_Atulaziacao) {
		this.ultima_Atulaziacao = ultima_Atulaziacao;
	}

}
