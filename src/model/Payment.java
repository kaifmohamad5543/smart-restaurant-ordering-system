package model;

public class Payment {

    private int paymentId;
    private int orderId;
    private double amount;
    private String method;
    private PaymentStatus status;

    public Payment(int paymentId, int orderId, double amount, String method) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.PENDING;
    }

    public void processPayment() {
        if (amount > 0) {
            status = PaymentStatus.PAID;
            System.out.println("Payment Successful: £" + amount + " using " + method);
        } else {
            status = PaymentStatus.FAILED;
            System.out.println("Payment Failed.");
        }
    }

    public PaymentStatus getStatus() {
        return status;
    }
}