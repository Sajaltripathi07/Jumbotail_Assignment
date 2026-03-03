package com.jumbotail.shipping.repository;

import com.jumbotail.shipping.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}

