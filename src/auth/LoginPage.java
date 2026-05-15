package auth;

import dashboard.RestaurantDashboard;
import security.PasswordHasher;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final String validUsername = "admin";
    private final String validPasswordHash =
            PasswordHasher.hashPassword("Admin@123");

    public LoginPage() {

        setTitle("Smart Restaurant Login");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("Restaurant Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(70, 20, 250, 30);
        add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(60, 80, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 80, 160, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 120, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 120, 160, 25);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 170, 110, 35);
        add(loginButton);

        loginButton.addActionListener(e -> login());
    }

    public boolean login(String username, String password) {

        String inputHash = PasswordHasher.hashPassword(password);

        return validUsername.equals(username)
                && validPasswordHash.equals(inputHash);
    }

    private void login() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (login(username, password)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful"
            );

            dispose();

            RestaurantDashboard dashboard =
                    new RestaurantDashboard();

            dashboard.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password"
            );
        }
    }
}