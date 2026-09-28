package loja_virtual_3m.com.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;
import loja_virtual_3m.com.services.AcessoServices;

@ActiveProfiles("test")
@SpringBootTest(classes = LojaVirtual3mApplication.class)
@AutoConfigureMockMvc
public class AcessoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String token;

	@Autowired
	private AcessoServices acessoServices;
	
	
	
	public void setup() throws Exception {
		
		Map<String, String> loginMap = new HashMap<>();
		loginMap.put("user","admin");
		loginMap.put("password", "admin");
		
		
		MvcResult result = mockMvc.perform(post("/login")
				
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(loginMap)))
				.andExpect(status().isOk())
				.andReturn();
		
		this.token = result.getResponse().getHeader("Authorization");
				
				
		
		
		
	}
	
	


	@Test
	public void testeApiSalvarAcesso() throws JacksonException, Exception {

		/*
		 * trabalhando com MOCKTIO -> OBJETOS RESPONSAVEIS POR AQUISIÇÕES HTTP( ENVIO E
		 * RETORNOR DE API
		 */
		/* 1 passo nao dependender do banco, usa testes unitarios */
		Acesso acesso = new Acesso();
		acesso.setDescricao("AÇÃO_RONALDO");

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/salvarAcesso").content(objectMapper.writeValueAsBytes(acesso))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		System.out.println("RETORNOR API: " + retornoApi.andReturn().getResponse().getContentAsString());

		Acesso objetoAcesso = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
				Acesso.class);

		assertEquals(objetoAcesso.getDescricao(), acesso.getDescricao());

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
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
	
	
	@JsonIgnoreProperties(ignoreUnknown = true)
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
				.perform(MockMvcRequestBuilders.get("/buscarAcessoid/id/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso)).header("Authorization", token ).contentType(MediaType.APPLICATION_JSON)
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

		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/buscarAcesso/desc/" + acesso.getDescricao())
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
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
