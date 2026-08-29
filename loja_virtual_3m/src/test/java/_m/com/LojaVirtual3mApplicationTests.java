package _m.com;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.controller.AcessoControler;
import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;

@SpringBootTest(classes = LojaVirtual3mApplication.class)
class LojaVirtual3mApplicationTests  {

	@Autowired
	private AcessoControler acessoControler;

	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void testeCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_PC2026C");

		/*teste se o id esta nulo p/ gravar*/
		assertNull(acesso.getId());
		
		/* grava no banco*/
		acesso = acessoControler.salvarAcesso(acesso).getBody();
		
		
		/*Garantindo que objeto retornado nao é nulo*/
		assertNotNull(acesso);
		assertTrue(acesso.getId() > 0);
		
		
		/*Validar dados salvos*/
		assertEquals("ROLE_PCJUNIOR", acesso.getDescricao(), "ERRO CRÍTICO: A descrição retornada pelo banco é diferente da que foi enviada!");
		
		
		
		/*Teste carregamento*/
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).orElse(null);
		assertNotNull(acesso2);
		
		/* validação*/
		
		assertEquals(acesso.getId(), acesso2.getId());
		
		System.out.println("ID:"+ acesso.getId()+ " e "+ acesso2.getId());
		
		
		
		/*TESTE DE DELETE*/
		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush(); /* EXECUTA O SQL DE DELETE NO BANCO DE DADOS*/
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		
		assertEquals(true,acesso == null); 
		
	}
	
	
	@Test
	public void testeAcesso2() {
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ALUNO");
		acesso = acessoControler.salvarAcesso(acesso).getBody();
		List<Acesso> acessos = acessoRepository.buscarAcessoDescricao("ALUNO".trim().toUpperCase());
		assertEquals(1, acessos.size());
		
		
		
		
		
	}

}
