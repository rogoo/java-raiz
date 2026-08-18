package com.example.shop.adapter.in.web;

import com.example.shop.application.port.in.PlaceOrder;
import com.example.shop.application.port.out.OrderRepository;
import com.example.shop.domain.OrderId;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Driving adapter. Spring, JSON and HTTP status codes stop here - everything
 * below this class is expressed in domain terms.
 */
@RestController
@RequestMapping("/orders")
class OrderController {

    private final PlaceOrder placeOrder;
    private final OrderRepository orderRepository;

    OrderController(PlaceOrder placeOrder, OrderRepository orderRepository) {
        this.placeOrder = placeOrder;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        OrderId id = placeOrder.handle(request.toCommand());
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.created(URI.create("/orders/" + id)).body(OrderResponse.from(order)))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable String id) {
        return orderRepository.findById(OrderId.of(id))
                .map(order -> ResponseEntity.ok(OrderResponse.from(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
