package loja_virtual_3m.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import loja_virtual_3m.com.model.Acesso;
import loja_virtual_3m.com.repository.AcessoRepository;
import loja_virtual_3m.com.services.AcessoServices;

@RestController
public class AcessoControler {


	@Autowired
	private AcessoServices acessoServices;

	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody /* Pode dar um retorno a API*/
	@PostMapping (value="**/salvarAcesso")/*Mapeado a url para receber o JSON*/
	public ResponseEntity<Acesso>salvarAcesso(@RequestBody Acesso acesso){/*Recebe o JSON e convrte para Objeto*/
		return new ResponseEntity<Acesso>(acessoServices.salvar(acesso), HttpStatus.OK);
	}
	
	
	@ResponseBody /* Pode dar um retorno a API*/
	@DeleteMapping (value="**/deleteAcesso")/*Mapeado a url para receber o JSON*/
	public ResponseEntity<String> deleteAcesso(@RequestBody Acesso acesso){/*Recebe o JSON e convrte para Objeto*/
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity<String>("Excluído com sucesso",HttpStatus.OK);
	}
	
	
}
