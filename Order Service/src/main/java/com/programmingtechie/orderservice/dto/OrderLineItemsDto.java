package com.programmingtechie.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDto {

    private Long id;

    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

}
