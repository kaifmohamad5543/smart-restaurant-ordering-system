// ===============================
// PACKAGE: test
// FILE: RestaurantSystemTest.java
// ===============================

package test;

import model.MenuItem;
import pattern.FoodFactory;
import pattern.NormalPricing;

// Basic testing class for restaurant system.
public class RestaurantSystemTest {

    // Method tests food factory creation.
    public void testFoodFactory() {

        // Creating burger object.
        MenuItem burger =
                FoodFactory.createFood(
                        "burger",
                        101
                );

        // Validation checks object creation.
        if (burger != null) {

            System.out.println(
                    "Food Factory Test Passed"
            );

        } else {

            System.out.println(
                    "Food Factory Test Failed"
            );
        }
    }

    // Method tests pricing strategy.
    public void testPricingStrategy() {

        // Creating pricing strategy object.
        NormalPricing pricing =
                new NormalPricing();

        // Calculating final price.
        double result =
                pricing.calculateFinalPrice(
                        100
                );

        // Validation checks calculation.
        if (result == 100) {

            System.out.println(
                    "Pricing Strategy Test Passed"
            );

        } else {

            System.out.println(
                    "Pricing Strategy Test Failed"
            );
        }
    }
}