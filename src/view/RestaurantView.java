// ===============================
// PACKAGE: view
// FILE: RestaurantView.java
// ===============================

package view;

import model.MenuItem;
import model.Order;

import java.util.List;

// View layer handles system output presentation.
public class RestaurantView {

    // Displays system welcome message.
    public void showWelcomeMessage() {

        System.out.println("=====================================");
        System.out.println(" SMART RESTAURANT ORDERING SYSTEM");
        System.out.println("=====================================");
    }

    // Displays restaurant menu items.
    public void showMenu(List<MenuItem> menuItems) {

        System.out.println("\n===== RESTAURANT MENU =====");

        // Loop displays all food items.
        for (MenuItem item : menuItems) {

            item.displayItem();
        }
    }

    // Displays customer order information.
    public void showOrder(Order order) {

        System.out.println("\n===== CUSTOMER ORDER =====");

        order.displayOrder();
    }

    // Displays generic system message.
    public void showMessage(String message) {

        System.out.println(message);
    }

    // Displays bill amount.
    public void showBill(double amount) {

        System.out.println("\nFinal Bill Amount: £" + amount);
    }

    // Displays order success message.
    public void showOrderSuccess() {

        System.out.println("Order placed successfully.");
    }

    // Displays payment success message.
    public void showPaymentSuccess() {

        System.out.println("Payment processed successfully.");
    }
}