# Alagoas Medical — Backend de Atendimento via WhatsApp

API REST em **Java + Spring Boot** para automatizar a triagem inicial do atendimento da Alagoas Medical no WhatsApp. O cliente é guiado por um menu interativo que identifica a necessidade e o direciona ao atendente humano quando preciso, reduzindo o tempo da equipe com perguntas repetitivas.

> 🚧 **Status:** em desenvolvimento, para uso interno da Alagoas Medical. O núcleo de triagem (menu, validação, tratamento de erros e testes) já está implementado. A integração com a API oficial do WhatsApp é o próximo passo do roadmap.

---

## Tech stack

- **Java 21**
- **Spring Boot** — Spring Web (REST) e Bean Validation
- **Maven** (wrapper incluído)
- **Spring Data JPA + H2** — configurados para a etapa de persistência (ver roadmap)
- **JUnit 5 + Mockito + MockMvc** — testes automatizados

---

## Funcionalidades já implementadas

- Endpoint REST que recebe a mensagem do cliente e devolve a resposta do menu
- Menu de triagem com 4 opções + fallback para entradas inválidas
- Estados de fluxo (`nextAction`) para orquestrar o atendimento (aguardar opção, aguardar atendente, transferir para humano)
- Validação de entrada (campos obrigatórios) com Bean Validation
- Tratamento centralizado de erros com resposta padronizada (`@RestControllerAdvice`)
- Cobertura por testes unitários e de camada web

---

## Como rodar

Pré-requisito: **Java 21**.

```bash
cd backend
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.
Console do H2 (ambiente de dev): `http://localhost:8080/h2-console`

---

## Endpoints

### `GET /health`

Health check da aplicação.

```
200 OK
Backend Alagoas Medical online
```

### `POST /menu/message`

Processa a mensagem do cliente e retorna a resposta do menu.

**Request**

```json
{
  "from": "5582999999999",
  "message": "1"
}
```

**Response — `200 OK`**

```json
{
  "to": "5582999999999",
  "message": "Você escolheu *Curativos e coberturas*. ...",
  "nextAction": "AGUARDAR_ATENDENTE"
}
```

**Opções do menu**

| `message`      | Opção                          | `nextAction`        |
| -------------- | ------------------------------ | ------------------- |
| `1`            | Curativos e coberturas         | `AGUARDAR_ATENDENTE`|
| `2`            | Terapia por pressão negativa   | `AGUARDAR_ATENDENTE`|
| `3`            | Produtos hospitalares          | `AGUARDAR_ATENDENTE`|
| `4`            | Falar com atendente            | `TRANSFERIR_HUMANO` |
| qualquer outra | Reexibe o menu principal       | `AGUARDAR_OPCAO`    |

**Erros de validação — `400 Bad Request`**

Quando `from` ou `message` vêm vazios, ou o corpo é um JSON inválido:

```json
{
  "status": 400,
  "erro": "Requisição inválida",
  "detalhes": ["O campo 'from' é obrigatório"]
}
```

---

## Testes

```bash
cd backend
./mvnw test
```

- **`MenuServiceTest`** — testes unitários da lógica de triagem: todas as opções do menu, fallback, mensagem só com espaços e preservação do número do remetente.
- **`MenuControllerTest`** — testes da camada web (`@WebMvcTest` + MockMvc): respostas `200` e `400`, JSON inválido e corpo ausente.

---

## Estrutura

```
backend/
├── src/main/java/br/com/alagoasmedical/backend/
│   ├── controller/    # MenuController, HealthController
│   ├── service/       # MenuService (lógica de triagem)
│   ├── dto/           # MenuMessageRequest, MenuMessageResponse, ErroResponse
│   └── exception/     # GlobalExceptionHandler
│   └── resources/     # application.properties
├── src/test/java/...  # testes unitários e de camada web
└── pom.xml
docs/                  # visão geral, requisitos, plano e diário de bordo
```

---

## Roadmap

- [ ] Integração com a **API oficial do WhatsApp** (Cloud API) — recebimento e envio de mensagens reais
- [ ] **Persistência** de conversas e estados (entidades JPA + banco relacional)
- [ ] Histórico de atendimento e métricas básicas
- [ ] Deploy

---

## Autor

Eduardo Souza de Barros — [github.com/Eduardo-Brrs](https://github.com/Eduardo-Brrs)
