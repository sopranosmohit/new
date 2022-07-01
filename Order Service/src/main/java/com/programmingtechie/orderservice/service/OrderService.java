package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.dto.OrderResponse;
import com.programmingtechie.orderservice.entity.Order;
import com.programmingtechie.orderservice.entity.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest)
    {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
      List<OrderLineItems> orderLineItemsList =   orderRequest.getOrderLineItemsDtoList().stream().map(this::maptoOrder).collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = orderLineItemsList.stream().map(e->e.getSkuCode()).collect(Collectors.toList());

        InventoryResponse[] inventoryResponses  = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve().
                bodyToMono(InventoryResponse[].class).block();

      boolean result =  Arrays.stream(inventoryResponses).allMatch(e->e.isInStock());
        if(result)
        orderRepository.save(order);
        else
            throw new RuntimeException("Product not in stock");
    }

    private OrderLineItems maptoOrder(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

    private OrderLineItems maptoOrderLineItems(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

    @CircuitBreaker(name = "order",fallbackMethod = "fallBackGetAllOrders")
    public List<OrderResponse> getAllOrders(String id) {

        if(!id.equals("lol"))
        return orderRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
        else
            throw new RuntimeException();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<OrderResponse> fallBackGetAllOrders(String id , RuntimeException exception)
    {
       return null;

    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.builder().id(order.getId()).build();
        orderResponse.setOrderLineItemsList(order.getOrderLineItemsList());
        return orderResponse;
    }
}
