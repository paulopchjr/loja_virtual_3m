package loja_virtual_3m.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import loja_virtual_3m.com.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "select u from Usuario u where u.login =?1")
	Usuario findUserbyLogin(String login);

	@Query(value = "select u from Usuario u where u.pessoa.id=?1 or u.login = ?2")
	Usuario fidUserByPessoa(Long id, String email);

	@Query(value = "SELECT constraint_name FROM information_schema.constraint_column_usage \r\n"
			+ "where table_name = 'usuario_acesso' and column_name='acesso_id'\r\n"
			+ "and constraint_name <>'unique_acesso_usuario'", nativeQuery = true)
	String consultaConstraintAcesso();

	@Query(value = "select u from Usuario u where u.dataAtualSenha <= current_date - 90 DAY")
	List<Usuario> usuarioSenhaVencida();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuario_acesso(usuario_id,acesso_id) values(?1,(select id from acesso where acesso_desc = 'ROLE_USER')) ")
	void insertUserPj(Long id);

}
