package loja_virtual_3m.com;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import loja_virtual_3m.com.controller.AcessoControler;
import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(classes = LojaVirtual3mApplication.class)
class LojaVirtual3mApplicationTests {

	@Autowired
	private AcessoControler acessoControler;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private WebApplicationContext applicationContext;

	@Test
	public void testeRestApiCadastroAcesso() throws JacksonException, Exception {

		/* trabalhando com mock */

		/*
		 * Objetos responsaveis para efetuar os testes (requisicoes para APIS, E
		 * REOTORNOS )
		 */
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.applicationContext);
		MockMvc mockMvc = builder.build();

		/*
		 * 1 passo_> Não depender do banco , pode acontecer portabilidade de
		 * dados(postgres, mysql, firebird.. Velocidade (Testes em milissegundos),
		 * Isolamento de Erros (Achar o culpado), Independência de Dados (O banco está
		 * sempre limpo) e tambem ele é um teste unitário e nao de integração
		 */

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR2");

		/* criando um json para passar no contet */
		ObjectMapper mapper = new ObjectMapper();

		/*
		 * FAZENDO UM POST NO MEU END POINT DO CONTROLLER, E PASSANDO UM JSON POR
		 * PARAMENTRO DE ACESSO, QUE FOI SETADO LA EM CIMA
		 * {"descricao":"ROLE_COMPRADOR"}
		 */
		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/salvarAcesso").content(mapper.writeValueAsString(acesso))
						.contentType(MediaType.APPLICATION_JSON) /* como vai enviar */
						.accept(MediaType.APPLICATION_JSON));/* como vai receber */

		System.out.println("RETORNO DA API:" + retornoApi.andReturn().getResponse().getContentAsString());
		/*
		 * converter o retorno da API para um objeto de acesso _> pq no end point ele
		 * retorna um json
		 */
		Acesso objAcessoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
				Acesso.class);  // RETORNA UM JSON
		
		
		/*valida o que foi setado do objeto salvar, do que realmente foi salvo*/
		assertEquals(acesso.getDescricao(), objAcessoRetorno.getDescricao());
	}

	@Test
	public void testeCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_PC2026C");

		/* teste se o id esta nulo p/ gravar */
		assertNull(acesso.getId());

		/* grava no banco */
		acesso = acessoControler.salvarAcesso(acesso).getBody();

		/* Garantindo que objeto retornado nao é nulo */
		assertNotNull(acesso);
		assertTrue(acesso.getId() > 0);

		/* Validar dados salvos */
		assertEquals("ROLE_PCJUNIOR", acesso.getDescricao(), /* Aqui quebra o cdigo, porque ROLE_PCJUNIOR. NAOFOI GRAVADO NO BANCO, O QUEFOI SETADO PRA GRAVAR FOI ROLE_PC2026C*/
				"ERRO CRÍTICO: A descrição retornada pelo banco é diferente da que foi enviada!");

		/* Teste carregamento */
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).orElse(null);
		assertNotNull(acesso2);

		/* validação */

		assertEquals(acesso.getId(), acesso2.getId());

		System.out.println("ID:" + acesso.getId() + " e " + acesso2.getId());

		/* TESTE DE DELETE */
		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush(); /* EXECUTA O SQL DE DELETE NO BANCO DE DADOS */
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

		assertEquals(true, acesso == null);

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
