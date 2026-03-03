Shipping Charge Estimator

A Spring Boot application that calculates shipping charges for a B2B marketplace.

It:

Finds the nearest warehouse

Selects transport mode based on distance

Applies pricing rules based on delivery speed and weight

Returns structured JSON responses with proper error handling



⚙️ Tech Stack

Java 17 • Spring Boot 3 • Spring Web • Spring Data JPA • H2
Spring Cache • Lombok • JUnit + MockMvc




▶️ Run the App
mvn spring-boot:run

Base URL:
http://localhost:8080

H2 Console:
http://localhost:8080/h2-console



Sample data is preloaded via data.sql.




📌 APIs
1. Nearest Warehouse

GET /api/v1/warehouse/nearest?sellerId=1

2. Shipping Charge (Direct)

GET /api/v1/shipping-charge?warehouseId=1&customerId=1&deliverySpeed=STANDARD&weightKg=5

3. Shipping Charge (Seller-Based)

POST /api/v1/shipping-charge/calculate





🏗 Architecture
Layered design:

Controller → Service → Repository




🚀 Optimization
@EnableCaching enabled

Caches warehouse lookup & shipping calculations




🛡 Error Handling
Centralized @ControllerAdvice

400 → Bad request

404 → Not found

500 → Server error




🧪 Testing

Integration tests using:

@SpringBootTest

@AutoConfigureMockMvc
