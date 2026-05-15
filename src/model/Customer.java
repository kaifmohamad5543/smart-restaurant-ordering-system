package model;

public class Customer extends User {

    private String phoneNumber;

    public Customer(int userId, String name, String email, String phoneNumber) {
        super(userId, name, email);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void displayRole() {
        System.out.println("Customer: Can browse menu, place orders and receive notifications.");
    }
}