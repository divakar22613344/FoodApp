<div align="center">
  <!-- Title -->
  <h1>🍔 MealAPI </h1>
  <h3>Full-Stack Food Ordering Platform</h3>
  
  <!-- Banner -->
  <img src="https://food-app-dev-divakar.s3.eu-north-1.amazonaws.com/11zon_cropped.png" 
       alt="FoodApp - Food Ordering Platform" 
       width="30%" 
       style="max-width: 1200px; border-radius: 12px; margin: 25px 0; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
  
  <!-- Badges -->
  <p>
    <a href="https://mealapi.in"><img src="https://img.shields.io/badge/Live%20Demo-Visit%20Now-brightgreen?style=for-the-badge" alt="Live Demo"></a>&nbsp;
    <a href="https://www.oracle.com/java/"><img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" alt="Java 21"></a>&nbsp;
    <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-4.0.1-green?style=for-the-badge&logo=spring-boot" alt="Spring Boot"></a>&nbsp;
    <a href="https://react.dev/"><img src="https://img.shields.io/badge/React-19.2-blue?style=for-the-badge&logo=react" alt="React"></a>&nbsp;
    <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/MySQL-8.0-lightblue?style=for-the-badge&logo=mysql" alt="MySQL"></a>&nbsp;
  </p>
  
  <!-- Description -->
  <p><strong>A production-ready food ordering platform built with modern backend engineering practices, microservices patterns.</strong></p>
  
  <!-- Quick Links -->
  <p>
    <a href="https://mealapi.in"><strong>→ Visit Live App</strong></a> •
    <a href="#-overview"><strong>Overview</strong></a> •
    <a href="#-api-documentation"><strong>API Docs</strong></a> •
    <a href="#%EF%B8%8F-system-design-deep-dive"><strong>System Design</strong></a>
  </p>
