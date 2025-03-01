# Sistema de Replicação Ativa de Banco de Dados

## Objetivo

Este trabalho tem como objetivo desenvolver um sistema de replicação ativa de banco de dados baseado no conceito de comunicação em grupo. A aplicação simula um ambiente distribuído composto por:

- **Líder:** Recebe mensagens/comandos SQL enviados via uma API REST, persiste os dados localmente e os replica para as réplicas.
- **Réplicas (Database):** Módulos que representam bancos de dados replicados, os quais salvam as mensagens ou executam os comandos SQL recebidos via RMI e persistem os dados localmente.

## Implementação

A solução foi implementada em Java 23, utilizando Spring Boot para criação da API REST, Spring Data JPA para persistência com um banco H2 em memória e a API RMI do Java para comunicação entre os módulos.

### Funcionalidades

- **Dois Endpoints no Líder:**
    - **/message:** Recebe uma mensagem simples, que é persistida no banco do líder e replicada para as réplicas.
    - **/sql:** Recebe um comando SQL completo, que é executado no banco do líder (efetuando a operação, por exemplo, um INSERT) e replicado para as réplicas, que também executam o comando.
- **Registro de Membros:**  
    O líder possui um endpoint para registrar as réplicas, que se expõem via RMI. (**/register**)
- **Execução Remota:**  
    O líder faz o lookup dos serviços remotos (stubs) das réplicas e, via RMI, invoca o método `executeCommand` para replicar os comandos.

## Tecnologias Utilizadas

- **Java 23**
- **Spring Boot**
- **Spring Data JPA**
- **H2 (banco de dados em memória)**
- **Java RMI**

### Passos para Testar o Projeto

1. **Clone o Repositório:**
    
2. **Execute as Aplicações:**
    
    - **Módulo Leader:** Execute a classe `LeaderApplication` (porta **8080**).
    - **Módulo Database:** Execute as classes `Database1Application` e `Database2Application` (configuradas nas portas **8081** e **8082**, respectivamente).
3. **Registro de Réplicas:**
    
    - Envia uma requisição **POST** para o endpoint de registro do Leader:
        
        `POST http://localhost:8080/register?rmiUrl=rmi://localhost/Database1Service`
        
        (Repita para o módulo Database2)
4. **Envio de Comandos:**
    
    - **Mensagem Simples:**  
        Envie um **POST** para:
        
        `http://localhost:8080/message`
        
        `{   "message": "Olá, mundo!" }`
        
    - **Comando SQL:**  
        Envie um **POST** para:
        
        `http://localhost:8080/sql`
        
        `{   "message": "insert into message_entity (id, message) values (1, 'Olá, SQL!')" }`
        
5. **Verificação:**
    
    - Acesse o H2 Console de cada módulo:
        - Leader: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
        - Database1: [http://localhost:8081/h2-console](http://localhost:8081/h2-console)
        - Database2: [http://localhost:8082/h2-console](http://localhost:8082/h2-console)
    - Realize um **SELECT** na tabela `message_entity` para confirmar que os dados foram inseridos corretamente em todos os bancos.