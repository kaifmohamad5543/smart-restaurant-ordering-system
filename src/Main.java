import auth.LoginPage;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginPage loginPage = new LoginPage();

            loginPage.setVisible(true);
        });
    }
}