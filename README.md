# MediCore Distributed System
A high-performance, cloud-native microservices ecosystem designed for Patient Management and Financial Orchestration. This project demonstrates modern backend architecture using Spring Boot 3, gRPC for low-latency internal communication, and Kafka for event-driven resilience.

# 🏗 System Architecture
The system is built on the principle of Decoupled Domains. Each service owns its own database (PostgreSQL) and communicates through strictly defined contracts.

 - Patient Service: The "Source of Truth" for patient records. Provides a gRPC server for identity verification.

 - Billing Service: An internal financial engine that processes invoices. It validates incoming data, verifies patient 
 existence via gRPC, and persists transactions.

 - Message Broker (Kafka): Orchestrates asynchronous workflows (e.g., triggering an invoice when an appointment is 
 booked).

|Category|Technology|
|Language | Java 21+|
|Framework|"Spring Boot 3.x, Spring Data JPA" |
|Communication | "gRPC (Protobuf), REST"|
|Messaging | Apache Kafka|
Database,PostgreSQL
DevOps,"Docker, Docker Compose"
Validation,Jakarta Bean Validation (Hibernate Validator)

## 🌟 Key Backend Features Implemented
1. Robust Data Flow (DTO & Mapper Pattern)
   To maintain Domain Isolation, the system uses a strict DTO/Mapper pattern. Internal JPA Entities never leak into the gRPC/REST layer.

 - Manual Mappers provide high-performance object conversion.

 - Fail-Fast Validation: Uses regex-based UUID validation in DTOs to reject malformed requests before they hit the 
 database.

2. Global Error Interception
   Implemented a custom gRPC Server Interceptor that acts as a global safety net.

 - Translates Java exceptions (like PatientNotFoundException) into standard gRPC Status Codes (NOT_FOUND, 
 INVALID_ARGUMENT).

 - Ensures consistent error responses across the entire distributed system.

3. Service-to-Service Communication
   The Billing Service acts as a gRPC client to the Patient Service, using a Blocking Stub for real-time patient verification before any financial record is created.

### 🚦 Getting Started
Prerequisites
- Docker & Docker Compose

- Java 21

- Postman (for gRPC testing)

Installation
Clone the repository:
```
git clone https://github.com/shubhamthakur-2504/medicore-distributed-system.git
```
Spin up the infrastructure:
```
docker-compose up -d
```

### 🛤 Roadmap
- [x] Create Patient Registry (CRUD)

- [x] Implement Billing Service Logic

- [x] Set up gRPC Cross-Service Verification

- [x] Global Exception Interceptors

- [ ] Integrate Kafka for Asynchronous Invoicing

- [ ] Implement API Gateway for Unified Entry