// ===============================
// PACKAGE: notification
// FILE: SMSNotification.java
// ===============================

package notification;

// SMS notification service implementation.
public class SMSNotification implements NotificationService {

    // Overridden method sends SMS notification.
    @Override
    public void sendNotification(String message) {

        // Displays SMS notification.
        System.out.println("SMS NOTIFICATION: " + message);
    }
}