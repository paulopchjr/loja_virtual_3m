package loja_virtual_3m.com;

import loja_virtual_3m.util.ValidaCNPJ;
import loja_virtual_3m.util.ValidaCPF;

public class TesteCPFCNPJ {
	
	public static void main(String[] args) {
		
		
		boolean isCnpj = ValidaCNPJ.isCNPJ("48.639.276/0001-40");
		
		System.out.println("Cnpj é valido: "+isCnpj);
		
		
		boolean isCpf = ValidaCPF.isCPF("384.873.388-99");
		
		System.out.println("cpf = "+ isCpf);
		
		
	}

}
