package loja_virtual_3m.com.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*filtro onde todas as requisções serao capturadas p/ autenticar*/
public class JwtApiAutenticacaoFilter extends GenericFilterBean {

	private final JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

	public JwtApiAutenticacaoFilter(JWTTokenAutenticacaoService serviceAutenticacaoJwt) {

		this.jwtTokenAutenticacaoService = serviceAutenticacaoJwt;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			/* Estebelece autenticacao do usuario */

			Authentication authentication = jwtTokenAutenticacaoService.getAuthentication((HttpServletRequest) request,
					(HttpServletResponse) response);

			if (authentication != null) {

				/* Coloca o processo de autenticacao para o spring security */

				SecurityContextHolder.getContext().setAuthentication(authentication);

			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		chain.doFilter(request, response);

	}

}
