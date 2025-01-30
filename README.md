# Housing Management System (HMS)

A comprehensive housing management system built with Spring Boot, designed to streamline property management operations.

## Features

- 🏠 Property Management
  - Property listing and details
  - Room/Unit management
  - Maintenance tracking
  - Occupancy status

- 👥 User Management
  - Role-based access control
  - Tenant profiles
  - Staff management
  - Authentication & Authorization

- 📋 Lease Management
  - Lease creation and tracking
  - Rent collection
  - Payment history
  - Document management

- 🔧 Maintenance
  - Maintenance request tracking
  - Work order management
  - Staff assignment
  - Status updates

- 💰 Financial Management
  - Payment processing
  - Rent tracking
  - Financial reporting
  - Invoice generation

- 📊 Reporting
  - Occupancy reports
  - Financial reports
  - Maintenance reports
  - Audit logs

## Tech Stack

- Java 17
- Spring Boot 3.1.5
- PostgreSQL
- Spring Security with JWT
- Spring Data JPA
- Swagger/OpenAPI
- Maven
- Lombok
- MapStruct

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- Git

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/housing-management-system.git
   cd housing-management-system
   ```

2. Create a PostgreSQL database:

   ```sql
   CREATE DATABASE hms_db;
   ```

3. Configure environment variables:

   ```bash
   cp .env.example .env
   # Edit .env with your database credentials and other configurations
   ```

4. Build the project:

   ```bash
   mvn clean install
   ```

5. Run the application:

   ```bash
   mvn spring-boot:run
   ```

6. Access the application:
   - API: <http://localhost:8080/api/v1>
   - Swagger UI: <http://localhost:8080/api/v1/swagger-ui.html>

### Environment Variables

Create a `.env` file in the project root with the following variables:

```properties
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/hms_db
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

# Mail Configuration
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

## API Documentation

The API documentation is available through Swagger UI at `/api/v1/swagger-ui.html` when the application is running.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please open an issue in the GitHub repository or contact the maintainers.
