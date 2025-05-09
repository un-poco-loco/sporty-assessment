# Dockerfile
FROM maven:3.9.6-eclipse-temurin-21 as build

WORKDIR /app

# Install network utilities for health checks
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

# Copy pom.xml separately to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy project source
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create wait-for script
RUN echo '#!/bin/bash \n\
echo "Waiting for Kafka to be ready..." \n\
while ! nc -z kafka 9092; do \n\
  echo "Kafka not available yet - sleeping" \n\
  sleep 2 \n\
done \n\
echo "Services are ready! Starting application..." \n\
exec java -jar /app/app.jar' > /wait-for-services.sh && chmod +x /wait-for-services.sh

FROM eclipse-temurin:21-jre

WORKDIR /app

# Install netcat for health checks
RUN apt-get update && apt-get install -y netcat-openbsd iproute2 && rm -rf /var/lib/apt/lists/*

# Copy the built artifact and wait script from the build stage
COPY --from=build /app/target/*.jar /app/app.jar
COPY --from=build /wait-for-services.sh /wait-for-services.sh

# Expose ports
EXPOSE 8080

# Run with wait-for script to ensure services are available
ENTRYPOINT ["/wait-for-services.sh"]