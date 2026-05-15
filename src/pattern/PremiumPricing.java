// ===============================
// PACKAGE: pattern
// FILE: PremiumPricing.java
// ===============================

package pattern;

// Premium pricing adds service charge.
public class PremiumPricing implements PricingStrategy {

    // Applies 15% premium service charge.
    @Override
    public double calculateFinalPrice(double subtotal) {

        return subtotal + (subtotal * 0.15);
    }
}