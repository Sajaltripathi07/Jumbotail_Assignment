package com.jumbotail.shipping.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShippingChargeForSellerRequest {

    @NotNull
    private Long sellerId;

    @NotNull
    private Long customerId;

    @NotNull
    private String deliverySpeed;

    private Double totalWeightKg;
}

