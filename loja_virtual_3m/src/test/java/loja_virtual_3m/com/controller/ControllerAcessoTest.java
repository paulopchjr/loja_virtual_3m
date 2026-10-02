package loja_virtual_3m.com.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;

@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = LojaVirtual3mApplication.class)
@AutoConfigureMockMvc
public class ControllerAcessoTest  {
			
	
	
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext applicationContext;
	
	
	@Test
	public void insertAcesso() throws JsonProcessingException, Exception {
		// Configura o MockMvc utilizando o contexto completo da aplicação Spring (ApplicationContext).
		// Isso garante que todos os componentes, controladores, filtros de segurança (Spring Security) 
		// e configurações reais do sistema sejam carregados para simular requisições HTTP de ponta a ponta.
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(applicationContext);
		
		// Constrói e inicializa a instância do MockMvc pronta para executar e testar as rotas da API.
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_DEVPLENO");
		
		// json para passar na requisicao
		ObjectMapper mapper = new ObjectMapper();
		
		//Realizando um POST no endpoint do controller, passando o json 
		ResultActions retornoApi = mockMvc
										.perform(MockMvcRequestBuilders.post("/salvarAcesso")
										.content(mapper.writeValueAsString(acesso))
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON));
		
		System.out.println("RETORNO API:" + retornoApi.andReturn().getResponse().getContentAsString());
		
		
		Acesso acessoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		
		assertEquals(acesso.getDescricao(),acessoRetorno.getDescricao());
	}
	
	
	
	
	
}
