package loja_virtual_3m.com.enums;

public enum StatusContaPagar {

	COBRANCA("Pagar"), VENCIA("Vencida"), ABERTA("Aberta"), QUITADA("Quitada"), ALUGUEL("Aluguel"),
	FUNCIONARIO("Funcionário"), NEGOCIADA("Renegociada");

	private String descricao;

	private StatusContaPagar(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.descricao;
	}

}
