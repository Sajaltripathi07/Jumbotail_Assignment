package com.jumbotail.shipping.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {

    private Long warehouseId;
    private LocationDto warehouseLocation;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationDto {
        private double lat;
        private double lng;
    }
}

