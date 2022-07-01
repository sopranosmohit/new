package com.programmingtechie.orderservice.dto;

import com.programmingtechie.orderservice.entity.OrderLineItems;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    private List<OrderLineItems> orderLineItemsList = new java.util.ArrayList<>();
}
