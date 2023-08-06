package org.webshop;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order implements Comparable<Order>{
    private long id;
    private final LocalDateTime timeOfOrder;
    private final Customer customer;
    private final Map<Product, Integer> cart;

    public Order(long id, LocalDateTime timeOfOrder, Customer customer, Map<Product, Integer> cart) {

        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty!");
        }
        if (id == 0) {
            throw new IllegalArgumentException("Number 0 is not a valid id!");
        }
        if (timeOfOrder == null) {
            throw new IllegalArgumentException("Time of order cannot be empty!");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be empty!");
        }

        this.id = id;
        this.timeOfOrder = timeOfOrder;
        this.customer = customer;
        this.cart = cart;
    }

    public int getTotalAmount() {
        int price = 0;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            price += entry.getKey().getPrice() * entry.getValue();
            }

        if (customer.getCategory().equals(CustomerCategory.VIP)){
            price -= price * CustomerCategory.VIP.getDiscount() / 100;
        }

        return price;
    }

    public boolean hasCustomerBoughtProduct(Product product) {
        return cart.containsKey(product);
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, Integer> getCart() {
        return new HashMap<>(cart);
    }

    public void setId(long id) {
        this.id = id;
    }
    @Override
    public int compareTo(Order o) {
        return o.getTotalAmount() - this.getTotalAmount();
    }
}

