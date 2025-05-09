#!/bin/bash
# run.sh - Complete script to manage the application with cross-platform compatibility

# Color codes for better readability
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Log file setup
LOG_DIR="logs"
LOG_FILE="$LOG_DIR/run_$(date +%Y%m%d_%H%M%S).log"

# Create logs directory if it doesn't exist
mkdir -p $LOG_DIR

# Function to log messages to both console and log file
log() {
    local level=$1
    local message=$2
    local color=$NC

    case $level in
        INFO)  color=$GREEN ;;
        WARN)  color=$YELLOW ;;
        ERROR) color=$RED ;;
    esac

    # Log to console with color
    echo -e "${color}[$level] $message${NC}"

    # Log to file without color codes
    echo "[$(date +"%Y-%m-%d %H:%M:%S")] [$level] $message" >> $LOG_FILE
}

# Function to display usage
usage() {
    log "INFO" "Usage: $0 {start|stop|restart|build|logs [service]|rebuild}"
    log "INFO" "  start:    Start all services with build caching"
    log "INFO" "  stop:     Stop all services"
    log "INFO" "  restart:  Restart all services with build caching"
    log "INFO" "  build:    Build the application using Docker cache"
    log "INFO" "  rebuild:  Force rebuild the application (no cache)"
    log "INFO" "  logs:     View logs, optionally for a specific service"
}

# Function to create wait-for-services.sh script for betting-service
create_wait_for_services_script() {
    log "INFO" "Creating wait-for-services.sh script for betting-service..."
    cat > wait-for-services.sh << 'EOL'
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
EOL
    chmod +x wait-for-services.sh
    log "INFO" "wait-for-services.sh script created and made executable"
}

# Function to create Kafka topics
create_kafka_topics() {
    log "INFO" "Creating Kafka topics..."
    for i in {1..10}; do
        log "INFO" "Attempt $i: Creating Kafka topics..."
        docker-compose exec -T kafka \
            kafka-topics.sh --create --if-not-exists \
            --bootstrap-server kafka:9092 \
            --partitions 1 \
            --replication-factor 1 \
            --topic event-outcomes && { log "INFO" "Event-outcomes topic created successfully"; break; }
        sleep 2
    done

    for i in {1..10}; do
        log "INFO" "Attempt $i: Creating bet-settlements Kafka topic..."
        docker-compose exec -T kafka \
            kafka-topics.sh --create --if-not-exists \
            --bootstrap-server kafka:9092 \
            --partitions 1 \
            --replication-factor 1 \
            --topic bet-settlements && { log "INFO" "Bet-settlements Kafka topic created successfully"; break; }
        sleep 2
    done
}

# Function to build the application with cache
build() {
    log "INFO" "Building the application with cache..."
    docker-compose build betting-service 2>&1 | tee -a $LOG_FILE
    if [ ${PIPESTATUS[0]} -eq 0 ]; then
        log "INFO" "Build completed successfully."
    else
        log "ERROR" "Build failed. Check logs for details."
        exit 1
    fi
}

# Function to force rebuild the application
rebuild() {
    log "INFO" "Force rebuilding the application (no cache)..."
    docker-compose build --no-cache betting-service 2>&1 | tee -a $LOG_FILE
    if [ ${PIPESTATUS[0]} -eq 0 ]; then
        log "INFO" "Rebuild completed successfully."
    else
        log "ERROR" "Rebuild failed. Check logs for details."
        exit 1
    fi
}

# Function to start the application
start() {
    log "INFO" "Starting application..."

    # Clean up any running instances but keep the volumes
    log "INFO" "Stopping running services..."
    docker-compose down 2>&1 | tee -a $LOG_FILE

    # Create wait-for-services.sh script for betting-service
    create_wait_for_services_script

    # Always build to ensure latest code
    log "INFO" "Building latest code..."
    build

    # Start Zookeeper and wait longer for it to initialize
    log "INFO" "Starting Zookeeper..."
    docker-compose up -d zookeeper 2>&1 | tee -a $LOG_FILE
    sleep 15  # Increase from 5 to 15

    # Start Kafka and wait longer for it to initialize
    log "INFO" "Starting Kafka..."
    docker-compose up -d kafka 2>&1 | tee -a $LOG_FILE
    sleep 30  # Increase from 10 to 30

    # Create Kafka topics
    create_kafka_topics

    log "INFO" "Starting Betting Service..."
    docker-compose up -d betting-service 2>&1 | tee -a $LOG_FILE

    log "INFO" "All services started. You can access:"
    log "INFO" "  - Betting Service API: http://localhost:8080"
    log "INFO" "  - Swagger UI: http://localhost:8080/swagger-ui.html"
    log "INFO" "  - H2 Console: http://localhost:8080/h2-console"

    log "INFO" "To see logs: $0 logs"
    log "INFO" "Complete startup log saved to: $LOG_FILE"
}

# Function to stop the application
stop() {
    log "INFO" "Stopping application..."
    docker-compose down -v 2>&1 | tee -a $LOG_FILE
    log "INFO" "Application stopped. All data has been cleaned up."
}

# Function to restart the application
restart() {
    log "INFO" "Restarting application..."

    # Stop containers but preserve data volumes for faster startup
    docker-compose down 2>&1 | tee -a $LOG_FILE

    sleep 5

    # Start again with build
    start
}

# Function to view logs
logs() {
    if [ -z "$2" ]; then
        log "INFO" "Showing logs for all services..."
        docker-compose logs -f 2>&1 | tee -a $LOG_FILE
    else
        log "INFO" "Showing logs for $2 service..."
        docker-compose logs -f "$2" 2>&1 | tee -a $LOG_FILE
    fi
}

# Log startup information
log "INFO" "=== Run Script Started ==="
log "INFO" "Date: $(date)"
log "INFO" "User: $(whoami)"
log "INFO" "Directory: $(pwd)"
log "INFO" "Command: $0 $*"

# Detect platform but don't apply specific settings
PLATFORM=$(uname -m)
case "$PLATFORM" in
    arm64|aarch64)
        log "INFO" "Detected ARM64 platform (Apple Silicon)"
        ;;
    x86_64|amd64)
        log "INFO" "Detected x86_64 platform"
        ;;
    *)
        log "INFO" "Detected platform: $PLATFORM"
        ;;
esac

# Main execution
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    build)
        build
        ;;
    rebuild)
        rebuild
        ;;
    logs)
        logs "$@"
        ;;
    *)
        usage
        exit 1
        ;;
esac

log "INFO" "=== Run Script Completed ==="
exit 0