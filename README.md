🏠 Housing Management System (HMS)
=================================

> A modern, scalable housing management solution built with Spring Boot, designed to revolutionize property management operations.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12%2B-blue)
![License](https://img.shields.io/badge/License-MIT-purple)

## 🚀 Features

### Core Functionality

- **Property Management** 🏢
  - Smart property listing and details
  - Dynamic room/unit management
  - Real-time occupancy tracking
  - Comprehensive property insights

- **User Management** 👥
  - Secure role-based access control
  - Detailed tenant profiles
  - Efficient staff management
  - JWT-based authentication

- **Lease Management** 📋
  - Automated lease generation
  - Smart rent collection
  - Digital document management
  - Payment tracking

### Advanced Features

- **Maintenance Hub** 🔧
  - Real-time request tracking
  - Smart work order management
  - Automated staff assignment
  - Progress monitoring

- **Financial Suite** 💰
  - Secure payment processing
  - Automated rent tracking
  - Comprehensive financial reporting
  - Digital invoice generation

- **Analytics Dashboard** 📊
  - Real-time occupancy insights
  - Financial analytics
  - Maintenance metrics
  - Detailed audit trails

## 🛠️ Tech Stack

### Backend

- Java 17
- Spring Boot 3.1.5
- PostgreSQL
- Spring Security with JWT
- Spring Data JPA
- Swagger/OpenAPI

### Development Tools

- Maven
- Lombok
- MapStruct

### Frontend (Coming Soon! 🎨)
>
> Stay tuned for our modern, responsive frontend implementation!

## ⚡ Quick Start

### Prerequisites

Make sure you have these installed:

- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- Git

### Setup in 5 Minutes ⚡

1. **Clone & Navigate**

   ```bash
   git clone git@github.com:zakcination/hms_senior.git
   cd hms_senior
   ```

2. **Database Setup**

   ```sql
   CREATE DATABASE hms_db;
   ```

3. **Environment Configuration**

   ```bash
   cp .env.example .env
   # Update .env with your credentials
   ```

4. **Build & Run**

   ```bash
   # Build the project
   mvn clean install

   # Start the application
   mvn spring-boot:run
   ```

5. **Access the System**
   - API Endpoint: <http://localhost:8080/api/v1>
   - API Documentation: <http://localhost:8080/api/v1/swagger-ui.html>

### 🔐 Environment Setup

Create a `.env` file with these configurations:

```properties
# Database
DB_URL=jdbc:postgresql://localhost:5432/hms_db
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Security
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

# Email
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

## 📚 Documentation

Explore our comprehensive API documentation through Swagger UI at `/api/v1/swagger-ui.html` when the application is running.

## 🤝 Contributing

We welcome contributions! Here's how:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add: Amazing Feature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 💪 Support

Need help? We've got you covered:

- 📫 Open an issue in the GitHub repository
- 🤝 Contact the maintainers
- 📚 Check out our documentation

---

<div align="center">
  Made with ❤️ by the HMS Team
</div>
