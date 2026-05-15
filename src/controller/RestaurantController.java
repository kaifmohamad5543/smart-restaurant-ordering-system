package controller;

import model.MenuItem;
import pattern.RestaurantSystem;

// Controller handles restaurant menu operations.
public class RestaurantController {

    // Singleton restaurant system object.
    private RestaurantSystem restaurantSystem;

    // Constructor initializes system.
    public RestaurantController() {

        restaurantSystem =
                RestaurantSystem.getInstance();
    }

    // Adds menu item into restaurant menu.
    public void addMenuItem(MenuItem item) {

        restaurantSystem.addMenuItem(item);

        System.out.println(
                item.getItemName() +
                        " added successfully."
        );
    }

    // Displays menu items.
    public void showMenu() {

        restaurantSystem.displayMenu();
    }

    // Returns restaurant system object.
    public RestaurantSystem getRestaurantSystem() {

        return restaurantSystem;
    }
}