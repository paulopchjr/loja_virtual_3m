package loja_virtual_3m.com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity implements HttpSessionListener {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		//// ⚠️ Libera as outras rotas temporariamente para seu app subir
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/**/salvarAcesso").permitAll()
						.requestMatchers(HttpMethod.POST, "/**/salvarAcesso").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/**/deleteAcesso").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/**/deleteAcessoId/{id}").permitAll()
						.requestMatchers(HttpMethod.GET, "/**/buscarAcessoid/{id}").permitAll()
						.requestMatchers(HttpMethod.GET,"/buscarAcesso/{desc}").permitAll()
						.anyRequest()
						.authenticated())// todos os endpoints exigem validacao

				// ativa autenticacao para o postman
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// Corrigido: adicionada a barra '/' no início de todas as rotas
		return (web) -> web.ignoring().requestMatchers(HttpMethod.GET, "/**/salvarAcesso")
				.requestMatchers(HttpMethod.POST, "/**/salvarAcesso")
				.requestMatchers(HttpMethod.DELETE, "/**/deleteAcesso")
				.requestMatchers(HttpMethod.DELETE, "/**/deleteAcessoId/{id}")
				.requestMatchers(HttpMethod.GET,"/buscarAcesso/{desc}")
				.requestMatchers(HttpMethod.GET, "/**/buscarAcessoid/{id}");
	}
}