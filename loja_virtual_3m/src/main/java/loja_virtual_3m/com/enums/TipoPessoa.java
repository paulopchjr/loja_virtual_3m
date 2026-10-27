package loja_virtual_3m.com.enums;

public enum TipoPessoa {

	JURIDICA("Judirica"),
	JURIDICA_FORNECEDOR("Judirica e Fornecedor"),
	FISICA("física");
	
	private String Descricao;

	
	private TipoPessoa(String desc) {
		this.Descricao = desc;
	}
	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}
	
	
	
}
