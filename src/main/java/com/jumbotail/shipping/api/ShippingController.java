package com.jumbotail.shipping.api;

import com.jumbotail.shipping.api.dto.ShippingChargeForSellerRequest;
import com.jumbotail.shipping.api.dto.ShippingChargeResponse;
import com.jumbotail.shipping.api.dto.ShippingChargeWithWarehouseResponse;
import com.jumbotail.shipping.api.dto.WarehouseResponse;
import com.jumbotail.shipping.domain.DeliverySpeed;
import com.jumbotail.shipping.domain.Warehouse;
import com.jumbotail.shipping.exception.BadRequestException;
import com.jumbotail.shipping.service.ShippingService;
import com.jumbotail.shipping.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ShippingController {

    private final ShippingService shippingService;
    private final WarehouseService warehouseService;

    public ShippingController(ShippingService shippingService,
                              WarehouseService warehouseService) {
        this.shippingService = shippingService;
        this.warehouseService = warehouseService;
    }

    @GetMapping("/shipping-charge")
    public ShippingChargeResponse getShippingCharge(@RequestParam Long warehouseId,
                                                    @RequestParam Long customerId,
                                                    @RequestParam String deliverySpeed,
                                                    @RequestParam(required = false) Double weightKg) {
        DeliverySpeed speed = parseDeliverySpeed(deliverySpeed);
        double effectiveWeight = (weightKg != null && weightKg > 0) ? weightKg : 1.0;
        double charge = shippingService.calculateShippingCharge(warehouseId, customerId, speed, effectiveWeight);
        return new ShippingChargeResponse(charge);
    }

    @PostMapping("/shipping-charge/calculate")
    public ShippingChargeWithWarehouseResponse calculateForSellerAndCustomer(
            @Valid @RequestBody ShippingChargeForSellerRequest request) {
        DeliverySpeed speed = parseDeliverySpeed(request.getDeliverySpeed());
        double charge = shippingService.calculateShippingChargeForSellerAndCustomer(
                request.getSellerId(),
                request.getCustomerId(),
                speed,
                request.getTotalWeightKg()
        );

        Warehouse nearest = warehouseService.findNearestWarehouseForSeller(
                request.getSellerId(), null);

        WarehouseResponse warehouseResponse = new WarehouseResponse(
                nearest.getId(),
                new WarehouseResponse.LocationDto(
                        nearest.getLocation().getLatitude(),
                        nearest.getLocation().getLongitude()
                )
        );

        return new ShippingChargeWithWarehouseResponse(charge, warehouseResponse);
    }

    private DeliverySpeed parseDeliverySpeed(String value) {
        try {
            return DeliverySpeed.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Unsupported deliverySpeed: " + value +
                    ". Supported values: STANDARD, EXPRESS");
        }
    }
}

