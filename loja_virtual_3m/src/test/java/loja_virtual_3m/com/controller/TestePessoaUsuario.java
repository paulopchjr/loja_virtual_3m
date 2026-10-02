package loja_virtual_3m.com.controller;

import java.util.Calendar;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.Exceptions.ExcessoesCustomizadas;
import loja_virtual_3m.com.model.Pessoa_Juridica;

@ActiveProfiles("test")
@SpringBootTest(classes = LojaVirtual3mApplication.class)
@AutoConfigureMockMvc
public class TestePessoaUsuario {

	@Autowired
	private PessoaControler pessoaControler;

	@Test
	public void testeInsertPessoaJuridica() throws ExcessoesCustomizadas {

		Random random = new Random(); 
		int numeroAleatorio =  random.nextInt(10);
		
		Pessoa_Juridica pJuridica = new Pessoa_Juridica();
		pJuridica.setCnpj(String.valueOf(numeroAleatorio) + Calendar.getInstance().getTime());
		pJuridica.setNome("Junior Henrique");
		pJuridica.setEmail("junior.henrique@teste.com");
		pJuridica.setTelefone("(18)365645454");
		pJuridica.setInscricaoEstadual("095.784.11");
		pJuridica.setInscricaoMunicipal("000000-09");
		pJuridica.setNomeFantasia("TECNOLOGIA EM SISTEMAS - ERP");
		pJuridica.setRazaoSocial("Mujus LTDA"); // 👈 Garanta que está preenchido
		pJuridica.setEmpresa(null); // 👈 Explicitamente nulo para testar a FK opcional
		

		pessoaControler.savePj(pJuridica);

		// assertNotNull(pessoaSalva.getId(), "A pessoa jurídica não foi salva no
		// banco!");
	}

}
