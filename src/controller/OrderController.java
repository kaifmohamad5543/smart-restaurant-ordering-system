package controller;

import model.Customer;
import model.MenuItem;
import model.Order;
import model.OrderStatus;
import notification.NotificationService;
import pattern.PricingStrategy;
import pattern.RestaurantSystem;

public class OrderController {

    private RestaurantSystem restaurantSystem;
    private NotificationService notificationService;
    private PricingStrategy pricingStrategy;

    public OrderController(NotificationService notificationService, PricingStrategy pricingStrategy) {
        restaurantSystem = RestaurantSystem.getInstance();
        this.notificationService = notificationService;
        this.pricingStrategy = pricingStrategy;
    }

    public Order createOrder(int orderId, Customer customer) {
        Order order = new Order(orderId, customer);
        restaurantSystem.addOrder(order);
        notificationService.sendNotification("New order created for " + customer.getName());
        return order;
    }

    public void addItemToOrder(Order order, MenuItem item) {
        order.addItem(item);
        notificationService.sendNotification(item.getItemName() + " added to order.");
    }

    public void updateOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        notificationService.sendNotification("Order status updated to " + status);
    }

    public double calculateFinalBill(Order order) {
        return pricingStrategy.calculateFinalPrice(order.calculateSubtotal());
    }
}