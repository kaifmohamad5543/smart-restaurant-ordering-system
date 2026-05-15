package model;

public class Admin extends User {

    public Admin(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Admin: Can manage menu, users, pricing and reports.");
    }
}