package com.jumbotail.shipping.service;

import com.jumbotail.shipping.domain.*;
import com.jumbotail.shipping.exception.BadRequestException;
import com.jumbotail.shipping.exception.NotFoundException;
import com.jumbotail.shipping.repository.CustomerRepository;
import com.jumbotail.shipping.repository.SellerRepository;
import com.jumbotail.shipping.repository.WarehouseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ShippingService {

    private static final double MINI_VAN_RATE = 3.0;
    private static final double TRUCK_RATE = 2.0;
    private static final double AEROPLANE_RATE = 1.0;

    private static final double STANDARD_BASE_CHARGE = 10.0;
    private static final double EXPRESS_PER_KG_EXTRA = 1.2;

    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final WarehouseService warehouseService;

    public ShippingService(WarehouseRepository warehouseRepository,
                           CustomerRepository customerRepository,
                           SellerRepository sellerRepository,
                           WarehouseService warehouseService) {
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
        this.warehouseService = warehouseService;
    }

    @Cacheable(cacheNames = "shippingCharge",
            key = "#warehouseId + '-' + #customerId + '-' + #deliverySpeed.name() + '-' + #weightKg")
    public double calculateShippingCharge(Long warehouseId,
                                          Long customerId,
                                          DeliverySpeed deliverySpeed,
                                          double weightKg) {
        if (weightKg <= 0) {
            throw new BadRequestException("Weight must be positive");
        }

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException("Warehouse not found with id " + warehouseId));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id " + customerId));

        double distanceKm = DistanceCalculator.distanceInKm(
                warehouse.getLocation(), customer.getLocation());

        TransportMode mode = determineTransportMode(distanceKm);
        double ratePerKmPerKg = rateForMode(mode);

        double variableCharge = distanceKm * ratePerKmPerKg * weightKg;
        double baseCharge = STANDARD_BASE_CHARGE;

        if (deliverySpeed == DeliverySpeed.EXPRESS) {
            baseCharge += EXPRESS_PER_KG_EXTRA * weightKg;
        }

        double total = baseCharge + variableCharge;
        return BigDecimal.valueOf(total)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public double calculateShippingChargeForSellerAndCustomer(Long sellerId,
                                                              Long customerId,
                                                              DeliverySpeed deliverySpeed,
                                                              Double weightKgOverride) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller not found with id " + sellerId));

        double effectiveWeight = weightKgOverride != null && weightKgOverride > 0
                ? weightKgOverride
                : (seller.getDefaultShipmentWeightKg() > 0 ? seller.getDefaultShipmentWeightKg() : 1.0);

        Warehouse nearest = warehouseService.findNearestWarehouseForSeller(sellerId, null);
        return calculateShippingCharge(nearest.getId(), customerId, deliverySpeed, effectiveWeight);
    }

    private TransportMode determineTransportMode(double distanceKm) {
        if (distanceKm <= 100) {
            return TransportMode.MINI_VAN;
        } else if (distanceKm <= 500) {
            return TransportMode.TRUCK;
        } else {
            return TransportMode.AEROPLANE;
        }
    }

    private double rateForMode(TransportMode mode) {
        return switch (mode) {
            case MINI_VAN -> MINI_VAN_RATE;
            case TRUCK -> TRUCK_RATE;
            case AEROPLANE -> AEROPLANE_RATE;
        };
    }
}

