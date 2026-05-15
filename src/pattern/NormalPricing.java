// ===============================
// PACKAGE: pattern
// FILE: NormalPricing.java
// ===============================

package pattern;

// Normal pricing strategy implementation.
public class NormalPricing implements PricingStrategy {

    // Returns original subtotal.
    @Override
    public double calculateFinalPrice(double subtotal) {

        return subtotal;
    }
}