package loja_virtual_3m.com.services;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import loja_virtual_3m.com.model.Endereco;
import loja_virtual_3m.com.model.Pessoa_Juridica;
import loja_virtual_3m.com.model.Usuario;
import loja_virtual_3m.com.repository.PessoaReppository;
import loja_virtual_3m.com.repository.UsuarioRepository;

@Service
public class PessoaUsuarioService {

	private PessoaReppository pReppository;
	private UsuarioRepository uRepository;
	private JdbcTemplate templateJdbc;
	private ServiceSendoEmail email;
	

	public PessoaUsuarioService(PessoaReppository pessoaReppository, UsuarioRepository userRepository,
			JdbcTemplate jdbcTemplate, ServiceSendoEmail emailService ) {
		this.pReppository = pessoaReppository;
		this.uRepository = userRepository;
		this.templateJdbc = jdbcTemplate;
		this.email = emailService;

	}

	public Pessoa_Juridica savePessoaJ(Pessoa_Juridica pJuridica) {

		if (pJuridica.getEnderecos() != null) {
			for (Endereco endereco : pJuridica.getEnderecos()) {

				endereco.setEmpresa(pJuridica);
				endereco.setPessoa_endereco(pJuridica);

			}
		}
		

		pJuridica = pReppository.save(pJuridica);
		System.out.println(pJuridica.getId());

		Usuario userPj = uRepository.fidUserByPessoa(pJuridica.getId(), pJuridica.getEmail());

		if (userPj == null) {

			// identifica a restricao no banco
			String constraint = uRepository.consultaConstraintAcesso();

			// remove a constraint que esta impedindo salvar varios acessos no usuario
			if (constraint != null) {
				templateJdbc.execute("begin; alter table usuario_acesso drop constraint  " + constraint + "; commit;");
			}

			userPj = new Usuario();
			userPj.setDataAtualSenha(Calendar.getInstance().getTime());
			userPj.setEmpresa(pJuridica);
			userPj.setPessoa(pJuridica);
			userPj.setLogin(pJuridica.getEmail());

			String senhaAleatoria = UUID.randomUUID().toString().substring(0, 15);
			System.out.println("Senha Alatoria=" + senhaAleatoria);

			String senhaCriptografada = new BCryptPasswordEncoder().encode(senhaAleatoria);

			userPj.setSenha(senhaCriptografada);

			uRepository.save(userPj);

			uRepository.insertUserPj(userPj.getId());

			StringBuilder msgHtml  = new StringBuilder();
			msgHtml.append("<div style=\"background-color: black; text-decoration:none font-family: Verdana, Arial, Helvetica, sans-serif; color: #A3E4D7; font-weight: bold; padding: 15px; margin-bottom: 15px; border-radius: 4px;\">");
			msgHtml.append("<b>Segue abaixo os dados de acesso para a loja virtual</b></br>");
			msgHtml.append("</div>");
			msgHtml.append("<p style=\"color:black; font-weight:bold;  margin-bottom: 5px;\">Login:  "+pJuridica.getEmail()+"</p>");
			msgHtml.append("<p style=\"color:black; font-weight:bold;  margin-bottom: 5px;\">Senha: "+senhaAleatoria+"</p>");
			msgHtml.append("<b>Obrigado !!</b>");
			
			
			
			try {
				email.enviarEmailHtml("Acesso gerado para Loja virtual", msgHtml.toString(), pJuridica.getEmail());
			} catch (UnsupportedEncodingException | MessagingException e) {
				
				e.printStackTrace();
			}
			
		}

		return pJuridica;
	}
	
	
	
	

}
