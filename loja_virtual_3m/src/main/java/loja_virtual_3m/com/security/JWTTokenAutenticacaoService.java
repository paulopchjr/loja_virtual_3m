package loja_virtual_3m.com.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loja_virtual_3m.com.ApplicationContextLoad;
import loja_virtual_3m.com.model.Usuario;
import loja_virtual_3m.com.repository.UsuarioRepository;

/* Criar a autenticação e retornar a autenticacao JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {

	private final ApplicationContextLoad applicationContextLoad;

	/* token de validade de 30 dias */
	private static final long EXPIRATION_TIME_10_DAYS = 864_000_000L;

	/* Senha para juntar com JWT para Criptografia */
	private static final String SECRET = "tY4rE8wQ1mN7vB9zX2kL5pQ8sW3dF6gH1jK4lP7zX0cV3bN6mQ9wE2rT5yU8iI1o";

	/*
	 * Converte a String em uma chave mais segura aceita pelas versoes modernas do
	 * JJWT
	 */
	private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	@Autowired
	JWTTokenAutenticacaoService(ApplicationContextLoad applicationContextLoad) {
		this.applicationContextLoad = applicationContextLoad;
	}

	/* Gera o token e da a respota para o cliente */
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {

		/* Montagem do token */

		String JWT = Jwts.builder() /* chama o gerador de token */
				.subject(username) /* add o user */
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_10_DAYS)) /* tem. de expiração */
				.signWith(SECRET_KEY).compact();

		String token = TOKEN_PREFIX + " " + JWT;

		/*
		 * dá resposta para tela e para o cliente, OUTRA API, NAVEGADOR, APLICATIVO, JS,
		 * OUTRA CHAMADA JAVA.
		 */
		response.addHeader(HEADER_STRING, token);

		liberacaoCors(response);

		/* Usado para o postman para teste */
		response.getWriter().write("{\"Authorization\":\"" + token + "\"}");

	}

	/* Retorna usuário validado com token ou caso não seja valido retorna null */
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String token = request.getHeader(HEADER_STRING);

		try {

			if (token != null) {

				// retirando o Berrarer
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

				/* Faz a validação do token do usuario na requisicao e obtmem o USER */

				String user = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(tokenLimpo).getPayload()
						.getSubject(); /* ADMIN, ROLES */

				if (user != null) {

					Usuario usuario = applicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
							.findUserbyLogin(user);

					if (usuario != null) {
						return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
								usuario.getAuthorities());
					}

				}
			}

			retornarErroJson(response, "Token ausente ou inválido na requisição !");

		} catch (JwtException e) {

			String msgErro = switch (e) {
			case MalformedJwtException mjwt -> "O formato do token está inválido !!!";
			case ExpiredJwtException ejwt -> "O token de acesso enviado já expirou!!!";
			case SignatureException sjwt -> "A assinatura digital do token é inválida !!";
			default -> "Erro na seguranção do token";
			};

			retornarErroJson(response, msgErro);
		} finally {
			// sempre executa a liberação do cors
			liberacaoCors(response);

		}

		return null;
	}

	/* Fazendo a liberação contra erro de cOrs no navegador */
	private void liberacaoCors(HttpServletResponse response) {

		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");

		}

		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");

		}

		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");

		}

		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");

		}

	}

	private void retornarErroJson(HttpServletResponse response, String msg) throws IOException {
		// http 401 nao autorizadoo
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("{\"erro\":\"" + msg + "\"}");
		response.getWriter().flush();
	}

}
