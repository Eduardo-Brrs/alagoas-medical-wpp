# Alagoas Medical — WhatsApp Support Backend

**English** · [Português](README.md)

A REST API in **Java + Spring Boot** that automates the first triage step of Alagoas Medical's customer support on WhatsApp. The customer is guided through an interactive menu that identifies what they need and hands them over to a human agent when necessary, cutting the time the team spends on repetitive questions.

> 🚧 **Status:** in development, for internal use at Alagoas Medical. The triage core (menu, validation, error handling and tests) is already implemented. Integration with the official WhatsApp API is the next step on the roadmap. The project docs in `docs/` are in Portuguese.

---

## Tech stack

- **Java 21**
- **Spring Boot** — Spring Web (REST) and Bean Validation
- **Maven** (wrapper included)
- **Spring Data JPA + H2** — set up for the persistence stage (see roadmap)
- **JUnit 5 + Mockito + MockMvc** — automated tests

---

## Implemented features

- REST endpoint that receives the customer's message and returns the menu's reply
- Triage menu with 4 options + a fallback for invalid input
- Flow states (`nextAction`) to orchestrate the conversation (wait for an option, wait for an agent, transfer to a human)
- Input validation (required fields) with Bean Validation
- Centralized error handling with a standard response (`@RestControllerAdvice`)
- Covered by unit tests and web-layer tests

---

## Running

Requirement: **Java 21**.

```bash
cd backend
./mvnw spring-boot:run
```

The app starts at `http://localhost:8080`.
H2 console (dev environment): `http://localhost:8080/h2-console`

---

## Endpoints

### `GET /health`

Application health check.

```
200 OK
Backend Alagoas Medical online
```

### `POST /menu/message`

Processes the customer's message and returns the menu's reply.

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

**Menu options** (the replies themselves are in Portuguese, since the customers are Brazilian)

| `message`      | Option                              | `nextAction`        |
| -------------- | ----------------------------------- | ------------------- |
| `1`            | Wound dressings and coverings       | `AGUARDAR_ATENDENTE`|
| `2`            | Negative pressure wound therapy     | `AGUARDAR_ATENDENTE`|
| `3`            | Hospital products                   | `AGUARDAR_ATENDENTE`|
| `4`            | Talk to an agent                    | `TRANSFERIR_HUMANO` |
| anything else  | Shows the main menu again           | `AGUARDAR_OPCAO`    |

**Validation errors — `400 Bad Request`**

When `from` or `message` are empty, or the body is invalid JSON:

```json
{
  "status": 400,
  "erro": "Requisição inválida",
  "detalhes": ["O campo 'from' é obrigatório"]
}
```

---

## Tests

```bash
cd backend
./mvnw test
```

- **`MenuServiceTest`** — unit tests for the triage logic: every menu option, the fallback, a whitespace-only message and keeping the sender's number.
- **`MenuControllerTest`** — web-layer tests (`@WebMvcTest` + MockMvc): `200` and `400` responses, invalid JSON and a missing body.

---

## Structure

```
backend/
├── src/main/java/br/com/alagoasmedical/backend/
│   ├── controller/    # MenuController, HealthController
│   ├── service/       # MenuService (triage logic)
│   ├── dto/           # MenuMessageRequest, MenuMessageResponse, ErroResponse
│   └── exception/     # GlobalExceptionHandler
│   └── resources/     # application.properties
├── src/test/java/...  # unit and web-layer tests
└── pom.xml
docs/                  # overview, requirements, plan and dev log (in Portuguese)
```

---

## Roadmap

- [ ] Integration with the **official WhatsApp API** (Cloud API) — receiving and sending real messages
- [ ] **Persistence** of conversations and states (JPA entities + relational database)
- [ ] Support history and basic metrics
- [ ] Deploy

---

## Author

Eduardo Souza de Barros — [github.com/Eduardo-Brrs](https://github.com/Eduardo-Brrs)
