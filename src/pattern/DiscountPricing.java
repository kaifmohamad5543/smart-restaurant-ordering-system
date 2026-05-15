// ===============================
// PACKAGE: pattern
// FILE: DiscountPricing.java
// ===============================

package pattern;

// Discount pricing strategy implementation.
public class DiscountPricing implements PricingStrategy {

    // Applies 10% discount.
    @Override
    public double calculateFinalPrice(double subtotal) {

        return subtotal * 0.90;
    }
}