package _m.com;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import junit.framework.TestCase;
import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.controller.AcessoControler;
import loja_virtual_3m.com.model.Acesso;

@SpringBootTest(classes = LojaVirtual3mApplication.class)
class LojaVirtual3mApplicationTests extends TestCase {

	@Autowired
	private AcessoControler acessoControler;

	@Test
	public void testeCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN123");

		
	assertEquals(true, acesso.getId() == null);	
	
	/*Gravou no banco*/
	acesso = acessoControler.salvarAcesso(acesso).getBody();
	
	// compara se esta salvando no banco de dados
	assertEquals(true, acesso.getId() > 0);

	/* validar dados salvos na forma correta*/
	assertEquals("ROLE_ADMIN321", acesso.getDescricao());
	}

}
