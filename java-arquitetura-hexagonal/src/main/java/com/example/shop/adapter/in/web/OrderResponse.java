package com.example.shop.adapter.in.web;

import com.example.shop.domain.Order;
import java.util.List;

public record OrderResponse(String id, String customerId, String status, String total, List<LineView> lines) {

    public record LineView(String sku, int quantity, String unitPrice, String subtotal) {
    }

    public static OrderResponse from(Order order) {
        List<LineView> views = order.lines().stream()
                .map(l -> new LineView(l.sku(), l.quantity(), l.unitPrice().toString(), l.subtotal().toString()))
                .toList();
        return new OrderResponse(
                order.id().toString(),
                order.customerId(),
                order.status().name(),
                order.total().toString(),
                views);
    }
}
