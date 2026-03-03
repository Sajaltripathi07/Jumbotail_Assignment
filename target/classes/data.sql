INSERT INTO CUSTOMER (id, external_id, name, phone_number, latitude, longitude)
VALUES (1, 'Cust-123', 'Shree Kirana Store', '9847******', 11.232, 23.445495);

INSERT INTO CUSTOMER (id, external_id, name, phone_number, latitude, longitude)
VALUES (2, 'Cust-124', 'Andheri Mini Mart', '9101******', 17.232, 33.445495);

INSERT INTO SELLER (id, external_id, name, latitude, longitude, default_shipment_weight_kg)
VALUES (1, 'Seller-NESTLE', 'Nestle Seller', 12.50000, 25.000000, 0.5);

INSERT INTO SELLER (id, external_id, name, latitude, longitude, default_shipment_weight_kg)
VALUES (2, 'Seller-RICE', 'Rice Seller', 14.00000, 28.000000, 10.0);

INSERT INTO SELLER (id, external_id, name, latitude, longitude, default_shipment_weight_kg)
VALUES (3, 'Seller-SUGAR', 'Sugar Seller', 15.00000, 30.000000, 25.0);

INSERT INTO PRODUCT (id, name, selling_price, weight_kg, dimensions, seller_id)
VALUES (1, 'Maggie 500g Packet', 10.0, 0.5, '10cmx10cmx10cm', 1);

INSERT INTO PRODUCT (id, name, selling_price, weight_kg, dimensions, seller_id)
VALUES (2, 'Rice Bag 10Kg', 500.0, 10.0, '1000cmx800cmx500cm', 2);

INSERT INTO PRODUCT (id, name, selling_price, weight_kg, dimensions, seller_id)
VALUES (3, 'Sugar Bag 25Kg', 700.0, 25.0, '1000cmx900cmx600cm', 3);

INSERT INTO WAREHOUSE (id, name, latitude, longitude)
VALUES (1, 'BLR_Warehouse', 12.99999, 37.923273);

INSERT INTO WAREHOUSE (id, name, latitude, longitude)
VALUES (2, 'MUMB_Warehouse', 11.99999, 27.923273);

