package loja_virtual_3m.com;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EntityScan(basePackages = "loja_virtual_3m.com.model") /* mapea onde esta as classes de model, que geram as tabelas do banco */
@ComponentScan(basePackages = {"loja_virtual_3m.*"}) /*varre todo o projeto e os recurssos do springboot, ao rodar no servidor */
@EnableJpaRepositories(basePackages = {"loja_virtual_3m.com.repository"})/* mapea o pacotes do repository*/
@EnableTransactionManagement /* gerencia as transacoes no banco de dados*/
public class LojaVirtual3mApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtual3mApplication.class, args);
	}

	@Bean
	public Executor getAsymcExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assycono Thread");
		executor.initialize();
		return executor;

	}
	
	
}
