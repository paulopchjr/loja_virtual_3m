package loja_virtual_3m.com.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import loja_virtual_3m.com.model.Usuario;
import loja_virtual_3m.com.repository.UsuarioRepository;

@Service
public class TarefaAutomatizadaService {

	private final UsuarioRepository usuarioRepository;
	private final ServiceSendoEmail email;

	TarefaAutomatizadaService(UsuarioRepository usuarioRepository, ServiceSendoEmail serviceEmail) {
		this.usuarioRepository = usuarioRepository;
		this.email = serviceEmail;
	}

	//@Scheduled(initialDelay = 2000, fixedDelay = 86400000) /*roda a cada 24 hors*/
	@Scheduled(cron = "0 0 22 * * * ", zone = "America/Sao_Paulo") /* roda todo os dias as 11*/
	public void notificarUsuarioTrocarSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {

		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();

		for (Usuario usuario : usuarios) {

			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getPessoa().getNome()).append("!<br/>");
			msg.append("Está na hora de trocar a sua senha, já passou 90 dias de validade .").append("<br/>");
			msg.append("Troca a sua senha a loja Virtual  do Junior.").append("<br/>");
			
			
			email.enviarEmailHtml("Torca a senha: ", msg.toString(), usuario.getLogin());
			
			Thread.sleep(3000);
			
	 	}

	}

}
