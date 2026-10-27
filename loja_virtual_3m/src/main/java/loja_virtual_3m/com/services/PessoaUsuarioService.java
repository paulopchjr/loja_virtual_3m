package loja_virtual_3m.com.services;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import loja_virtual_3m.com.DTO.CepDTO;
import loja_virtual_3m.com.DTO.ConsultaCnpjDto;
import loja_virtual_3m.com.Exceptions.ExcessoesCustomizadas;
import loja_virtual_3m.com.model.Endereco;
import loja_virtual_3m.com.model.Pessoa_Fisica;
import loja_virtual_3m.com.model.Pessoa_Juridica;
import loja_virtual_3m.com.model.Usuario;
import loja_virtual_3m.com.repository.EnderecoRepository;
import loja_virtual_3m.com.repository.PessoaFisicaRepository;
import loja_virtual_3m.com.repository.PessoaReppository;
import loja_virtual_3m.com.repository.UsuarioRepository;

@Service
public class PessoaUsuarioService {

	private PessoaReppository pReppository;
	private PessoaFisicaRepository pfRepository;
	private UsuarioRepository uRepository;
	private JdbcTemplate templateJdbc;
	private ServiceSendoEmail email;
	private EnderecoRepository enderecoRepository;

	public PessoaUsuarioService(PessoaReppository pessoaReppository, PessoaFisicaRepository pessoaFisicaRepository,
			UsuarioRepository userRepository, JdbcTemplate jdbcTemplate, ServiceSendoEmail emailService,
			EnderecoRepository endRepository) {
		this.pReppository = pessoaReppository;
		this.uRepository = userRepository;
		this.templateJdbc = jdbcTemplate;
		this.email = emailService;
		this.pfRepository = pessoaFisicaRepository;
		this.enderecoRepository = endRepository;

	}

	public Pessoa_Juridica savePessoaJ(Pessoa_Juridica pJuridica) throws ExcessoesCustomizadas {

		this.processarEnderecosPj(pJuridica.getEnderecos(), pJuridica);

		pJuridica = pReppository.saveAndFlush(pJuridica);

		Usuario userPj = uRepository.fidUserByPessoa(pJuridica.getId(), pJuridica.getEmail());

		if (userPj == null) {

			this.criarUsuarioEEnviarEmailPj(pJuridica);

		}

		return pJuridica;
	}

	public Pessoa_Fisica savePessoaFisica(Pessoa_Fisica pFisica) throws ExcessoesCustomizadas {

		this.processarEnderecosPf(pFisica.getEnderecos(), pFisica);

		pFisica = pfRepository.save(pFisica);

		boolean usuarioJaExiste = uRepository.existeByPessoaIdPorLogin(pFisica.getId(), pFisica.getEmail());

		if (!usuarioJaExiste) {

			this.criarUsuarioEEnviarEmailPf(pFisica);

		}
		return pFisica;

	}

	private void criarUsuarioEEnviarEmailPj(Pessoa_Juridica pj) {

		this.removerConstraintAcessoSeExistir();

		// cria um novo usuario pessoa juridica , com a data atual cadastrada
		Usuario userPj = new Usuario();
		userPj.setDataAtualSenha(Calendar.getInstance().getTime());
		userPj.setEmpresa(pj);
		userPj.setPessoa(pj);
		userPj.setLogin(pj.getEmail());

		String senhaAleatoria = this.gerarSenhasECriptografarSenha(userPj);
		uRepository.save(userPj);
		uRepository.insertUserAcesso(userPj.getId());

		this.enviarEmailBoasVindas("Acesso Gerado para a Loja Virtual TESTE:", pj.getEmail(), senhaAleatoria);
	}

	private void criarUsuarioEEnviarEmailPf(Pessoa_Fisica pf) {

		this.removerConstraintAcessoSeExistir();
		// cria um novo usuario pessoa juridica , com a data atual cadastrada
		// cria um novo usuario pessoa juridica , com a data atual cadastrada
		Usuario userPf = new Usuario();
		userPf.setDataAtualSenha(Calendar.getInstance().getTime());
		userPf.setEmpresa(pf);
		userPf.setPessoa(pf);
		userPf.setLogin(pf.getEmail());

		String senhaAleatoria = this.gerarSenhasECriptografarSenha(userPf);
		uRepository.save(userPf);
		uRepository.insertUserAcesso(userPf.getId());

		this.enviarEmailBoasVindas("Acesso Gerado para a Loja Virtual:", pf.getEmail(), senhaAleatoria);
	}

