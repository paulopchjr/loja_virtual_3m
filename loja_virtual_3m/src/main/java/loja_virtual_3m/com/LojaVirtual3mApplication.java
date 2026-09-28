package loja_virtual_3m.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "loja_virtual_3m.com.model") /* mapea onde esta as classes de model, que geram as tabelas do banco */
@ComponentScan(basePackages = {"loja_virtual_3m.*"}) /*varre todo o projeto e os recurssos do springboot, ao rodar no servidor */
@EnableJpaRepositories(basePackages = {"loja_virtual_3m.com.repository"})/* mapea o pacotes do repository*/
@EnableTransactionManagement /* gerencia as transacoes no banco de dados*/
public class LojaVirtual3mApplication {

	public static void main(String[] args) {

		System.out.println( new BCryptPasswordEncoder().encode("admin"));
		
		SpringApplication.run(LojaVirtual3mApplication.class, args);
	}

}
