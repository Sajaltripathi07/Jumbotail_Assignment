## Shipping Charge Estimator

- **Stack**: Java 17, Spring Boot 3, Web, Data JPA, H2, Cache, Lombok.
- **Run**: `mvn spring-boot:run` (H2 console at `http://localhost:8080/h2-console`, JDBC `jdbc:h2:mem:shippingdb`).
- **Data**: sample customers, sellers, products, warehouses in `data.sql`.
- **APIs**:
  - `GET /api/v1/warehouse/nearest?sellerId=&productId=` – nearest warehouse for a seller.
  - `GET /api/v1/shipping-charge?warehouseId=&customerId=&deliverySpeed=&weightKg=` – shipping charge from warehouse to customer.
  - `POST /api/v1/shipping-charge/calculate` – body `{ sellerId, customerId, deliverySpeed, totalWeightKg? }`, returns charge + nearest warehouse.

See `ARCHITECTURE.md` for full workflow and code-level details.

