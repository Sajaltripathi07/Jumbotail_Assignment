package com.jumbotail.shipping.repository;

import com.jumbotail.shipping.domain.Product;
import com.jumbotail.shipping.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySeller(Seller seller);
}

