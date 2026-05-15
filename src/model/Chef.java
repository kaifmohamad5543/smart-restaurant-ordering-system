package model;

public class Chef extends User {

    public Chef(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Chef: Can receive kitchen orders and update preparation status.");
    }
}