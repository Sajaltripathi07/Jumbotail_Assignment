package com.jumbotail.shipping.repository;

import com.jumbotail.shipping.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByExternalId(String externalId);
}

