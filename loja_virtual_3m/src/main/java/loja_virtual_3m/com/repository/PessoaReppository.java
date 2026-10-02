package loja_virtual_3m.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import loja_virtual_3m.com.model.Pessoa_Juridica;

@Repository
public interface PessoaReppository extends JpaRepository<Pessoa_Juridica, Long> {

	
	@Query(value = "SELECT pj from Pessoa_Juridica pj where pj.cnpj = ?1")
	public Pessoa_Juridica contemCnpjCadastrado(String cnpj);
	
}
