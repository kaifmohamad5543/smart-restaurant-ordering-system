package model;

public class Cashier extends User {

    public Cashier(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Cashier: Can process payments and generate bills.");
    }
}