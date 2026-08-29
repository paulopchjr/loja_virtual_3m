package loja_virtual_3m.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import loja_virtual_3m.com.model.Acesso;

@Transactional
public interface AcessoRepository  extends JpaRepository<Acesso, Long>{
	@Query("select a from Acesso a where upper(trim(a.descricao)) like CONCAT('%',upper(:desc),'%')")
	List<Acesso> buscarAcessoDescricao(@Param("desc") String desc);

}
