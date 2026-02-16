# Kabsu App

A Spring Boot application with PostgreSQL database, containerized using Docker.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Local Development](#local-development)
  - [Docker Deployment](#docker-deployment)
- [Configuration](#configuration)
- [Environment Variables](#environment-variables)
- [Database](#database)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** (JDK)
- **Maven 3.6+**
- **Docker** and **Docker Compose** (for containerized deployment)
- **PostgreSQL 12+** (for local development)
- **Git**

## Technology Stack

- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- 

## Getting Started

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd kabsu
   ```

2. **Set up environment variables**
   ```bash
   cp .env.example .env
   # Edit .env with your local PostgreSQL credentials
   ```

3. **Create the database**
   ```bash
   # Connect to PostgreSQL
   psql -U postgres
   
   # Create database
   CREATE DATABASE kabsu_dev_db;
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start at `http://localhost:8080`

### Docker Deployment

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd kabsu
   ```

2. **Set up environment variables**
   ```bash
   cp .env.example .env
   # The default Docker values should work out of the box
   ```

3. **Build and run with Docker Compose**
   ```bash
   # Build the JAR file first
   mvn clean package
   
   # Start the containers
   docker-compose up --build
   ```

4. **Stop the containers**
   ```bash
   docker-compose down
   ```

5. **Stop and remove volumes (clean slate)**
   ```bash
   docker-compose down -v
   ```

## Configuration

The application uses Spring profiles to manage different environments:

- **dev**: Local development profile
- **docker**: Docker container profile

### Profile Configuration Files

- `application.yml` - Base configuration
- `application-dev.yml` - Local development settings
- `application-docker.yml` - Docker container settings

## Environment Variables

Create a `.env` file in the project root with the following variables:

```env
# Application
APP_NAME=kabsu-app
APP_PROFILE_ACTIVE=dev  # or 'docker' for containerized deployment

# Docker PostgreSQL Configuration
DOCKER_POSTGRES_URL=postgres
DOCKER_POSTGRES_PORT=5432
DOCKER_POSTGRES_NAME=kabsu_db
DOCKER_POSTGRES_USERNAME=postgres
DOCKER_POSTGRES_PASSWORD=secret

# Local Development PostgreSQL Configuration
DEV_POSTGRES_URL=localhost
DEV_POSTGRES_PORT=5432
DEV_POSTGRES_NAME=kabsu_dev_db
DEV_POSTGRES_USERNAME=postgres
DEV_POSTGRES_PASSWORD=admin
```

## Database

### Schema Management

- **Local Development**: Uses `ddl-auto: create` (recreates schema on startup)
- **Docker**: Uses `ddl-auto: update` (updates schema without data loss)

### Accessing PostgreSQL

**Local Development:**
```bash
psql -U postgres -d kabsu_dev_db
```

**Docker Container:**
```bash
docker exec -it kabsu-postgres psql -U postgres -d kabsu_db
```

## API Documentation

Once the application is running, you can access:

- Application: `http://localhost:8080`
- Health Check: `http://localhost:8080/actuator/health` (if Spring Actuator is enabled)

## Building for Production

1. **Build the JAR file**
   ```bash
   mvn clean package -DskipTests
   ```

2. **The JAR file will be created at:**
   ```
   target/kabsu.jar
   ```

## Troubleshooting

### Common Issues

**Database connection refused:**
- Ensure PostgreSQL is running
- Check database credentials in `.env`
- Verify database exists

**Port 8080 already in use:**
```bash
# Find process using port 8080
lsof -i :8080  # Mac/Linux
netstat -ano | findstr :8080  # Windows

# Kill the process or change the port in application.yml
```

**Docker containers not starting:**
```bash
# Check logs
docker-compose logs app
docker-compose logs postgres

# Rebuild images
docker-compose down
docker-compose build --no-cache
docker-compose up
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Git Workflow

```bash
# Create a new branch
git checkout -b feature/your-feature-name

# Make changes and commit
git add .
git commit -m "Description of changes"

# Push to remote
git push origin feature/your-feature-name

# Switch branches
git checkout main

# Pull latest changes
git pull origin main
```

## License

[Specify your license here]

## Contact

[Your contact information or team information]

---

**Note**: Make sure to keep your `.env` file secure and never commit it to version control. Use `.env.example` as a template for other developers.
