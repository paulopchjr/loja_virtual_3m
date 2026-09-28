package loja_virtual_3m.com.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpSessionListener;
import loja_virtual_3m.com.services.ImplUserDetailsService;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebConfigSecurity implements HttpSessionListener {

	private ImplUserDetailsService implUserDetailsService;
	private final JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

	
	public WebConfigSecurity(ImplUserDetailsService userDetailService,
			JWTTokenAutenticacaoService jwtTokenAutenticacaoService) {
		this.implUserDetailsService = userDetailService;
		this.jwtTokenAutenticacaoService = jwtTokenAutenticacaoService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManager authenticationManager = authenticationManager(passwordEncoder());

		JWTLoginFilter jwtLoginFilter = new JWTLoginFilter("/login", authenticationManager,
				jwtTokenAutenticacaoService);
		JwtApiAutenticacaoFilter jwtApiAutenticacaoFilter = new JwtApiAutenticacaoFilter(jwtTokenAutenticacaoService);

		http
				// Configuração de CORS E CSRF
				.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())

				// REGRAS DE AUTORIZAÇÃO
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/", "/index").permitAll().anyRequest().authenticated())

				// SESSAO DE AUTORIZAÇÃO PARA API REST COM JWT
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// FILTROS ordenados para validação de login e autenticacao da API
				.addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(jwtApiAutenticacaoFilter, UsernamePasswordAuthenticationFilter.class)

				/* CONF DE LOGOUT */
				.logout(logout -> logout.logoutUrl("/logout") // A URL que o usuário vai chamar para deslogar
						.logoutSuccessUrl("/index").invalidateHttpSession(true)// destroi a sessao do sevidor
						.clearAuthentication(true)// limpa os dados de autenticacao
						.deleteCookies("JSESSIONID")); // apaga o cookie da sessacao do navegador

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// Corrigido: adicionada a barra '/' no início de todas as rotas
		return (web) -> web.ignoring().requestMatchers("/", "/index");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * Autenticacao para qualquer lugar do projeto, ou controller, no caso
	 * controller login
	 */
	@Bean
	public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) throws Exception {

		/* Validação de dados, pegando o usuario no banco. */
		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
		authprovider.setUserDetailsService(implUserDetailsService);

		/* checando a senha */
		authprovider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authprovider);

	}

	/* METODO QUE LIBERA O cOrs do navegador */

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		/* permite qualquer origem */
		configuration.setAllowedOrigins(Arrays.asList("*"));

		/* liberação de metodos */
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

		/* Lberação do cabecalho das requisições */
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));

		/* Expoe o cabecalho para ferramentas de testes */
		configuration.setExposedHeaders(Arrays.asList("Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return (CorsConfigurationSource) source;

	}

}