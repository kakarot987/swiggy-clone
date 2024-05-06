package com.swiggyapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swiggyapi.domain.Order;
import com.swiggyapi.service.CustomerOrderService;

@RestController
@RequestMapping(value = "/api")
public class CustomerOrderController {

	@Autowired
	private CustomerOrderService customerOrderService;
	
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrdersOfCustomer(@RequestParam("userId") String userId) {
    	List<Order> orders = customerOrderService.findByUserId(userId);
    	return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(customerOrderService.findByOrderId(orderId));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
    	return ResponseEntity.ok(customerOrderService.saveOrder(order));
    }
    
}
