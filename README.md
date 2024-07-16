Este projeto é uma API criada usando Java, Spring, PostgreSQL como o banco de dados para desenvolvimento. 
Para os testes utilizei mockito e H2 como banco de dados.

Instalação:
1. Clone o repositório https://github.com/JoseDeAssis/Desafio_Goat_.git
2. Instale as dependências com o Maven

Uso:
1. Inicie a aplicação com o Maven
2. A API estará acessível em http://localhost:8080 (utilizei o postman para poder realizar as requisições)

API Endpoints:
1. Servidores
   1.1 GET /servidores -> retorna todos os servidores cadastrados
   1.2 GET /servidores/{id} -> retorna um servidor pelo ID
   1.3 GET /servidores/ + parâmetro cpf -> retorna um servidor baseado no parâmetro cpf passado
   1.4 POST /servidores -> cadastra um servidor
   1.5 PUT /servidores/{id} -> altera um servidor baseado no ID
   1.6 DELETE /servidores/{id} -> deleta um servidor baseado no ID

2. Especialização
   2.1 GET /especializacao -> retorna todos as especializações cadastradas
   2.2 GET /especializacao/{id} -> retorna uma especialização pelo ID
   2.3 GET /especializacao/ + parâmetro cpf -> retorna todas as especializações de um servidor baseado no parâmetro cpf passado
   2.4 POST /especializacao -> cadastra uma especialização
   2.5 PUT /especializacao/{id} -> altera uma especialização baseado no ID
   2.6 DELETE /especializacao/{id} -> deleta uma especialização baseado no ID
   2.6 PUT /especializacao/{id}/deferir -> defere uma especialização baseado no ID
   2.7 PUT /especializacao/{id}/indeferir + parâmetro motivoIndeferimento -> indefere uma especialização baseada no ID

DATABASE:
Este projeto utiliza PostgreSQL para desenvolvimento e H2 para testes unitários.
