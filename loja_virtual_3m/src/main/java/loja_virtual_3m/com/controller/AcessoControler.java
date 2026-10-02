package loja_virtual_3m.com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import loja_virtual_3m.com.Exceptions.ExcessoesCustomizadas;
import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;
import loja_virtual_3m.com.services.AcessoServices;

@RestController
public class AcessoControler {

	private AcessoServices acessoServices;
	private AcessoRepository acessoRepository;

	public AcessoControler(AcessoServices servicesAcesso, AcessoRepository repositoryAcesso) {
		// injeção de depedencias, recomendada por construtor (dispensa @Autowired);
		this.acessoServices = servicesAcesso;
		this.acessoRepository = repositoryAcesso;
	}

	@PostMapping(value = "/salvarAcesso") /* Mapeado a url para receber o JSON */
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExcessoesCustomizadas {/* Recebe o JSON e convrte para Objeto */
		
		if (acesso.getId() == null) {
			List<Acesso> acessos = acessoRepository.buscarAcessoDescricao(acesso.getDescricao().toUpperCase());
			
			if (!acessos.isEmpty()) {
				throw new ExcessoesCustomizadas("Já existe esse Acesso com a descrição:"+acesso.getDescricao());
			}
		}
		return new ResponseEntity<Acesso>(acessoServices.salvar(acesso), HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteAcesso") 
	public ResponseEntity<String> deleteAcesso(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity<String>("Excluído com sucesso", HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteAcessoId/{id}")
	public ResponseEntity<String> deleteAcessoPorId(@PathVariable("id") Long id) {

		acessoRepository.deleteById(id);

		return new ResponseEntity<String>("Excluído com sucesso", HttpStatus.OK);
	}

	
	//@Secured({ "ROLE_ADMIN" }) ->Liberar quando o controle de perfis estiver ativo
	@ResponseBody
	@GetMapping(value = "/buscarAcessoid/{id}")
	public ResponseEntity<Acesso> buscarAcessoPorid(@PathVariable("id") Long id) throws ExcessoesCustomizadas {

		Acesso acesso = acessoRepository.findById(id).orElse(null);

		if(acesso ==null) {
			throw new ExcessoesCustomizadas("Não encontrou o acesso com código: "+ id);
		}
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}

	
	@GetMapping(value = "/buscarAcesso/{desc}")
	public ResponseEntity<List<Acesso>> buscarAcessoDescricao(@PathVariable("desc") String descricao) {

		List<Acesso> acessos = acessoRepository.buscarAcessoDescricao(descricao.toUpperCase());

		return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
	}

}
