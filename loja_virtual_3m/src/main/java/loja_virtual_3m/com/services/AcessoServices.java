package loja_virtual_3m.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;

@Service
public class AcessoServices {

	
	// injencao de depedencia
	@Autowired
	private AcessoRepository acessoRepository;
	
	
	public Acesso salvar(Acesso acesso) {
		return acessoRepository.save(acesso);
	}
	
	
	
	
}
