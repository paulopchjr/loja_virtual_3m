# 🛒 Projeto Loja Virtual (Em Desenvolvimento) - Back-End

Bem-vindo ao repositório do back-end da loja virtual que estou desenvolvendo! 

Diferente de um projeto feito apenas para cumprir tabela em um curso, a história por trás deste código é muito pessoal: 
*"estou construindo este sistema sob medida para informatizar e impulsionar o negócio real da minha irmã." *

Como estou no meu processo de transição de carreira para me tornar um *Desenvolvedor Java Júnior*, decidi usar essa necessidade real da minha família
como o meu maior laboratório de testes e aprendizado. 
O projeto lida com regras de negócio complexas e de nível corporativo, como controle financeiro (`ContaPagar`/`ContaReceber`), 
permissões de usuários e emissão de notas fiscais.

⚠️ **Status do Projeto:** O sistema está em pleno desenvolvimento. 
Estou construindo e testando cada camada passo a passo junto com as minhas aulas, 
garantindo que o código seja seguro e escalável.

---

## 🛠️ Por que escolhi estas Tecnologias?

Para garantir que o negócio da minha irmã rode sem travamentos e com a máxima segurança que o mercado exige hoje, 
optei por trabalhar com a stack mais recente e estável do ecossistema Java:

*   **Java 21 & Spring Boot (versões estáveis recentes):
*   ** Escolhi usar o Java moderno para já me adequar às práticas atuais das grandes empresas,
*   aproveitando os recursos de performance mais novos da linguagem.
*
*   **Spring Security:**
*   Implementado para criar a segurança estrutural das requisições HTTP (as "catracas" de acesso).
*   Isso garante que dados sensíveis de clientes e do financeiro fiquem blindados contra acessos não autorizados.
*
*   **PostgreSQL & Hibernate 7:** O banco de dados relacional escolhido pela robustez.
*   Configurei e otimizei manualmente o pool de conexões (HikariCP) e os dialetos de comunicação para que o sistema rode de forma leve no ambiente
*   local de desenvolvimento.
*
*   **JUnit 5 & Mockito (Minha rede de segurança):
*   **Esta é a parte que mais me orgulho**. Escrevo testes automatizados para cada rota criada. Uso o **JUnit para testes de integração com o banco** e
*   o **Mockito para testes unitários** super rápidos (rodando em milissegundos) isolando as regras de negócio através de dublês de código.

*  
*   **Postman:** Utilizado como minha ferramenta principal para o consumo, validação e teste manual dos payloads JSON enviados para os endpoints da API,
*   garantindo que as rotas respondam com os status HTTP corretos (como `200 OK`) antes mesmo de integrar com o front-end.


---

## 🎯 Minhas Maiores Conquistas Técnicas neste Projeto

No dia a dia do desenvolvimento, encarar um sistema moderno me trouxe desafios reais de mercado que resolvi com sucesso:
1.  **Ajustes de Infraestrutura:** Identifiquei e corrigi problemas de estouro de conexões no PostgreSQL ajustando o pool do Hikari de forma realista para o ambiente local.
2.  **Arquitetura Limpa:** Optei por remover automatizadores como o Spring Data REST para assumir o controle total dos meus próprios Controllers manuais, garantindo maior flexibilidade nas rotas.
3.  **Cultura de Testes:** Garanto a famosa "barra verde" no JUnit, garantindo que alterações futuras no código não quebrem as regras que já estão funcionando.

---

## 🧑‍💻 Sobre Mim

*   **Paulo Cezar**
*   Buscando minha primeira oportunidade como **Desenvolvedor Java Júnior** (Meta: Concluir essa transição ainda este ano! 🚀)
*   Se quiser acompanhar minha jornada ou trocar uma ideia sobre Java: [Meu LinkedIn ](https://www.linkedin.com/in/paulocezarhenriquejr/)
