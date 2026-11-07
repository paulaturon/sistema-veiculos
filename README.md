# Sistema Automotivo – Gestão de Estoque de Veículos

CRUD orientado a objetos em **Java 17 + Spring Boot 3 + MySQL**, com API REST e uma tela web simples.

---

## PASSO A PASSO PARA RODAR

### Passo 1 – Criar o banco no MySQL
1. Abra o **MySQL Workbench** e conecte no servidor local (usuário `root`).
2. Menu **File → Open SQL Script** → escolha `database/sistema_veiculos.sql`.
3. Clique no **raio ⚡** (Execute) para rodar o script inteiro.
4. Na lateral (Schemas), clique no botão de atualizar: deve aparecer `sistema_veiculos` com as tabelas `marca`, `modelo` e `veiculo`.

> O script apaga e recria o banco `sistema_veiculos`. Pode rodar quantas vezes quiser para "resetar" os dados.

### Passo 2 – Abrir o projeto no IntelliJ (do jeito certo)
1. Descompacte o `.zip` numa pasta fácil, ex.: `C:\projetos\sistema-veiculos`.
2. No IntelliJ: **File → Open** → selecione a pasta **`sistema-veiculos`** (a que tem o `pom.xml` dentro) → OK.
3. Se perguntar *"Trust project?"* → **Trust Project**. Se perguntar *"Open as Maven project?"* → **Sim**.
4. Espere a barra de progresso lá embaixo terminar (ele baixa as bibliotecas do Spring – pode levar alguns minutos na primeira vez).

> **Não crie as pastas na mão.** Quem diz ao IntelliJ que isso é um projeto Spring é o `pom.xml`.

### Passo 3 – Conferir o JDK
**File → Project Structure → Project → SDK**: precisa ser **17 ou superior** (17, 21...).
Se não tiver nenhum, clique em **Add SDK → Download JDK** → escolha a versão 21 (ex.: Eclipse Temurin).

### Passo 4 – Colocar a senha do MySQL
Abra `src/main/resources/application.properties` e troque:
```
spring.datasource.password=SUA_SENHA_AQUI
```
pela senha que você criou ao instalar o MySQL.

### Passo 5 – Rodar
Abra `src/main/java/com/concessionaria/sistemaveiculos/SistemaVeiculosApplication.java` e clique no **▶ verde** ao lado de `public class` (ou do `main`) → **Run**.

Funcionou se aparecer no final do console (aba **Run**, parte de baixo):
```
Tomcat started on port 8080 (http)
Started SistemaVeiculosApplication in X seconds
```

### Passo 6 – Usar
- Tela do sistema: **http://localhost:8080**
- API pura (JSON): **http://localhost:8080/api/veiculos**

---

## ONDE VER O ERRO

O erro aparece na **janela Run** (parte de baixo do IntelliJ, abre sozinha quando você roda).
Role até o **fim** e procure a última linha que começa com `Caused by:` – é ela que diz o motivo real.

| Mensagem no console | O que significa | Como resolver |
|---|---|---|
| `Access denied for user 'root'@'localhost'` | Senha errada | Corrija `spring.datasource.password` |
| `Communications link failure` | MySQL desligado | Inicie o serviço MySQL (Windows: "Serviços" → MySQL80 → Iniciar) |
| `Unknown database 'sistema_veiculos'` | Banco não criado | Rode o script do Passo 1 |
| `Port 8080 was already in use` | Já tem algo rodando na 8080 | Pare a execução anterior (■ vermelho) ou troque `server.port=8081` |
| `release version 17 not supported` / `invalid source release` | JDK antigo | Passo 3: use JDK 17 ou 21 |
| `package org.springframework... does not exist` / imports em vermelho | Maven não carregou | Clique com o direito no `pom.xml` → **Maven → Reload project** |
| Botão ▶ não aparece | Projeto aberto na pasta errada | Passo 2: abra a pasta que contém o `pom.xml` |

---

## ESTRUTURA (arquitetura em camadas / MVC + REST)

```
src/main/java/com/concessionaria/sistemaveiculos
├── SistemaVeiculosApplication.java   → inicia o sistema
├── model/        → entidades (EntidadeBase, Marca, Modelo, Veiculo, enums)
├── repository/   → acesso ao banco (Spring Data JPA)
├── service/      → regras de negócio
├── controller/   → endpoints REST
├── dto/          → dados de entrada da API
└── exception/    → tratamento de erros
src/main/resources
├── application.properties → configuração do banco
└── static/index.html      → tela (frontend)
database/sistema_veiculos.sql → modelo físico + dados de exemplo
```

## ENDPOINTS

| Método | URL | O que faz |
|---|---|---|
| GET | `/api/marcas` | Lista marcas |
| POST | `/api/marcas` | Cadastra marca `{"nome":"Ford","paisOrigem":"EUA"}` |
| PUT / DELETE | `/api/marcas/{id}` | Edita / remove marca |
| GET | `/api/modelos?marcaId=1` | Lista modelos (filtro opcional por marca) |
| POST | `/api/modelos` | `{"nome":"Ka","tipo":"CARRO","marcaId":6}` |
| PUT / DELETE | `/api/modelos/{id}` | Edita / remove modelo |
| GET | `/api/veiculos` | Lista veículos – filtros: `marcaId, modeloId, tipo, status, precoMin, precoMax, anoMin, anoMax` |
| GET | `/api/veiculos/{id}` | Busca um veículo |
| POST | `/api/veiculos` | `{"modeloId":1,"ano":2023,"cor":"Preto","preco":120000,"quilometragem":0,"status":"DISPONIVEL"}` |
| PUT | `/api/veiculos/{id}` | Atualiza todos os dados |
| PATCH | `/api/veiculos/{id}` | Atualiza só preço/km/status `{"preco":115000}` |
| PATCH | `/api/veiculos/{id}/vender` | Marca como VENDIDO |
| DELETE | `/api/veiculos/{id}` | Remove do estoque |

Exemplo de filtro: `http://localhost:8080/api/veiculos?status=DISPONIVEL&precoMax=140000&anoMin=2021`

## CONCEITOS DE POO APLICADOS
- **Classes e objetos**: `Marca`, `Modelo`, `Veiculo`.
- **Herança**: as três entidades estendem `EntidadeBase` (id e data de cadastro).
- **Encapsulamento**: atributos `private` com getters/setters.
- **Abstração**: `EntidadeBase` é `abstract` (não existe "entidade genérica" sozinha).
- **Métodos de comportamento**: `Veiculo.vender()`, `Veiculo.estaDisponivel()`.
- **Associação**: Veículo → Modelo → Marca (`@ManyToOne`).
- **Enums**: `TipoVeiculo`, `StatusVeiculo`.