</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Live Demo](#-live-demo)
- [Key Features](#-key-features)
- [System Design](#%EF%B8%8F-system-design-deep-dive)
- [API Documentation](#-api-documentation)
- [Deployment](#-deployment)
- [Security Implementation](#-security-implementation)
- [Getting Started](#-getting-started)

---

## 🎯 Overview

MealAPI is a **full-stack food ordering platform** designed with enterprise-grade backend architecture, demonstrating proficiency in:

- **Microservices Architecture** with Spring Boot
- **Security & Authentication** using JWT and Spring Security
- **Database Design** and optimization
- **Cloud Infrastructure** (AWS S3 for media storage, AWS EC2 for compute, AWS RDS for database)
- **RESTful API Design** with centralized error handling
- **Payment Integration** (Stripe)
- **Email Notifications** with Thymeleaf templates
- **Production Deployment** on AWS cloud infrastructure

---

## 🌐 Live Demo

**Visit the live application at: https://mealapi.in**

### Test Credentials

**Admin Account:**
- Email: `admin@mealapi.in`
- Password: `admin123`

**Customer Account:**
- Email: `customer@mealapi.in`
- Password: `customer123`

### Features to Explore

1. **Browse Menu** - View all available food items with ratings and reviews
2. **Search & Filter** - Search by name or filter by category
3. **Add to Cart** - Add items to cart and adjust quantities
4. **Checkout** - Complete purchase with Stripe payment integration
5. **Track Orders** - Monitor your order status in real-time
6. **Leave Reviews** - Rate and review food items after delivery
7. **Admin Dashboard** - Analytics and management tools (Admin only)
8. **Manage Menu** - Add, edit, delete menu items (Admin only)

---

## ✨ Key Features

### 1. **User Management**
- User registration with email verification
- Role-based access control (ADMIN, CUSTOMER, DELIVERY)
- Profile management with image upload
- Account deactivation

### 2. **Authentication & Authorization**
- JWT Token Generation & Validation (30-day expiration)
- Spring Security-based authorization
- Role-based endpoint protection (@PreAuthorize)
- Custom AuthenticationEntryPoint for 401 handling
- CustomAccessDenialHandler for 403 handling

### 3. **Menu Management**
- CRUD operations for menu items
- Category-based menu organization
- Image uploads to AWS S3
- Product pricing & descriptions
- Search and filtering capabilities

### 4. **Order Processing**
- Shopping cart management
- Order creation & status tracking
- Order history with pagination
- Order item details with reviews
- Real-time order status updates

### 5. **Payment Integration**
- Stripe payment gateway integration
- Payment status tracking (COMPLETED, PENDING, FAILED)
- Transaction logging
- Email confirmations (HTML templates)

### 6. **Review & Rating System**
- Product reviews with 1-10 star ratings
- User comments
- Average rating calculation
- Review pagination

### 8. **Email Notifications**
- Order confirmation emails
- Payment success/failure notifications
- Thymeleaf HTML templates
- Async email sending

### 9. **Admin Dashboard**
- Real-time statistics (orders, revenue, active customers)
- Monthly revenue analytics with charts
- Order status distribution visualization
- Popular items tracking
- User management interface
- Menu and category management

---

## 🛠️ System Design Deep Dive

### Database Design

#### Core Entities

```sql
-- Users Table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    profile_url VARCHAR(500),
    roles SET('ADMIN', 'CUSTOMER', 'DELIVERY'),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Categories Table
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Menu Items Table
CREATE TABLE menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500),
    category_id BIGINT,
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_category (category_id),
    INDEX idx_price (price)
);

-- Orders Table
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_status ENUM('INITIALIZED', 'CONFIRMED', 'PREPARING', 'READY', 'DISPATCHED', 'DELIVERED', 'CANCELLED'),
    total_price DECIMAL(10, 2) NOT NULL,
    delivery_address TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_status (order_status),
    INDEX idx_created (created_at)
);

-- Order Items Table (Join Table)
CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price_at_time DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE RESTRICT,
    INDEX idx_order (order_id),
    INDEX idx_menu (menu_id)
);

-- Payments Table
CREATE TABLE payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED'),
    payment_gateway VARCHAR(50), -- 'STRIPE', 'RAZORPAY'
    transaction_id VARCHAR(255),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    INDEX idx_order (order_id),
    INDEX idx_status (payment_status),
    UNIQUE KEY uq_transaction (transaction_id)
);

-- Reviews Table
CREATE TABLE reviews (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    menu_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    order_id BIGINT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE SET NULL,
    INDEX idx_menu (menu_id),
    INDEX idx_user (user_id),
    UNIQUE KEY uq_review (menu_id, user_id, order_id)
);
```

### API Design Patterns

#### Response Wrapper Pattern
```java
@Data
@AllArgsConstructor
public class Response<T> {
    private int statusCode;        // 200, 400, 401, 403, 500
    private String message;        // Success/Error message
    private T data;               // Generic payload
    private LocalDateTime timestamp;
}
```

#### Centralized Error Handling Strategy
```java
 Global exception handler (@RestControllerAdvice)
 Custom exceptions:
 - BadRequestException (400)
 - UnauthorizedException (401)
 - AccessDeniedException (403)
 - ResourceNotFoundException (404)
 - InternalServerException (500)
```

#### Security Filter Chain
```java
// Authentication Flow:
1. Request → AuthFilter (extract JWT)
2. Validate token with JwtUtils
3. Load UserDetails from CustomUserDetailsService
4. Set SecurityContext
5. Route to appropriate endpoint
6. CustomAuthenticationEntryPoint handles failures
```

---

## 📡 API Documentation

### Base URL
- **Development**: `http://localhost:8090/api`
- **Production**: `https://mealapi.in/api`

### Authentication Endpoints

```http
POST /api/auth/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secure_password",
  "phoneNumber": "+1-800-123-4567",
  "address": "123 Main St"
}
Response: { statusCode: 200, data: { token, roles } }

POST /api/auth/login
{
  "email": "john@example.com",
  "password": "secure_password"
}
Response: { statusCode: 200, data: { token, roles } }
```

### Menu Endpoints

```http
GET /api/menu
Query: page=0, size=20
Response: { statusCode: 200, data: Page<MenuDTO> }

GET /api/menu/:id
Response: { statusCode: 200, data: MenuDTO }

GET /api/menu/category/:categoryId
Response: { statusCode: 200, data: List<MenuDTO> }

POST /api/menu (ADMIN only)
Content-Type: multipart/form-data
{
  "name": "Burger",
  "description": "Delicious beef burger",
  "price": 9.99,
  "categoryId": 1,
  "imageFile": <binary>
}
Response: { statusCode: 200, data: MenuDTO }

PUT /api/menu (ADMIN only)
Response: { statusCode: 200, data: MenuDTO }

DELETE /api/menu/:id (ADMIN only)
Response: { statusCode: 200, message: "Menu deleted successfully" }
```

### Order Endpoints

```http
POST /api/orders/checkout (CUSTOMER only)
Response: { statusCode: 200, data: OrderDTO }

GET /api/orders/my-orders (CUSTOMER only)
Response: { statusCode: 200, data: List<OrderDTO> }

GET /api/orders/:id
Response: { statusCode: 200, data: OrderDTO }

GET /api/orders (ADMIN only)
Query: status=CONFIRMED, page=0, size=50
Response: { statusCode: 200, data: Page<OrderDTO> }

PUT /api/orders/:id/status (ADMIN/DELIVERY)
{
  "orderStatus": "DISPATCHED"
}
Response: { statusCode: 200, data: OrderDTO }
```

### Payment Endpoints

```http
POST /api/payments/process-payment
{
  "orderId": 1,
  "paymentMethodId": "pm_xxx",
  "amount": 45.99
}
Response: { statusCode: 200, data: PaymentDTO }

GET /api/payments (ADMIN only)
Response: { statusCode: 200, data: List<PaymentDTO> }
```

---

## 🚀 Deployment

### Live Deployment

**Application URL:** https://mealapi.in

The application is deployed on AWS cloud infrastructure with the following setup:

- **Frontend Hosting:** AWS S3 + CloudFront CDN
- **Backend Hosting:** AWS ECS (Elastic Container Service)
- **Database:** AWS RDS MySQL 8.0
- **File Storage:** AWS S3 Bucket
- **SSL/TLS:** CloudFlare Certificate Manager


## 🔒 Security Implementation

### Password Hashing
```java
// BCrypt with strength 10
public String encodePassword(String password) {
    return new BCryptPasswordEncoder(10).encode(password);
}
```

### JWT Token Claims
```java
{
  "sub": "user@example.com",
  "iat": 1704067200,
  "exp": 1721635200,
  "roles": ["CUSTOMER", "ADMIN"],
  "jti": "unique-token-id"
}
```

### CORS Policy
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://your-app.com")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("Authorization", "Content-Type")
            .maxAge(3600);
    }
}
```

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- MySQL 8.0+
- Node.js 20+
- AWS Account (for cloud deployment)

### Backend Setup


```bash
# Clone repository
git clone https://github.com/divakar-chakravarty/FoodApp.git
cd FoodApp

# Configure application.properties
# Update database credentials and AWS S3 config

# Build with Maven
mvn clean install

# Run Spring Boot application
mvn spring-boot:run

# Backend runs on http://localhost:8090
```

### Frontend Setup

```bash
# Navigate to frontend directory
cd ..

# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Frontend runs on http://localhost:3000
```

---

<br/>

<div align="center">
  
  ---
  
  <h3>🚀 Built with ❤️ by <strong>Divakar Chakravorty</strong></h3>
  
  <p><em>"Engineering food ordering, one commit at a time"</em></p>
  
  <p>
    <a href="https://mealapi.in">
      <strong>🌐 Visit Live Application →</strong>
    </a>
  </p>
  
  <p>
    <a href="https://github.com/divakar-chakravarty/FoodApp">⭐ Star on GitHub</a> •
    <a href="https://mealapi.in">🚀 Live Demo</a> •
    <a href="LICENSE">📄 License (MIT)</a>
  </p>
  
  <p><small>© 2026 Divakar Chakravorty. MIT Licensed.</small></p>
  
  ---
  
</div>
