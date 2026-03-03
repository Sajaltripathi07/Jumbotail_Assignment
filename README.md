Shipping Charge Estimator

A Spring Boot application that calculates shipping charges for a B2B marketplace by:

Selecting the nearest warehouse

Choosing the optimal transport mode based on distance

Applying pricing rules based on delivery speed and shipment weight

Returning structured JSON responses with proper error handling

⚙️ Tech Stack

Java 17

Spring Boot 3

Spring Web

Spring Data JPA

H2 (In-Memory DB)

Spring Cache

Lombok

JUnit + MockMvc

▶️ Run the Application
mvn spring-boot:run

Base URL: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

JDBC: jdbc:h2:mem:shippingdb

User: sa

Password: (empty)

Sample data is preloaded via data.sql.

📌 API Endpoints
1️⃣ Nearest Warehouse
GET /api/v1/warehouse/nearest?sellerId=1

Returns the nearest warehouse for the seller.

2️⃣ Shipping Charge (Direct)
GET /api/v1/shipping-charge?warehouseId=1&customerId=1&deliverySpeed=STANDARD&weightKg=5

Returns:

{
  "shippingCharge": 145.75
}
3️⃣ Shipping Charge (Seller-Based)
POST /api/v1/shipping-charge/calculate

Request:

{
  "sellerId": 1,
  "customerId": 1,
  "deliverySpeed": "STANDARD",
  "totalWeightKg": 10
}

Response:

{
  "shippingCharge": 210.40,
  "nearestWarehouse": {
    "warehouseId": 2,
    "warehouseLocation": {
      "lat": 12.97,
      "lng": 77.59
    }
  }
}
🏗 Architecture

Layered design:

Controller → Service → Repository

## API Demo

### 1. Nearest Warehouse
![Nearest Warehouse](ScreenShots/nearest-warehouse.png)

### 2. Shipping Charge (Seller-Based)
![Shipping Charge Success](ScreenShots/shipping-calc-success.png)

### 3. Error Handling Example
![Error Handling](ScreenShots/shipping-error.png)

Performance Optimization

@EnableCaching enabled

Cached:

Nearest warehouse lookup

Shipping charge calculations

Improves performance for repeated requests.


🛡 Error Handling

Centralized @ControllerAdvice:

400 → Validation / bad input

404 → Entity not found

500 → Unexpected error

All errors returned as structured JSON.

🧪 Testing

Integration tests using:

@SpringBootTest

@AutoConfigureMockMvc

Verifies:

Warehouse lookup

Shipping charge calculation

Seller-based workflow

JSON response structure

