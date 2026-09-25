package loja_virtual_3m.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpSessionListener;
import loja_virtual_3m.com.services.ImplUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity implements HttpSessionListener {

	private ImplUserDetailsService implUserDetailsService;

	@Autowired
	public WebConfigSecurity(ImplUserDetailsService userDetailService) {

		this.implUserDetailsService = userDetailService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		//// ⚠️ Libera as outras rotas temporariamente para seu app subir
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/**/salvarAcesso").permitAll()
						.requestMatchers(HttpMethod.POST, "/**/salvarAcesso").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/**/deleteAcesso").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/**/deleteAcessoId/{id}").permitAll()
						.requestMatchers(HttpMethod.GET, "/**/buscarAcessoid/{id}").permitAll()
						.requestMatchers(HttpMethod.GET, "/buscarAcesso/{desc}").permitAll().anyRequest()
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
				.requestMatchers(HttpMethod.GET, "/buscarAcesso/{desc}")
				.requestMatchers(HttpMethod.GET, "/**/buscarAcessoid/{id}");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BcryptPassword4jPasswordEncoder();
	}

	
	/*Autenticacao para qualquer lugar do projeto, ou controller, no caso controller login*/
	@Bean
	public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) throws Exception {

		/* Validação de dados, pegando o usuario no banco.*/
		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider(implUserDetailsService);
		
		/* checando a senha*/
		authprovider.setPasswordEncoder(passwordEncoder); 

		return new ProviderManager(authprovider);

	}

}