	private void enviarEmailBoasVindas(String Assunto, String emailDestino, String senhaAleatoria) {

		String msgHtml = "<div style=\"background-color: black; text-decoration:none font-family: Verdana, Arial, Helvetica, sans-serif; color: #A3E4D7; font-weight: bold; padding: 15px; margin-bottom: 15px; border-radius: 4px;\">"
				+ "<b>Segue abaixo os dados de acesso para a loja virtual</b></br>" + "</div>"
				+ "<p style=\"color:black; font-weight:bold;  margin-bottom: 5px;\">Login:  " + emailDestino + "</p>"
				+ "<p style=\"color:black; font-weight:bold;  margin-bottom: 5px;\">Senha: " + senhaAleatoria + "</p>"
				+ "<b>Obrigado !!</b>";

		try {
			email.enviarEmailHtml(Assunto, msgHtml, emailDestino);
		} catch (UnsupportedEncodingException | MessagingException e) {

			e.printStackTrace();
		}

	}

	private void removerConstraintAcessoSeExistir() {
		// identifica a restricao no banco
		String constraint = uRepository.consultaConstraintAcesso();

		// remove a constraint que esta impedindo salvar varios acessos no usuario
		if (constraint != null) {
			templateJdbc.execute("begin; alter table usuario_acesso drop constraint  " + constraint + "; commit;");
		}

	}

	private String gerarSenhasECriptografarSenha(Usuario usuario) {

		String senhaAleatoria = UUID.randomUUID().toString().substring(0, 15);
		String senhaCriptografada = new BCryptPasswordEncoder().encode(senhaAleatoria);

		usuario.setSenha(senhaCriptografada);

		return senhaAleatoria;
	}

	private void processarEnderecosPj(List<Endereco> enderecos, Pessoa_Juridica pj) throws ExcessoesCustomizadas {

		if (enderecos == null || pj.getEnderecos().isEmpty()) {
			throw new ExcessoesCustomizadas("É obrigatório pelo menos um endereço da pessoa Juridica");
		}

		// listas de enderecos vindo da pessoa juridica
		for (Endereco endereco : enderecos) {
			endereco.setEmpresa(pj);
			endereco.setPessoa_endereco(pj);
			this.atualizarDadosPorCep(endereco);
		}

	}

	private void processarEnderecosPf(List<Endereco> enderecos, Pessoa_Fisica pf) throws ExcessoesCustomizadas {

		if (enderecos == null || pf.getEnderecos().isEmpty()) {
			throw new ExcessoesCustomizadas("É obrigatório pelo menos um endereço da pessoa Fisica");
		}
		// passando os endereços que a pessoa fisica obtem.
		for (Endereco endereco : enderecos) {
			endereco.setEmpresa(pf);
			endereco.setPessoa_endereco(pf);
			this.atualizarDadosPorCep(endereco);
		}
	}

	private void atualizarDadosPorCep(Endereco endereco) {

		if (endereco.getCep() != null && !endereco.getCep().trim().isEmpty()) {
			CepDTO cepDTO = this.consultaCep(endereco.getCep());
			if (cepDTO != null) {

				endereco.setBairro(cepDTO.getBairro());
				endereco.setCidade(cepDTO.getLocalidade());
				endereco.setRuaLogradouro(cepDTO.getLogradouro());
				endereco.setUf(cepDTO.getUf());
			}
		}
	}

	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
	}

	public Endereco atualizarEndereco(Long enderecoid, Endereco enderecoAtualizado) throws ExcessoesCustomizadas {

		// localiza o endereco atual que está no banco

		Endereco enderecoExistente = enderecoRepository.findById(enderecoid)
				.orElseThrow(() -> new ExcessoesCustomizadas("Endereço não encontrado"));

		/// atualiza os campos mutávis vindo do front / requesição

		enderecoExistente.setCep(enderecoAtualizado.getCep());
		enderecoExistente.setNumero(enderecoAtualizado.getNumero());
		enderecoExistente.setComplemento(enderecoAtualizado.getComplemento());

		// se for mudar a matriz para quem é pessoa juridica
		if (enderecoAtualizado.getTipoEndereco() != null) {
			enderecoExistente.setTipoEndereco(enderecoAtualizado.getTipoEndereco());
		}

		this.atualizarDadosPorCep(enderecoExistente);

		return enderecoRepository.save(enderecoExistente);
	}

	public ConsultaCnpjDto consultaCnpjReceitaWs(String cnpj) throws Exception {

		
	String cnpjLimpo =	cnpj.replaceAll("[./-]", "");
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://receitaws.com.br/v1/cnpj/" + cnpjLimpo;

		try {
			// Faz a chamada e pega como String pura para analisar se necessário
			return restTemplate.getForEntity(url, ConsultaCnpjDto.class).getBody();
		} catch (Exception e) {
			// Se der erro, vamos tentar descobrir o que a API respondeu
			String respostaBruta = restTemplate.getForObject(url, String.class);
			System.out.println("⚠️ Resposta bruta da API: " + respostaBruta);
			throw new RuntimeException("Erro ao consultar CNPJ. Resposta da API: " + respostaBruta, e);
		}

	}

}
