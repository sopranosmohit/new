package com.programmingtechie.orderservice.controller;


import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.dto.OrderResponse;
import com.programmingtechie.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

@RequestMapping("/api/order")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest)
    {

            orderService.placeOrder(orderRequest);
            return "Order Placed Succesfully";

    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders()
    {
      return orderService.getAllOrders("lol");


    }
}
