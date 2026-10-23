package loja_virtual_3m.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import loja_virtual_3m.com.model.Pessoa_Fisica;
import loja_virtual_3m.com.model.Pessoa_Juridica;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<Pessoa_Fisica, Long> {

	
	@Query(value = "SELECT pf from Pessoa_Fisica pf where pf.cpf = ?1")
	public Pessoa_Fisica contemCpfCadastrado(String cpf);

	@Query(value = "SELECT pf from Pessoa_Fisica pf where upper(trim(pf.nome)) like CONCAT('%', ?1, '%')")
	public List<Pessoa_Fisica>pesquisaPorNomePf(String nome);
	
	
	
	
}
