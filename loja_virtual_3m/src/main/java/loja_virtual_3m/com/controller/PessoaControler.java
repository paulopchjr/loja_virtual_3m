package loja_virtual_3m.com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import loja_virtual_3m.com.Exceptions.ExcessoesCustomizadas;
import loja_virtual_3m.com.model.Pessoa_Juridica;
import loja_virtual_3m.com.repository.PessoaReppository;
import loja_virtual_3m.com.services.PessoaUsuarioService;

@RestController
public class PessoaControler {

	private PessoaUsuarioService pessoaUsuarioService;
	private PessoaReppository pessoaReppository;

	public PessoaControler(PessoaUsuarioService pUserService, PessoaReppository pReppository) {

		this.pessoaUsuarioService = pUserService;
		this.pessoaReppository = pReppository;
	}

	@ResponseBody
	@PostMapping(value = "/salvarPj")
	public ResponseEntity<Pessoa_Juridica> savePj(@RequestBody Pessoa_Juridica pj) throws ExcessoesCustomizadas {

		if (pj == null) {

			throw new ExcessoesCustomizadas("Pessoa Jurídica, não pode ser Null!");

		}

		if (pj.getId() == null && pessoaReppository.contemCnpjCadastrado(pj.getCnpj()) != null) {

			throw new ExcessoesCustomizadas("Já existe CNPJ registrado no Sistema!! \n CNPJ: "+pj.getCnpj());

		}
		
		
		
		
		pj = pessoaUsuarioService.savePessoaJ(pj);

		return new ResponseEntity<Pessoa_Juridica>(pj, HttpStatus.OK);
	} 

}
