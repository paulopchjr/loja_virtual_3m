package _m.com;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;
import loja_virtual_3m.com.services.AcessoServices;

@SpringBootTest(classes = LojaVirtual3mApplication.class)
class LojaVirtual3mApplicationTests {

	@Autowired
	private AcessoServices acessoServices;

	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void testeCadastraAcesso() {
	
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoRepository.save(acesso);
	
	}
	

}
