package test;

import model.MenuItem;

import org.junit.jupiter.api.Test;

import pattern.DiscountPricing;
import pattern.FoodFactory;
import pattern.NormalPricing;
import pattern.PremiumPricing;

import security.PasswordHasher;
import security.SecurityUtil;

import static org.junit.jupiter.api.Assertions.*;

public class AdvancedRestaurantTest {

    @Test
    public void testFoodFactoryBurgerCreation() {

        MenuItem burger =
                FoodFactory.createFood(
                        "burger",
                        101
                );

        assertNotNull(burger);

        assertEquals(
                "Classic Burger",
                burger.getItemName()
        );

        System.out.println(
                "Burger created successfully."
        );
    }

    @Test
    public void testFoodFactoryPizzaCreation() {

        MenuItem pizza =
                FoodFactory.createFood(
                        "pizza",
                        102
                );

        assertNotNull(pizza);

        assertEquals(
                "Margherita Pizza",
                pizza.getItemName()
        );

        System.out.println(
                "Pizza created successfully."
        );
    }

    @Test
    public void testNormalPricing() {

        NormalPricing pricing =
                new NormalPricing();

        double result =
                pricing.calculateFinalPrice(
                        100
                );

        assertEquals(
                100,
                result
        );

        System.out.println(
                "Normal pricing test passed."
        );
    }

    @Test
    public void testDiscountPricing() {

        DiscountPricing pricing =
                new DiscountPricing();

        double result =
                pricing.calculateFinalPrice(
                        100
                );

        assertEquals(
                90,
                result
        );

        System.out.println(
                "Discount pricing test passed."
        );
    }

    @Test
    public void testPremiumPricing() {

        PremiumPricing pricing =
                new PremiumPricing();

        double result =
                pricing.calculateFinalPrice(
                        100
                );

        assertEquals(
                115,
                result
        );

        System.out.println(
                "Premium pricing test passed."
        );
    }

    @Test
    public void testPasswordHashing() {

        String hash =
                PasswordHasher.hashPassword(
                        "Admin@123"
                );

        assertNotNull(hash);

        assertNotEquals(
                "Admin@123",
                hash
        );

        System.out.println(
                "Password hashing test passed."
        );
    }

    @Test
    public void testPasswordValidation() {

        boolean result =
                SecurityUtil.validatePassword(
                        "Admin@123"
                );

        assertTrue(result);

        System.out.println(
                "Password validation test passed."
        );
    }

    @Test
    public void performanceTest() {

        long start =
                System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {

            MenuItem item =
                    FoodFactory.createFood(
                            "burger",
                            i
                    );

            assertNotNull(item);
        }

        long end =
                System.currentTimeMillis();

        assertTrue(
                (end - start) < 1000
        );

        System.out.println(
                "Performance test completed successfully."
        );
    }
}