package loja_virtual_3m.com.services;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import loja_virtual_3m.com.model.Pessoa_Juridica;
import loja_virtual_3m.com.model.Usuario;
import loja_virtual_3m.com.repository.PessoaReppository;
import loja_virtual_3m.com.repository.UsuarioRepository;

@Service
public class PessoaUsuarioService {

	private PessoaReppository pReppository;
	private UsuarioRepository uRepository;
	private JdbcTemplate templateJdbc;

	public PessoaUsuarioService(PessoaReppository pessoaReppository, UsuarioRepository userRepository,
			JdbcTemplate jdbcTemplate) {
		this.pReppository = pessoaReppository;
		this.uRepository = userRepository;
		this.templateJdbc = jdbcTemplate;

	}

	public Pessoa_Juridica savePessoaJ(Pessoa_Juridica pJuridica) {

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

			System.out.printf("userPj: " + userPj.toString());

			String senhaAleatoria = UUID.randomUUID().toString().substring(0, 15);
			System.out.println("Senha Alatoria=" + senhaAleatoria);

			String senhaCriptografada = new BCryptPasswordEncoder().encode(senhaAleatoria);

			userPj.setSenha(senhaCriptografada);

			uRepository.save(userPj);

			uRepository.insertUserPj(userPj.getId());

		}

		return pJuridica;
	}

}
