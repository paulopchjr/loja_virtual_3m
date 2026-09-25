package loja_virtual_3m.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import loja_virtual_3m.com.model.Usuario;
import loja_virtual_3m.com.repository.UsuarioRepository;

@Service
public class ImplUserDetailsService implements UserDetailsService {

	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public ImplUserDetailsService(UsuarioRepository repositoryUser) {
		
		this.usuarioRepository =repositoryUser;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		/* Recebe o login para consulta */
		Usuario usuario = usuarioRepository.findUserbyLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}

		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
	}

}
