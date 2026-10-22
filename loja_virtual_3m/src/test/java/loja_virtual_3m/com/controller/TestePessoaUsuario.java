package loja_virtual_3m.com.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import loja_virtual_3m.com.LojaVirtual3mApplication;
import loja_virtual_3m.com.Exceptions.ExcessoesCustomizadas;
import loja_virtual_3m.com.enums.TipoEndereco;
import loja_virtual_3m.com.model.Endereco;
import loja_virtual_3m.com.model.Pessoa_Fisica;
import loja_virtual_3m.com.model.Pessoa_Juridica;

@ActiveProfiles("test")
@SpringBootTest(classes = LojaVirtual3mApplication.class)
@AutoConfigureMockMvc
public class TestePessoaUsuario {

	@Autowired
	private PessoaControler pessoaControler;

	@Test
	public void testeInsertPessoaJuridica() throws ExcessoesCustomizadas {

		Pessoa_Juridica pJuridica = new Pessoa_Juridica();
		pJuridica.setCnpj("87.945.893/0001-27");
		pJuridica.setNome("PAulo Cezar Henrique Junior");
		pJuridica.setEmail("pchjr.email@teste.com");
		pJuridica.setTelefone("(18)34465645454");
		pJuridica.setInscricaoEstadual("958.601.422.521");
		pJuridica.setInscricaoMunicipal("59844595-1");
		pJuridica.setNomeFantasia("TECNOLOGIA EM SISTEMAS - ERP");
		pJuridica.setRazaoSocial("Muju's LTDA"); // 👈 Garanta que está preenchido
		pJuridica.setCategoria("TECNOLOGIA");

		Endereco endereco = new Endereco();
		endereco.setRuaLogradouro("Av Konder");
		endereco.setCep("2222222.22222");
		endereco.setBairro("centro");
		endereco.setCidade("Rubiácea");
		endereco.setNumero("22");
		endereco.setUf("sp");
		endereco.setPessoa_endereco(pJuridica);
		endereco.setEmpresa(pJuridica);
		endereco.setTipoEndereco(TipoEndereco.ENTREGA);

		Endereco endereco2 = new Endereco();
		endereco2.setCep("2222222");
		endereco2.setRuaLogradouro("Afonso Pena");
		endereco2.setBairro("Umarama");
		endereco2.setCidade("Araçatuba");
		endereco2.setNumero("2222");
		endereco2.setUf("sp");
		endereco2.setPessoa_endereco(pJuridica);
		endereco2.setEmpresa(pJuridica);
		endereco2.setTipoEndereco(TipoEndereco.COBRANCA);

		pJuridica.getEnderecos().add(endereco);
		pJuridica.getEnderecos().add(endereco2);

		pJuridica = pessoaControler.savePj(pJuridica).getBody();

		assertEquals(pJuridica.getId() > 0, true);

		assertEquals(2, pJuridica.getEnderecos().size());

	}

	@Test
	public void testInsertPessoaFisica() throws ParseException, ExcessoesCustomizadas {

		Pessoa_Fisica pFisica = new Pessoa_Fisica();
		pFisica.setNome("PAULIN");
		pFisica.setCpf("934.689.240-46");
		pFisica.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("23/02/1958"));
		pFisica.setTelefone("(75) 3704-6683");
		pFisica.setEmail("paulocezarhenriquejunior1990@gmail.com");

		Endereco endereco = new Endereco();
		endereco.setRuaLogradouro("TESTE");
		endereco.setCep("74063010");
		endereco.setBairro("AASASA");
		endereco.setCidade("RubiáceASAASa");
		endereco.setNumero("118");
		endereco.setUf("SP");
		endereco.setPessoa_endereco(pFisica);
		endereco.setEmpresa(pFisica);
		endereco.setTipoEndereco(TipoEndereco.ENTREGA);

		Endereco endereco2 = new Endereco();
		endereco2.setRuaLogradouro("TESTE");
		endereco2.setCep("65915306");
		endereco2.setBairro("AASASA");
		endereco2.setCidade("RubiáceASAASa");
		endereco2.setNumero("11558");
		endereco2.setUf("SP");
		endereco2.setPessoa_endereco(pFisica);
		endereco2.setEmpresa(pFisica);
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);

		
		
		
		
		pFisica.getEnderecos().add(endereco); // ADICIONANDO O ENDERECO NO OBJETO PESSOA FISIC
		pFisica.getEnderecos().add(endereco2);
		
		pFisica = pessoaControler.savePf(pFisica).getBody();

		assertEquals(1, pFisica.getEnderecos().size());
	}

}
