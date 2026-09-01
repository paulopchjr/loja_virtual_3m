package loja_virtual_3m.com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;
import loja_virtual_3m.com.services.AcessoServices;

@RestController
public class AcessoControler {

	private AcessoServices acessoServices;
	private AcessoRepository acessoRepository;

	public AcessoControler(AcessoServices servicesAcesso, AcessoRepository repositoryAcesso) {
		// injeção de depedencias, não precisa da antoação @Autowired;
		this.acessoServices = servicesAcesso;
		this.acessoRepository = repositoryAcesso;
	}

	@ResponseBody /* Pode dar um retorno a API */
	@PostMapping(value = "**/salvarAcesso") /* Mapeado a url para receber o JSON */
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {/* Recebe o JSON e convrte para Objeto */
		return new ResponseEntity<Acesso>(acessoServices.salvar(acesso), HttpStatus.OK);
	}

	@ResponseBody /* Pode dar um retorno a API */
	@DeleteMapping(value = "**/deleteAcesso") /* Mapeado a url para receber o JSON */
	public ResponseEntity<String> deleteAcesso(@RequestBody Acesso acesso) {/* Recebe o JSON e convrte para Objeto */
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity<String>("Excluído com sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/deleteAcessoId/{id}")
	public ResponseEntity<String> deleteAcessoPorId(@PathVariable("id") Long id) {

		acessoRepository.deleteById(id);

		return new ResponseEntity<String>("Excluído com sucesso", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscarAcessoid/{id}")
	public ResponseEntity<Acesso> buscarAcessoPorid(@PathVariable("id") Long id) {

		Acesso acesso = acessoRepository.findById(id).get();

		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscarAcesso/{desc}")
	public ResponseEntity<List<Acesso>> buscarAcessoDescricao(@PathVariable("desc") String descricao) {

		List<Acesso> acessos = acessoRepository.buscarAcessoDescricao(descricao);

		return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
	}

}
