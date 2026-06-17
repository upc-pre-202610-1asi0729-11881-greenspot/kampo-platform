# Inventory Bounded Context

Bounded context responsable de la gestión de inventario, proveedores y órdenes de entrada de la plataforma **Acme Kampo**. Implementado con **Domain-Driven Design (DDD)** y arquitectura hexagonal sobre Spring Boot 3 / Java 26.

---

## Tabla de contenidos

- [Arquitectura](#arquitectura)
- [Estructura de paquetes](#estructura-de-paquetes)
- [Endpoints REST](#endpoints-rest)
    - [Inventory](#inventory)
    - [Suppliers](#suppliers)
    - [Order Inputs](#order-inputs)
- [Documentación interactiva (Swagger UI)](#documentación-interactiva-swagger-ui)
- [Tecnologías](#tecnologías)
- [Fases de implementación](#fases-de-implementación)

---

## Arquitectura

El bounded context sigue la arquitectura hexagonal (ports & adapters) organizada en cuatro capas:

```
interfaces        →  REST controllers, ACL facade, integration events
application       →  Command services, query services, event handlers
domain            →  Aggregates, value objects, domain events, repository interfaces
infrastructure    →  JPA persistence entities, Spring Data repos, adapters
```

La regla de dependencias es estricta: cada capa solo depende de la inmediatamente interior. El dominio no conoce a nadie.

---

## Estructura de paquetes

```
com.acme.kampo.platform
├── inventory
│   ├── interfaces
│   │   ├── rest
│   │   │   ├── resources         # Inbound/outbound HTTP resources
│   │   │   ├── transform          # Resource ↔ command assemblers
│   │   │   ├── InventoryController.java
│   │   │   ├── SupplierController.java
│   │   │   └── OrderInputController.java
│   │   ├── acl                    # InventoryContextFacade (interface)
│   │   └── events                 # Integration events
│   ├── application
│   │   ├── commandservices        # Command service interfaces
│   │   ├── queryservices          # Query service interfaces
│   │   ├── acl                    # InventoriesContextFacadeImpl
│   │   └── internal
│   │       ├── commandservices    # Command service implementations
│   │       ├── queryservices      # Query service implementations
│   │       └── eventhandlers      # Domain → integration event translators
│   ├── domain
│   │   └── model
│   │       ├── aggregates         # Inventory, Supplier, OrderInput
│   │       ├── commands           # CreateInventoryCommand, AddSupplierCommand, …
│   │       ├── queries            # GetInventoryByIdQuery, GetAllInventoryQuery, …
│   │       ├── events             # InventoryCreatedEvent, SupplierCreatedEvent, …
│   │       ├── valueobjects       # InventoryId, SupplierId, OrderId
│   │       └── enums              # InventoryStatus, OrderStatus
│   │   └── repositories           # Domain repository interfaces
│   └── infrastructure
│       └── persistence.jpa
│           ├── entities           # JPA persistence entities
│           ├── repositories       # Spring Data JPA interfaces
│           └── adapters           # Repository adapter implementations
└── shared
    └── domain.model.aggregates    # AbstractDomainAggregateRoot
```

---

## Endpoints REST

Base URL: `http://localhost:8080/api/v1`

### Inventory

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| `POST` | `/inventory` | Registra un nuevo ítem de inventario | `201 Created` |
| `GET` | `/inventory/{inventoryId}` | Obtiene un ítem por ID | `200 OK` / `404 Not Found` |
| `GET` | `/inventory` | Lista todos los ítems de inventario | `200 OK` |

#### POST `/api/v1/inventory`

**Request body:**
```json
{
  "name": "Fertilizante NPK",
  "quantity": 100,
  "unit": "kg",
  "minStock": 20
}
```

**Response `201`:**
```json
{
  "id": 1,
  "name": "Fertilizante NPK",
  "quantity": 100,
  "unit": "kg",
  "minStock": 20,
  "status": "AVAILABLE"
}
```

**Estados posibles de `status`:** `AVAILABLE` · `LOW_STOCK` · `OUT_OF_STOCK`

---

### Suppliers

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| `POST` | `/suppliers` | Registra un nuevo proveedor | `201 Created` |
| `GET` | `/suppliers/{supplierId}` | Obtiene un proveedor por ID | `200 OK` / `404 Not Found` |
| `GET` | `/suppliers` | Lista todos los proveedores | `200 OK` |

#### POST `/api/v1/suppliers`

**Request body:**
```json
{
  "name": "AgroSup S.A.",
  "contact": "+51 999 888 777",
  "email": "contacto@agrosup.pe"
}
```

**Response `201`:**
```json
{
  "id": 1,
  "name": "AgroSup S.A.",
  "contact": "+51 999 888 777",
  "email": "contacto@agrosup.pe"
}
```

---

### Order Inputs

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| `POST` | `/order-inputs` | Crea una nueva orden de entrada | `201 Created` |
| `PATCH` | `/order-inputs/{orderId}/receive` | Marca una orden como recibida y actualiza el stock | `200 OK` |
| `GET` | `/order-inputs/{orderId}` | Obtiene una orden por ID | `200 OK` / `404 Not Found` |
| `GET` | `/order-inputs` | Lista todas las órdenes | `200 OK` |

#### POST `/api/v1/order-inputs`

**Request body:**
```json
{
  "inventoryId": 1,
  "supplierId": 1,
  "quantity": 50
}
```

**Response `201`:**
```json
{
  "id": 1,
  "inventoryId": 1,
  "supplierId": 1,
  "quantity": 50,
  "status": "PENDING",
  "orderedAt": "2025-06-04T10:30:00",
  "receivedAt": null
}
```

#### PATCH `/api/v1/order-inputs/{orderId}/receive`

No requiere body. Transiciona la orden de `PENDING` → `RECEIVED` y suma el `quantity` de la orden al stock del inventario relacionado. Operación atómica — si falla alguna parte hace rollback completo.

**Response `200`:**
```json
{
  "id": 1,
  "inventoryId": 1,
  "supplierId": 1,
  "quantity": 50,
  "status": "RECEIVED",
  "orderedAt": "2025-06-04T10:30:00",
  "receivedAt": "2025-06-04T11:00:00"
}
```

**Estados posibles de `status`:** `PENDING` · `RECEIVED` · `CANCELLED`

> Una orden en estado `RECEIVED` o `CANCELLED` es terminal — no admite más transiciones.

---

## Documentación interactiva (Swagger UI)

Con la aplicación corriendo, la documentación interactiva está disponible en:

| Recurso | URL |
|---------|-----|
| **Swagger UI** | http://localhost:8080/swagger-ui/index.html |
| **OpenAPI JSON** | http://localhost:8080/v3/api-docs |
| **OpenAPI YAML** | http://localhost:8080/v3/api-docs.yaml |

Swagger UI permite explorar y probar todos los endpoints directamente desde el navegador, sin necesidad de Postman u otro cliente HTTP.

---

## Tecnologías

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 26 | Lenguaje principal |
| Spring Boot | 3.x | Framework de aplicación |
| Spring Data JPA | 3.x | Persistencia |
| Hibernate | 6.x | ORM |
| Springdoc OpenAPI | 2.8.9 | Documentación y Swagger UI |
| Lombok | latest | `@Slf4j` y reducción de boilerplate |
| JSpecify | latest | Null-safety estática (`@NullMarked`) |
| JUnit 5 + AssertJ | latest | Tests unitarios |
| Mockito | latest | Mocks en tests de application layer |
| H2 | latest | Base de datos en memoria para tests (`@DataJpaTest`) |

### Dependencias clave en `pom.xml`

```xml
<!-- OpenAPI 3 / Swagger UI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.9</version>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- JSpecify -->
<dependency>
    <groupId>org.jspecify</groupId>
    <artifactId>jspecify</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Fases de implementación

El bounded context se construyó en 6 fases siguiendo el orden de dependencias del DDD:

| Fase | Capa | Contenido |
|------|------|-----------|
| 1 | Domain | Value objects (`InventoryId`, `SupplierId`, `OrderId`), enums (`InventoryStatus`, `OrderStatus`), `AbstractDomainAggregateRoot` |
| 2 | Domain | Aggregates (`Inventory`, `Supplier`, `OrderInput`), commands, domain events |
| 3 | Domain | Interfaces de repositorio (`InventoryRepository`, `SupplierRepository`, `OrderInputRepository`) |
| 4 | Application | Command services, query services, event handlers, integration events, query objects |
| 5 | Infrastructure | JPA persistence entities, Spring Data repositories, adapters (implementan las interfaces de dominio) |
| 6 | Interfaces | REST controllers, resources, assemblers, ACL facade (`InventoryContextFacade`) |