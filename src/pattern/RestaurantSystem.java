// ===============================
// FILE: src/pattern/RestaurantSystem.java
// ===============================

package pattern;

import model.MenuItem;
import model.Order;

import java.util.ArrayList;
import java.util.List;

// Singleton Pattern ensures only one restaurant system exists.
public class RestaurantSystem {

    // Static instance object.
    private static RestaurantSystem instance;

    // List stores restaurant menu items.
    private List<MenuItem> menuItems;

    // List stores customer orders.
    private List<Order> orders;

    // Private constructor prevents external object creation.
    private RestaurantSystem() {

        menuItems = new ArrayList<>();

        orders = new ArrayList<>();
    }

    // Static method returns single system instance.
    public static RestaurantSystem getInstance() {

        // Object created only once.
        if (instance == null) {

            instance = new RestaurantSystem();
        }

        return instance;
    }

    // Method adds menu item.
    public void addMenuItem(MenuItem item) {

        menuItems.add(item);
    }

    // Method adds order.
    public void addOrder(Order order) {

        orders.add(order);
    }

    // Getter method returns menu items.
    public List<MenuItem> getMenuItems() {

        return menuItems;
    }

    // Method displays menu.
    public void displayMenu() {

        System.out.println("\n===== RESTAURANT MENU =====");

        for (MenuItem item : menuItems) {

            item.displayItem();
        }
    }
}