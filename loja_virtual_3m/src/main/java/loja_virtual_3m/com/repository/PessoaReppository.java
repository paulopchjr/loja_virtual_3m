package loja_virtual_3m.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import loja_virtual_3m.com.model.Pessoa_Juridica;

@Repository
public interface PessoaReppository extends JpaRepository<Pessoa_Juridica, Long> {

	@Query(value = "SELECT pj from Pessoa_Juridica pj where pj.cnpj = ?1")
	public Pessoa_Juridica contemCnpjCadastrado(String cnpj);

	@Query(value = "SELECT pj from Pessoa_Juridica pj where pj.inscricaoEstadual = ?1")
	public Pessoa_Juridica contemInscricaoEstadualCadastrado(String ie);

	@Query(value = "SELECT pj from Pessoa_Juridica pj where pj.nomeFantasia = ?1")
	public Object existeNomeFantasia(String nomeFantasia);

	@Query(value = "SELECT pj from Pessoa_Juridica pj where upper(trim(pj.nome)) LIKE CONCAT('%', UPPER(TRIM(:nome)),'%')")
	public List<Pessoa_Juridica> pesquisaPorNomePj(@Param("nome") String nome);

	@Query(value = "SELECT pj from Pessoa_Juridica pj where upper(trim(pj.cnpj)) like CONCAT('%', :cnpj, '%')")
	public List<Pessoa_Juridica> pesquisaPorCnpj(@Param("cnpj") String cnpj);

	@Query(value = "update pessoa_juridica set cnjp = cnpjLimpo where cnpj = cnpj ", nativeQuery = true)
	public String atualizaCnpjLimpo(String cnpjLimpo, String cnpj);

	

}
