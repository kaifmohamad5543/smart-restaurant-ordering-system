package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderId;
    private Customer customer;
    private List<MenuItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addItem(MenuItem item) {
        if (item.isAvailable()) {
            items.add(item);
        } else {
            System.out.println(item.getItemName() + " is not available.");
        }
    }

    public double calculateSubtotal() {
        double total = 0;

        for (MenuItem item : items) {
            total += item.getPrice();
        }

        return total;
    }

    public void displayOrder() {
        System.out.println("\n--- ORDER DETAILS ---");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Status: " + status);
        System.out.println("Created At: " + createdAt);

        for (MenuItem item : items) {
            System.out.println("- " + item.getItemName() + " £" + item.getPrice());
        }

        System.out.println("Subtotal: £" + calculateSubtotal());
    }
}