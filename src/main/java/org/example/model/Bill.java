package org.example.model;

/**
 * Record pentru factura.
 */
public record Bill(
        int id,
        int orderId,
        String clientName,
        double totalPrice
) {
}