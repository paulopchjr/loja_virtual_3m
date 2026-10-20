package loja_virtual_3m.com.controller;

import loja_virtual_3m.com.repository.PessoaFisicaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import loja_virtual_3m.com.Exceptions.ExcessoesCustomizadas;
import loja_virtual_3m.com.model.Pessoa_Fisica;
import loja_virtual_3m.com.model.Pessoa_Juridica;
import loja_virtual_3m.com.repository.PessoaReppository;
import loja_virtual_3m.com.repository.UsuarioRepository;
import loja_virtual_3m.com.services.PessoaUsuarioService;
import loja_virtual_3m.util.ValidaCNPJ;
import loja_virtual_3m.util.ValidaCPF;

@RestController
public class PessoaControler {

	private final PessoaFisicaRepository pessoaFisicaRepository;
	private PessoaUsuarioService pessoaUsuarioService;
	private PessoaReppository pessoaReppository;
	private UsuarioRepository usuarioRepository;

	public PessoaControler(PessoaUsuarioService pUserService, PessoaReppository pReppository,
			PessoaFisicaRepository pessoaFisicaRepository, UsuarioRepository usuarioRepository) {

		this.pessoaUsuarioService = pUserService;
		this.pessoaReppository = pReppository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@ResponseBody
	@PostMapping(value = "/salvarPj")
	public ResponseEntity<Pessoa_Juridica> savePj(@RequestBody Pessoa_Juridica pj) throws ExcessoesCustomizadas {

		if (pj == null) {

			throw new ExcessoesCustomizadas("Pessoa Jurídica, não pode ser Null!");

		}

		if (pj.getId() == null && pessoaReppository.contemCnpjCadastrado(pj.getCnpj()) != null) {

			throw new ExcessoesCustomizadas("Já existe CNPJ registrado no Sistema!! \n CNPJ: " + pj.getCnpj());

		}

		if (!ValidaCNPJ.isCNPJ(pj.getCnpj())) {
			throw new ExcessoesCustomizadas("CNPJ: " + pj.getCnpj() + " está inválido.");

		}

		pj = pessoaUsuarioService.savePessoaJ(pj);

		return new ResponseEntity<Pessoa_Juridica>(pj, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "/salvarPf")
	public ResponseEntity<Pessoa_Fisica> savePf(@RequestBody Pessoa_Fisica pf) throws ExcessoesCustomizadas {

		if (pf == null) {

			throw new ExcessoesCustomizadas("Pessoa Fisíca, não pode ser Null!");

		}

		if (pf.getId() == null && pessoaFisicaRepository.contemCpfCadastrado(pf.getCpf()) != null) {

			throw new ExcessoesCustomizadas("Já existe CPF registrado no Sistema!! \n CPF: " + pf.getCpf());

		}

		if (!ValidaCPF.isCPF(pf.getCpf())) {
			throw new ExcessoesCustomizadas("CNPJ: " + pf.getCpf() + " está inválido.");

		}
		
		if (pf.getEmail() == null || usuarioRepository.findByEmail(pf.getEmail()) !=null) {
			
			throw new ExcessoesCustomizadas("Já existe esse email cadastrado no sistema!! \n Email:" + pf.getEmail());
			
		}
		
		

		pf = pessoaUsuarioService.savePessoaFisica(pf);

		return new ResponseEntity<Pessoa_Fisica>(pf, HttpStatus.OK);
	}

}
