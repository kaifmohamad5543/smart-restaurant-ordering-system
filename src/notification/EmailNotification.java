// ===============================
// PACKAGE: notification
// FILE: EmailNotification.java
// ===============================

package notification;

// Email notification service implements NotificationService interface.
public class EmailNotification implements NotificationService {

    // Overridden method demonstrates polymorphism.
    @Override
    public void sendNotification(String message) {

        // Displays email notification.
        System.out.println("EMAIL NOTIFICATION: " + message);
    }
}