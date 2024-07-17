# e-commerce

É um sistema de e-commerce composto por quatro módulos de microserviços principais: Usuário, Gestão de Itens e Estoque, Carrinho de Compras e Pagamento. Essa abordagem permite uma melhor modularização e escalabilidade, garantindo maior flexibilidade e eficiência operacional.

O **módulo Usuário** é responsável pelo gerenciamento de informações e pela criação de acesso. Por meio deste acesso, realizamos a autenticação e autorização dos demais microserviços via JWT.

O **módulo Gestão de Itens e Estoque** controla o estoque, acompanha o fluxo de entrada e saída de produtos, e conta com um fluxo de importação massiva de produtos..

O **módulo Carrinho de Compras** trata da funcionalidade de escolher produtos para compra, permitindo inserir ou remover produtos do carrinho, visualizar os itens e seus respectivos valores.

Por fim, o **módulo de Pagamento** realiza uma simulação de pagamento por PIX, cartão de crédito ou débito. Também podemos consultar o status do pagamento, verificando se foi bem-sucedido ou não

## Link do GitHub do projeto

https://github.com/FlaviaMonteiro31/e-commerce.git


## Start do projeto 


1º - Ter instalado Docker na máquina
2º - Executar o comando docker:

> docker-compose build

> docker-compose up 

## Tecnologias utilizadas

* Java 17 (Padrão Spring Initializr)

* Spring boot 3.1.0 (Padrão Spring Initializr)

* DevTools (Facilitar setup no ambiente de desenvolvimento dando Restart no servidor a cada modificação feita)

* Lombok (Facilitar criação de métodos acessores e construtores quando necessário)

* Spring Web (Para usar uma API REST)

* Open API (Habilitar Swagger) URL: **/swagger-ui/index.html*

* Spring Data JPA (Para implementar paginação)

* Bean Validation (Para fazer validações de campos na borda mais externa da API, as REQUESTS)

* Spring Batch (Para realizar a carga massiva de produtos)

* Spring Function (Para criar API para consumo)

* Spring Integration (Para integração de APIs baseado em mensagens)

* GIT (Controle de versão do projeto)

* IDE's (Eclipse, Intellij, VS Code)

* Insomnia (Testes da API)

* PostgreSQL(Persistir os dados)

* Docker (Para subir um container com mysql)

## Documentação do Swagger

MS_Usuarios: [Swagger_Ms_Usuarios.json](https://github.com/FlaviaMonteiro31/e-commerce/blob/main/Ms_Usuarios.yaml)

MS_Gestao_Itens: [Swagger_Ms_Gestao_Itens.json](https://github.com/FlaviaMonteiro31/e-commerce/blob/main/Ms_Gestao_Itens.yaml)

MS_Carrinho_Compras: [Swagger_Ms_Carrinho.json](https://github.com/FlaviaMonteiro31/e-commerce/blob/main/Ms_Carrinho.yaml)

MS_Pagamentos: [Swagger_Ms_Pagamentos.json](https://github.com/FlaviaMonteiro31/e-commerce/blob/main/Ms_Pagamentos.yaml)

## Requisições HTTP/Insomnia

Para testes dos micro serviços foram utilizados as requisições do [link](https://github.com/FlaviaMonteiro31/e-commerce/blob/main/Insomnia_Fase5) no Insomnia. 

