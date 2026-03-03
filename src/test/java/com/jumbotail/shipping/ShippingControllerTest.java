package com.jumbotail.shipping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbotail.shipping.api.dto.ShippingChargeForSellerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShippingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void nearestWarehouseEndpointWorks() throws Exception {
        mockMvc.perform(get("/api/v1/warehouse/nearest")
                        .param("sellerId", "1")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseId").exists())
                .andExpect(jsonPath("$.warehouseLocation.lat").exists())
                .andExpect(jsonPath("$.warehouseLocation.lng").exists());
    }

    @Test
    void shippingChargeEndpointWorks() throws Exception {
        mockMvc.perform(get("/api/v1/shipping-charge")
                        .param("warehouseId", "1")
                        .param("customerId", "1")
                        .param("deliverySpeed", "standard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingCharge").exists());
    }

    @Test
    void shippingChargeForSellerEndpointWorks() throws Exception {
        ShippingChargeForSellerRequest request = new ShippingChargeForSellerRequest();
        request.setSellerId(1L);
        request.setCustomerId(1L);
        request.setDeliverySpeed("express");

        mockMvc.perform(post("/api/v1/shipping-charge/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingCharge").exists())
                .andExpect(jsonPath("$.nearestWarehouse.warehouseId").exists());
    }
}

