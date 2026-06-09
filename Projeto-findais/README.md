# Projeto Findais — Senac Solutions

Sistema de Gestão de Ordens de Serviço  
UC4 – Projeto Integrador | Faculdade Senac Blumenau | Jovem Programador 2025

---

## Tecnologias

- Java 20+
- MySQL 8+
- Git / GitHub

---

## Estrutura do Projeto

```
Projeto-findais/
├── src/
│   └── senac/solutions/
│       ├── model/          # Classes de domínio (Cliente, Equipamento, OrdemServico, StatusOS)
│       ├── dao/            # Acesso ao banco de dados (CRUD)
│       ├── service/        # Regras de negócio
│       ├── util/           # ConexaoDB
│       └── Main.java       # Ponto de entrada
├── sql/
│   └── banco.sql           # Script de criação do banco de dados
└── README.md
```

---

## Como executar

### 1. Criar o banco de dados

Abra o MySQL e execute:
```sql
source caminho/para/sql/banco.sql
```

### 2. Configurar a conexão

Edite o arquivo `src/senac/solutions/util/ConexaoDB.java`:
```java
private static final String URL     = "jdbc:mysql://localhost:3306/senac_solutions";
private static final String USUARIO = "root";
private static final String SENHA   = "sua_senha_aqui";
```

### 3. Adicionar o driver MySQL

Baixe o conector JDBC em: https://dev.mysql.com/downloads/connector/j/  
Adicione o `.jar` ao classpath do projeto.

### 4. Compilar e rodar

```bash
javac -cp ".;mysql-connector.jar" src/senac/solutions/**/*.java
java  -cp ".;mysql-connector.jar" senac.solutions.Main
```

---

## Branches

| Branch | Finalidade |
|---|---|
| `main` | Versão estável / entrega final |
| `develop` | Integração das funcionalidades |
| `feature/clientes` | CRUD de clientes |
| `feature/equipamentos` | CRUD de equipamentos |
| `feature/ordens-servico` | Módulo de OS |

---

## Equipe

- Isaac Oliveira  
- [Adicione os demais membros da equipe]
