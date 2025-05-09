# Sports Betting Settlement System

This backend application simulates sports betting event outcome handling and bet settlement via Kafka and RocketMQ.

## Features

- API endpoint to publish sports event outcomes to Kafka
- Kafka consumer that listens to event-outcomes topic
- Matching event outcomes to bets that need to be settled using H2 in-memory database
- Mocked RocketMQ producer that sends messages to bet-settlements

## Technology Stack

- Java 21
- Spring Boot 3.2
- Spring Data JPA
- Apache Kafka
- ~~Apache RocketMQ~~(Using mocked producer)
- H2 Database
- Flyway Migration
- Lombok
- MapStruct
- Docker & Docker Compose

## Project Architecture

### 3-Tier Domain Model

The application uses a 3-tier domain model architecture to separate concerns:

**Entity Layer:** JPA entities for database persistence

- BetEntity
- EventOutcomeEntity

**Model Layer:** Domain models for business logic

- Bet
- EventOutcome
- BetSettlement

**DTO Layer:** Data Transfer Objects for API communication

- BetDto (Java Record)
- EventOutcomeDto (Java Record)
- BetSettlementDto (Java Record)

**Mapper Layer:** MapStruct-based mappers for converting between layers

- BetMapper
- EventOutcomeMapper
- BetSettlementMapper

### Other Components

- API Layer: REST controllers for incoming requests
- Service Layer: Business logic components
- Repository Layer: Data access interfaces for the in-memory database
- Kafka Components: Producers and consumers for messaging
- RocketMQ Component: Producer for bet settlement messaging

## Prerequisites

- Docker and Docker Compose

## How to Run

1. Clone this repository:
   ```bash
   git clone git@github.com:un-poco-loco/sporty-assessment.git
   cd sporty-assessment
   ```

2. Enable runner script:
   ```bash
   chmod +x run.sh
   ```

3. Start the entire environment:
   ```bash
   ./run.sh start
   
4. To stop the entire environment:
   ```bash 
   ./dev.sh stop
   ```
5. To restart the entire environment:
   ```bash
   ./run.sh restart
   ```

8. Accessing the services:

* Betting Service API: http://localhost:8080
* H2 Database Console: http://localhost:8080/h2-console
* Swagger UI: http://localhost:8080/swagger-ui.html

## API Documentation

The application provides an OpenAPI (Swagger) interface for API documentation and testing:

### Accessing the API Documentation

* OpenAPI UI: http://localhost:8080/swagger-ui.html
* OpenAPI Docs: http://localhost:8080/api-docs

### You can use the Swagger UI to:

* Explore available API endpoints
* Test API functionality directly from the browser
* View request/response models and schemas
* Download OpenAPI specifications for client generation

## API Usage Examples
### Publish Event Outcome

To publish a sports event outcome:
   ```bash 
      curl -X POST http://localhost:8080/api/events/outcome \
      -H "Content-Type: application/json" \
      -d '{
      "eventId": "event1",
      "eventName": "Football Match A vs B",
      "eventWinnerId": "team1"
     }'
  ```


## Database Access
### H2 Database Console
The H2 in-memory database console is accessible at:

* **URL:** http://localhost:8080/h2-console
* **JDBC URL:** jdbc:h2:mem
* **Username:** sa
* **Password:** password
* 
Remote connections to the H2 console are enabled.

## System Flow

1. A POST request is made to /api/events/outcome with event outcome details (DTO)
2. The DTO is converted to a domain model using MapStruct
3. The domain model is converted to an entity and saved to the H2 database
4. The domain model is published to the Kafka event-outcomes topic
5. The Kafka consumer picks up the message and processes it
6. The system queries the H2 database for bets that match the event ID
7. For each matching bet entity, it's mapped to a domain model
8. Each bet is processed and a BetSettlement domain model is created
9. Each BetSettlement is sent to RocketMQ (mocked)
10. The bet settlement details are logged

## Testing
The application includes both unit and integration tests:
*  Unit Tests: Test individual components in isolation, including mapper tests
*  Integration Tests: Test the entire flow with embedded Kafka

Run the tests with:
   ```bash
      mvn test
   ```

## Sample Data

The application is pre-loaded with sample bet data via Flyway migrations:

* Bet 1: User 1 bet on Team 1 for Event 1
* Bet 2: User 2 bet on Team 2 for Event 1
* Bet 3: User 3 bet on Team 3 for Event 2

Try publishing an event outcome for Event 1 with different winners to see how the system processes winning and losing bets.

## Docker Image Information

The application uses the following Docker images:
* Eclipse Temurin JDK 21 for building
* Eclipse Temurin JRE 21 for running (Alpine-based for smaller image size)
* Apache Kafka
* Apache ZooKeeper

## Configuration Options
The application can be configured using environment variables or by modifying the application.yml file:
* Kafka connection settings
* Database configuration

See the application.yml file for all available configuration options.

## Troubleshooting
### Common Issues

1. **H2 Console Access Issues:** If you encounter "Remote connections not allowed" error when accessing the H2 console, ensure that spring.h2.console.settings.web-allow-others is set to true in application.yml.
2. **Kafka Connection Issues:** If the application can't connect to Kafka, verify that the Kafka container is running and that the bootstrap servers configuration is correct.

## Getting Help

If you encounter any issues not covered in this documentation, please:

1. Check the application logs:
   ```bash
   docker-compose logs -f betting-service
   ```
2. Verify all containers are running:
   ```bash
   docker-compose ps
   ```
   
