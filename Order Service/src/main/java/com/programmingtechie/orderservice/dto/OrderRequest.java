package com.programmingtechie.orderservice.dto;

import com.programmingtechie.orderservice.entity.OrderLineItems;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {


    private Long id;

    private String orderNumber;

    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
