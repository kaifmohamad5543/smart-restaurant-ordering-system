package controller;

import model.Order;
import model.Payment;

public class PaymentController {

    public void processPayment(int paymentId, Order order, double amount, String method) {
        Payment payment = new Payment(paymentId, order.getOrderId(), amount, method);
        payment.processPayment();
    }
}