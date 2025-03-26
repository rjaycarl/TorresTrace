# TorresTrace

**Torrestrace** is a lightweight Spring Boot Utility designed to **log**, **capture**, and **replay** REST API requests.
Ideal for debugging, testing, and simulating API requests without needing complex tools.

## Features

- Request/Response Logging
- Replay REST calls manually or in batch
- Pluggable storage (JPA, file-based coming soon)
- Minimal setup, easily integratable in any Spring Boot app

---

**Author**: R-Jay Carl Torres
Github: [https://github.com/rjaycarl/](https://github.com/rjaycarl/)

## Concept Overview:

**TorresTrace** is a **lightweight Java 21 Spring Boot microservice utility** designed to **log, inspect, and replay
HTTP requests**.
It acts as a **trace recorder** and **replayer** for debugging, testing, and validating external system integrations,
API behavior, or microservices communication - without the need to rest through the original flow.

It works like a **mock recorder + playback tool**, but instead of stubbing responses, it **records real
requests/responses** and let you **replay them on demand.**

---

## Goal / What Problem Are We Solving?

> "Easily trace, inspect, and replay any HTTP requests across your services to debug faster, test smarter, and improve
> reliability."

### Problems It Solves:

1. **Hard to Reproduce Bugs**: You often can't easily reproduce production bugs because you can't trigger the exact same
   request again.
2. **Integration Testing without External Dependencies**: Replay captured requests without needing the original client
   systems.
3. **Contract Testing and Validation**: Replay past requests against updated services to ensure backward compatibility.
4. **API Debugging and Development**: Instantly replay edge cases or malformed requests to fine-tune your validation and
   error handling.
5. **Microservices Communication Insight**: Trace and replay requests between services in a distributed system.

---

## High-Level Features:

| Feature                | Description                                                                              |
|------------------------|------------------------------------------------------------------------------------------|
| Request Logging        | Log HTTP requests (headers, body, method, URL) and optionally responses.                 |
| Replay Requests        | Replay any logged requets on demand                                                      |
| Customizable Endpoints | Easily expose REST endpoints for replay, listing, and deletion of request logs.          |
| OpenAPI Documentation  | Automatically document the endpoints with OpenAPI v3 for UI exploration and integration. |
| Contract Testing Ready | Compatible with Pact for contract testing between services.                              |
| Modular Design         | Microservice-ready with separate modules for core, persistence, replay logic, etc.       |

---

## Primary Use Cases

1. Replay previously failed or successful request during debugging.
2. Validate backward compatibility by replaying old requests on updated APIs.
3. Contract testing with real request/response pairs.
4. Debug inter-service HTTP communication in microservice architectures.

---

## Why Is TorresTrace Useful?

* You **don't need complex tools like Postman Collections or curl scripts** for replaying requests.
* Captures the **entire request**, including headers, payload, and query params, which **reduces debugging guesswork**.
* Can **replay requests as they were**, including **timing and ordering** (optional).
* Provides **visibility** into requests, turning them into **contract test data** for future use.

---

## Example Workflow

1. **Log HTTP Requests**
    * Your service logs a request into TorresTrace Core (via REST call or interceptor).
2. **View/Inspect Requests**
    * Use TorresTrace REST API (`/replay` endpoint) to list and inspect logged requests.
3. **Replay Requests**
    * Select a request (by ID) and POST to `/replay/{id}`.
    * TorresTrace replays the request to the original target or configurable endpoint.
4. **Check Results**
    * TorresTrace returns the HTTP response from the replayed requests.
    * Useful for comparison, validation, and regression testing.

---

## Project Modules (Micro-managed)

| Module Name          | Purpose                                                                                 |
|----------------------|-----------------------------------------------------------------------------------------|
| torrestrace-core     | The core API and interfaces for logging and replaying requests.                         |
| torrestrace-storage  | Manages persistence of logs (in-memory for now, future DB/file support).                |
| torrestrace-rest     | Exposes HTTP APIs for listing, replaying, and deleting logged requests.                 |
| torrestrace-contract | Pact-based contract testing utilities (provider + consumer).                            |
| torrestrace-app      | Spring Boot main application module that wires together core, storage, and rest modules |

---

## Future Enhancements

1. **Replay Scheduling**: Replay requests on a schedule or at intervals (like load tests).
2. **GraphQL / gRPC Support**: Support for capturing and replaying GraphQL or gRPC requests.
3. **Persistence Backends**: Support for **PostgreSQL, MongoDB, or Flat Files** for storing request logs.
4. **Authentication & Authorization**: Add JWT security for accessing replay APIs.
5. **Web UI (Optional)**: Simple dashboard to view, serach, and replay logs without Postman/Swagger.

---

**Trace, Replay, Verify. Simplified.**
