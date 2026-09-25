package loja_virtual_3m.com.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import loja_virtual_3m.com.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	@Query(value="select u from Usuario u where u.login =?1")
	Usuario findUserbyLogin(String login);

}
