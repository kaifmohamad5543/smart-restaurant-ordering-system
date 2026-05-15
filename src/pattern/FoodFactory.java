// ===============================
// FILE: src/pattern/FoodFactory.java
// ===============================

package pattern;

import model.MenuItem;

// Factory Pattern dynamically creates food objects.
public class FoodFactory {

    // Static method returns different food objects.
    public static MenuItem createFood(String type, int id) {

        // Switch statement checks food type.
        switch (type.toLowerCase()) {

            case "burger":

                return new MenuItem(
                        id,
                        "Classic Burger",
                        "Main Course",
                        8.99
                );

            case "pizza":

                return new MenuItem(
                        id,
                        "Margherita Pizza",
                        "Main Course",
                        11.50
                );

            case "coffee":

                return new MenuItem(
                        id,
                        "Cappuccino",
                        "Drink",
                        3.20
                );

            case "cake":

                return new MenuItem(
                        id,
                        "Chocolate Cake",
                        "Dessert",
                        4.50
                );

            default:

                // Exception handling improves reliability.
                throw new IllegalArgumentException(
                        "Invalid food type: " + type
                );
        }
    }
}