# Courier Tracking System

## Overview

This project is a RESTful web application developed in Java Spring Boot that processes streaming geolocations of couriers. It logs when a courier enters a 100-meter radius of any store. The system uses Apache Kafka for real-time data streaming and Docker for infrastructure management.

## Features

- Processes real-time geolocation data of couriers using Kafka.
- Logs courier entries when within 100 meters of specified stores.
- Prevents re-entries to the same store within a 1-minute interval.

## Technologies Used

- Java
- Spring Boot
- Apache Kafka
- Docker
- GeoCalc Library (Distance calculations)

## Project Architecture

The application consists of multiple components working together:

1. **Courier Service**: Handles geolocation data and distance calculations for couriers.
2. **Proximity Service**: Monitors courier proximity to stores and logs events using Kafka.
3. **Kafka Infrastructure**:
   - Topics: `courier-location-topic` for communicating between microservices, and `courier-location-log` for logging courier events.
   - Dockerized setup using `docker-compose` for Kafka, Zookeeper, and Kafka UI.

## Setup Instructions

1. **Clone the Repository:**
   Clone the repository and navigate to the project directory:
   ```bash
   git clone https://github.com/Huzuntu/Courier-Tracking.git
   cd Courier-Tracking
2. **Docker Setup for Kafka and Zookeeper:**
   Make sure Docker is installed and running on your machine. From the infrastructure folder, bring up Kafka and Zookeeper services:
   ```bash
   cd infrastructure
   docker-compose up -d
3. **Run the Spring Boot Application:**
   Ensure that you have Java 17+ and Maven installed. From the root directory of the project, build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
4. **Stop the Services:**
   Once you’re done, you can stop the Kafka and Zookeeper services:
   ```bash
   docker-compose down
## Access Kafka UI

1.	Once the Docker containers are running, you can access the Kafka UI at http://localhost:8080.
2.	You can use the Kafka UI to monitor Kafka topics and see logs of courier events being published to the courier-location-log topic.

## Future Enhancements
1.	Unit Testing: I will implement unit tests using JUnit and Mockito to ensure the reliability of the services.
2.	API Security: I plan to add security layers such as OAuth2 or JWT authentication for protected endpoints.
