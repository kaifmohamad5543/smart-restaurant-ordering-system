// ===============================
// PACKAGE: pattern
// FILE: PricingStrategy.java
// ===============================

package pattern;

// Strategy Pattern interface.
public interface PricingStrategy {

    // Calculates final bill dynamically.
    double calculateFinalPrice(double subtotal);
}