package loja_virtual_3m.com.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;

/* Criar a autenticação e retornar a autenticacao JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {

	/* token de validade de 30 dias */
	private static final long EXPIRATION_TIME_10_DAYS = 864_000_000L;

	/* Senha para juntar com JWT para Criptografia */
	private static final String SECRET = "lojavirtual3m_Mai_Men_Mar_chave";
	
	/* Converte a String em uma chave mais segura aceita pelas versoes modernas do JJWT*/
	private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	/* Gera o token e da a respota para o cliente */
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {

		/* Montagem do token */

		String JWT = Jwts.builder() /* chama o gerador de token */
				.setSubject(username) /* add o user */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_10_DAYS)) /* tem. de expiração */
				.signWith(SECRET_KEY,SignatureAlgorithm.HS512).compact();

		
		String token = TOKEN_PREFIX +" "+ JWT;
		
		/*dá resposta para tela e para o cliente, OUTRA API, NAVEGADOR, APLICATIVO, JS, OUTRA CHAMADA JAVA.*/
		response.addHeader(HEADER_STRING, token);
		
	
		/*Usado para o postman para teste*/
		response.getWriter().write("{\"Authorization\":\""+token+"\"}");
		
		
	}

}
