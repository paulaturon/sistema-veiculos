# Sistema Automotivo – Gestão de Estoque de Veículos

Sistema CRUD orientado a objetos para gestão do estoque de veículos de uma concessionária, desenvolvido em **Java 17**, **Spring Boot 3** e **MySQL**, com API REST e interface web.

**Autora:** Paula Turon de Oliveira
**Disciplina:** Object Oriented Programming

---

## Funcionalidades

- **Cadastro de veículos**: modelo, marca, ano, cor, preço, quilometragem e status.
- **Cadastro de marcas e modelos**: cada veículo está associado a um modelo, e cada modelo a uma marca.
- **Consulta e filtros**: por marca, modelo, tipo, status, faixa de preço e faixa de ano.
- **Atualização**: edição completa ou parcial (preço, quilometragem, status), além da ação de venda.
- **Remoção**: exclusão de veículos vendidos ou descontinuados.
- **Validações e regras de negócio**: campos obrigatórios, valores não negativos, quilometragem que não pode diminuir e proteção contra exclusão de marcas ou modelos em uso.

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot 3 (Web, Data JPA, Validation) | API REST, persistência e validação |
| Hibernate / JPA | Mapeamento objeto-relacional |
| MySQL 8 | Banco de dados relacional |
| Maven | Gerenciamento de dependências e build |
| HTML, CSS e JavaScript | Interface web que consome a API |

## Arquitetura

O projeto segue uma **arquitetura em camadas** (padrão MVC adaptado para API REST):

```
src/main/java/com/concessionaria/sistemaveiculos
├── model/        Entidades de domínio (EntidadeBase, Marca, Modelo, Veiculo) e enums
├── repository/   Acesso a dados com Spring Data JPA
├── service/      Regras de negócio
├── controller/   Endpoints REST
├── dto/          Objetos de entrada da API
└── exception/    Exceções e tratamento global de erros
src/main/resources
├── application.properties   Configuração da aplicação
└── static/index.html        Interface web
database/
└── sistema_veiculos.sql     Modelo físico do banco e dados de exemplo
```

**Fluxo de uma requisição:** Interface web → Controller → Service → Repository → MySQL

## Modelo de dados

```
MARCA (1) ──── (N) MODELO (1) ──── (N) VEICULO
```

- **marca**: id, nome, pais_origem, data_cadastro
- **modelo**: id, nome, tipo, marca_id (FK), data_cadastro
- **veiculo**: id, modelo_id (FK), ano, cor, preco, quilometragem, status, data_cadastro

## Conceitos de POO aplicados

- **Classes e objetos**: `Marca`, `Modelo` e `Veiculo` representam as entidades do domínio.
- **Herança**: as entidades estendem a superclasse `EntidadeBase`, que concentra `id` e `dataCadastro`.
- **Abstração**: `EntidadeBase` é uma classe abstrata.
- **Encapsulamento**: atributos privados, com acesso por getters e setters.
- **Comportamento**: métodos de negócio como `Veiculo.vender()` e `Veiculo.estaDisponivel()`.
- **Associação**: Veículo → Modelo → Marca (`@ManyToOne`).
- **Enumerações**: `TipoVeiculo` e `StatusVeiculo`.

## Como executar

**Pré-requisitos:** JDK 17 ou superior, MySQL 8 e Maven (ou uma IDE com Maven, como o IntelliJ IDEA).

1. Execute o script `database/sistema_veiculos.sql` no MySQL.
2. Em `src/main/resources/application.properties`, informe o usuário e a senha do MySQL.
3. Execute a classe `SistemaVeiculosApplication`, ou rode `mvn spring-boot:run`.
4. Acesse **http://localhost:8080**.

## Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/marcas` | Lista marcas |
| GET | `/api/marcas/{id}` | Busca marca por id |
| POST | `/api/marcas` | Cadastra marca |
| PUT | `/api/marcas/{id}` | Atualiza marca |
| DELETE | `/api/marcas/{id}` | Remove marca |
| GET | `/api/modelos?marcaId=` | Lista modelos (filtro opcional por marca) |
| POST | `/api/modelos` | Cadastra modelo |
| PUT | `/api/modelos/{id}` | Atualiza modelo |
| DELETE | `/api/modelos/{id}` | Remove modelo |
| GET | `/api/veiculos` | Lista veículos com filtros opcionais |
| GET | `/api/veiculos/{id}` | Busca veículo por id |
| POST | `/api/veiculos` | Cadastra veículo |
| PUT | `/api/veiculos/{id}` | Atualiza todos os dados do veículo |
| PATCH | `/api/veiculos/{id}` | Atualiza preço, quilometragem e/ou status |
| PATCH | `/api/veiculos/{id}/vender` | Registra a venda do veículo |
| DELETE | `/api/veiculos/{id}` | Remove veículo |

**Filtros disponíveis em `/api/veiculos`:** `marcaId`, `modeloId`, `tipo`, `status`, `precoMin`, `precoMax`, `anoMin`, `anoMax`

Exemplo: `GET /api/veiculos?status=DISPONIVEL&precoMax=140000&anoMin=2021`

Exemplo de cadastro:
```
POST /api/veiculos
{
  "modeloId": 1,
  "ano": 2023,
  "cor": "Preto",
  "preco": 120000.00,
  "quilometragem": 0,
  "status": "DISPONIVEL"
}
```