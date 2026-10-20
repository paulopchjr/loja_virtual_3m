package loja_virtual_3m.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import loja_virtual_3m.com.model.Pessoa_Fisica;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<Pessoa_Fisica, Long> {

	
	@Query(value = "SELECT pf from Pessoa_Fisica pf where pf.cpf = ?1")
	public Pessoa_Fisica contemCpfCadastrado(String cpf);
	
}
