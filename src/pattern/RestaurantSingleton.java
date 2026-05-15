// ===============================
// PACKAGE: pattern
// FILE: RestaurantSingleton.java
// ===============================

package pattern;

// Singleton Pattern ensures single object creation.
public class RestaurantSingleton {

    // Static object reference.
    private static RestaurantSingleton instance;

    // Private constructor prevents direct object creation.
    private RestaurantSingleton() {

        System.out.println("Restaurant Singleton System Started");
    }

    // Static method returns single object.
    public static RestaurantSingleton getInstance() {

        // Creates object only once.
        if (instance == null) {

            instance = new RestaurantSingleton();
        }

        return instance;
    }

    // Displays singleton status.
    public void showStatus() {

        System.out.println("Singleton System Running Successfully");
    }
}