package loja_virtual_3m.com.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;
import loja_virtual_3m.com.services.AcessoServices;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(classes = LojaVirtual3mApplication.class)
@AutoConfigureMockMvc
public class AcessoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private AcessoServices acessoServices;

	@Test
	public void testeApiSalvarAcesso() throws JacksonException, Exception {

		/*
		 * trabalhando com MOCKTIO -> OBJETOS RESPONSAVEIS POR AQUISIÇÕES HTTP( ENVIO E
		 * RETORNOR DE API
		 */
		/* 1 passo nao dependender do banco, usa testes unitarios */
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_JR");

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/salvarAcesso").content(objectMapper.writeValueAsBytes(acesso))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		System.out.println("RETORNOR API: " + retornoApi.andReturn().getResponse().getContentAsString());

		Acesso objetoAcesso = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
				Acesso.class);

		assertEquals(objetoAcesso.getDescricao(), acesso.getDescricao());

	}

	@Test
	public void TesteAPIDeleteController() throws JacksonException, Exception {
		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_DELECAO");
		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.delete("/deleteAcesso").content(objectMapper.writeValueAsBytes(acesso))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		System.out.println("RETORNOR API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("RETORNOR STATUS API: " + retornoApi.andReturn().getResponse().getStatus());

		int statusHttp = retornoApi.andReturn().getResponse().getStatus();
		assertEquals(200, statusHttp, "API FALHOU AO DELETAR O ACESSO!");

		boolean DadoNoBanco = acessoRepository.existsById(acesso.getId());
		assertFalse(DadoNoBanco, "A INFORMAÇÃO AINDA ESTÁ NO BANCO");

	}

	@Test
	public void testeAPIDeletePorId() throws JacksonException, Exception {

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_DELECAO");
		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc /* deleteAcessoporid */
				.perform(MockMvcRequestBuilders.delete("/deleteAcessoId/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		System.out.println("RETORNOR API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("RETORNOR STATUS API: " + retornoApi.andReturn().getResponse().getStatus() + "REMOVIDO");

		int statusHttp = retornoApi.andReturn().getResponse().getStatus();
		assertEquals(200, statusHttp, "API FALHOU AO DELETAR O ACESSO!");

		boolean DadoNoBanco = acessoRepository.existsById(acesso.getId());
		assertFalse(DadoNoBanco, "A INFORMAÇÃO AINDA ESTÁ NO BANCO");

	}

	@Test
	public void testeAPiPegarAcessoPorId() throws JacksonException, Exception {

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_JR");
		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc /* deleteAcessoporid */
				.perform(MockMvcRequestBuilders.get("/buscarAcessoid/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		System.out.println("RETORNOR API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("RETORNOR STATUS API: " + retornoApi.andReturn().getResponse().getStatus() + "ENCONTRADO");

		Acesso acesso2 = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
				Acesso.class);
		assertEquals(acesso.getDescricao(), acesso2.getDescricao());

		assertEquals(acesso.getId(), acesso2.getId());

		int statusHttp = retornoApi.andReturn().getResponse().getStatus();
		assertEquals(200, statusHttp, "API FALHOU AO buscar o ID O ACESSO!");

	}

	@Test
	public void TesteRestApiBuscarAcessoPorDesc() throws JacksonException, Exception {
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_JR");

		ObjectMapper json = new ObjectMapper();

		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/buscarAcesso/" + acesso.getDescricao())
				.content(json.writeValueAsString(json)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		System.out.println("RETORNOR API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("RETORNOR STATUS API: " + retornoApi.andReturn().getResponse().getStatus() + "ENCONTRADO");

		/* VEREFICIAR SE A API RESPONDEU HTTP 200 OK */
		int statusHttp = retornoApi.andReturn().getResponse().getStatus();

		assertEquals(200, statusHttp, "API FALHOU AO BUSCAR OS ACESSOS");

		List<Acesso> listaAcessos = json.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
				new TypeReference<List<Acesso>>() {
				});

		assertTrue(listaAcessos.size() >= 2, "Deveria contter pelo menos 2 registros!");

		assertTrue(listaAcessos.get(0).getDescricao().contains("ROLE_TESTE_JR"),
				"O registro retornado não corresponde ao termo da buscada!");

	}

}
