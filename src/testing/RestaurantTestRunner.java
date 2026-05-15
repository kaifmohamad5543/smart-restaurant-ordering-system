// ===============================
// PACKAGE: testing
// FILE: RestaurantTestRunner.java
// ===============================

package testing;

import test.LoginAuthenticationTest;
import test.RestaurantSystemTest;

// Central test runner class.
public class RestaurantTestRunner {

    // Method executes all testing classes.
    public void runTests() {

        System.out.println("\n===== SYSTEM TESTING =====");

        // Creating restaurant-system test object.
        RestaurantSystemTest systemTest =
                new RestaurantSystemTest();

        // Running food factory test.
        systemTest.testFoodFactory();

        // Running pricing strategy test.
        systemTest.testPricingStrategy();

        // Creating login-authentication test object.
        LoginAuthenticationTest loginTest =
                new LoginAuthenticationTest();

        // Running login test.
        loginTest.testLogin();

        // Final testing output.
        System.out.println(
                "\nAll Testing Completed Successfully"
        );
    }
}