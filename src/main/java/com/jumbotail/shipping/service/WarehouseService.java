package com.jumbotail.shipping.service;

import com.jumbotail.shipping.domain.Location;
import com.jumbotail.shipping.domain.Seller;
import com.jumbotail.shipping.domain.Warehouse;
import com.jumbotail.shipping.exception.NotFoundException;
import com.jumbotail.shipping.repository.SellerRepository;
import com.jumbotail.shipping.repository.WarehouseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class WarehouseService {

    private final SellerRepository sellerRepository;
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(SellerRepository sellerRepository,
                            WarehouseRepository warehouseRepository) {
        this.sellerRepository = sellerRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Cacheable(cacheNames = "nearestWarehouse", key = "#sellerId + '-' + #productId")
    public Warehouse findNearestWarehouseForSeller(Long sellerId, Long productId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller not found with id " + sellerId));

        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            throw new NotFoundException("No warehouses configured in the system");
        }

        Location sellerLocation = seller.getLocation();
        return warehouses.stream()
                .min(Comparator.comparingDouble(w -> DistanceCalculator.distanceInKm(
                        sellerLocation, w.getLocation())))
                .orElseThrow(() -> new NotFoundException("No warehouses available for seller " + sellerId));
    }
}

