#!/bin/bash
echo "Waiting for Kafka to be ready..."
MAX_KAFKA_RETRIES=30
for i in $(seq 1 $MAX_KAFKA_RETRIES); do
  if nc -z kafka 9092; then
    echo "Kafka is available!"
    break
  fi
  echo "Kafka not available yet - sleeping (attempt $i/$MAX_KAFKA_RETRIES)"
  sleep 2
  if [ $i -eq $MAX_KAFKA_RETRIES ]; then
    echo "WARNING: Kafka connection check failed, but proceeding anyway..."
  fi
done

echo "Starting application..."
exec java -jar /app/app.jar
