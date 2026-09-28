package loja_virtual_3m.com.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loja_virtual_3m.com.model.Usuario;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

	/* Configurando o gerenciador de autenticacao */
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager,
			JWTTokenAutenticacaoService jtokenService) {

		/* Obriga a autenticacao da url */
		super(new AntPathRequestMatcher(url));

		/* gerenciador de autenticacao */
		setAuthenticationManager(authenticationManager);

		this.jwtTokenAutenticacaoService = jtokenService;
	}

	/* Retorna o usuario ao processar a autenticação */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		/* Obtem usuario */
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

		/* retorna o user com login e senha */
		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		try {
			jwtTokenAutenticacaoService.addAuthentication(response, authResult.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		
		// define o status do http como 401 Não autorizado
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		//define o tipo de conteudo e enconding no cabecalho
		response.setContentType("text/plain;charset=UTF-8");
		
		
		// ESCREVENDO A MENSAGEM CUSTOMIZADA
		if(failed instanceof BadCredentialsException) {
			response.getWriter().write("Usuário e senha não encontrado");
		}else {
			response.getWriter().write("falha ao logar " + failed.getMessage());
		}
		
		
	}
	
	
}
