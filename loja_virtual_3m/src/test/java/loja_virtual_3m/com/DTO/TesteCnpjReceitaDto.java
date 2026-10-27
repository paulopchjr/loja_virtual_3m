package loja_virtual_3m.com.DTO;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import loja_virtual_3m.com.LojaVirtual3mApplication;

@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = LojaVirtual3mApplication.class)
@AutoConfigureMockMvc
public class TesteCnpjReceitaDto {

	@Test
	void consultaListaSecundaria() {

		String url = "https://receitaws.com.br/v1/cnpj/17849323000157";
		RestTemplate restTemplate = new RestTemplate();

		ConsultaCnpjDto resultado = restTemplate.getForObject(url, ConsultaCnpjDto.class);

		if (resultado != null && resultado.getAtividades_secundarias() != null) {
			List<AtividadeDto> secundarias = resultado.getAtividades_secundarias();

			for (AtividadeDto atividadeDto : secundarias) {
				System.out.println("Código: " + atividadeDto.getCode() + "| Descrição: " + atividadeDto.getText());
			}
		}

	}

	@Test
	void testQsa() {
		try {

			String url = "https://receitaws.com.br/v1/cnpj/17849323000157";
			RestTemplate restTemplate = new RestTemplate();

			ConsultaCnpjDto resultado = restTemplate.getForObject(url, ConsultaCnpjDto.class);

			if (resultado != null && resultado.getQsa() != null) {
				List<QsaDto> qsaDtos = resultado.getQsa();

				for (QsaDto qsa : qsaDtos) {
					System.out.println("Nome: " + qsa.getNome() + "| Qual: " + qsa.getQual());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
