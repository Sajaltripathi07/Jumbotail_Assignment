package com.jumbotail.shipping.api;

import com.jumbotail.shipping.api.dto.WarehouseResponse;
import com.jumbotail.shipping.domain.Warehouse;
import com.jumbotail.shipping.service.WarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/nearest")
    public WarehouseResponse getNearestWarehouse(@RequestParam Long sellerId,
                                                 @RequestParam(required = false) Long productId) {
        Warehouse nearest = warehouseService.findNearestWarehouseForSeller(sellerId, productId);
        return new WarehouseResponse(
                nearest.getId(),
                new WarehouseResponse.LocationDto(
                        nearest.getLocation().getLatitude(),
                        nearest.getLocation().getLongitude()
                )
        );
    }
}

