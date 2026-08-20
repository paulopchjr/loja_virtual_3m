package loja_virtual_3m.com.enums;

public enum StatusContaReceber {

	COBRANCA("Pagar"), VENCIA("Vencida"), ABERTA("Aberta"), QUITADA("Quitada");

	private String descricao;

	private StatusContaReceber(String descricao) {
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
