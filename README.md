# Kafka Service

## Project Overview
This project provides a simple Kafka-based messaging service with Spring Boot applications for both the Kafka producer and Kafka consumer. The service allows for the production and consumption of messages using Kafka, facilitating decoupled communication between distributed systems.

## Key Features
- **Kafka Producer**: Sends messages to a Kafka topic.
- **Kafka Consumer**: Listens to a Kafka topic and processes incoming messages.
- **Spring Boot Integration**: Both producer and consumer are implemented as Spring Boot applications.
- **Dockerized Environment**: The project uses Docker to run the Kafka broker and related services.

## Project Structure
### Kafka Consumer
- **ConsumerApp.java**: Main entry point of the Kafka consumer Spring Boot application.
  - Listens for messages from Kafka topics and processes them.
- **consumer**: Contains the logic for consuming messages from Kafka.
- **service**: Contains the business logic for processing consumed messages.
- **model**: Data models used by the consumer.

### Kafka Producer
- **ProducerApp.java**: Main entry point of the Kafka producer Spring Boot application.
  - Sends messages to Kafka topics.
- **producer**: Contains the logic for producing messages to Kafka.
- **service**: Contains the business logic for creating and sending messages to Kafka topics.
- **model**: Data models used by the producer.

### Configuration
- **docker-compose.yml**: Docker configuration file to set up Kafka and Zookeeper.
- **pom.xml**: Maven configuration for managing dependencies and building the project.

## Testing the Service
- The Kafka producer will send messages to a Kafka topic.
- The Kafka consumer will receive and process these messages in real-time.

## Docker Configuration
The `docker-compose.yml` file sets up the following services:
- **Kafka**: The Kafka broker that handles message production and consumption.
- **Zookeeper**: Kafka relies on Zookeeper for managing the Kafka cluster.
- **Kafka Producer**: The Spring Boot service that sends messages to Kafka.
- **Kafka Consumer**: The Spring Boot service that listens for messages from Kafka.

## Advantages
- **Decoupled Communication**: Kafka allows services to communicate asynchronously, decoupling them for better scalability and fault tolerance.
- **Scalability**: Kafka can handle large volumes of messages, allowing the system to scale horizontally.
- **Message Durability**: Kafka ensures message durability and reliability even in case of failures.
- **Real-time Processing**: The consumer processes messages in real-time, making it suitable for event-driven architectures.
## Sample message to Kafdrop
  ![image](https://github.com/user-attachments/assets/1a96b18d-1d42-43bc-b672-23c18d0d86ab)
