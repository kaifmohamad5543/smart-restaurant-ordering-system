// ===============================
// PACKAGE: test
// FILE: LoginAuthenticationTest.java
// ===============================

package test;

import auth.LoginPage;

// Login authentication testing class.
public class LoginAuthenticationTest {

    // Method tests login validation.
    public void testLogin() {

        // Creating login object.
        LoginPage loginPage =
                new LoginPage();

        // Testing login credentials.
        boolean result =
                loginPage.login(
                        "admin",
                        "Admin@123"
                );

        // Validation checks authentication.
        if (result) {

            System.out.println(
                    "Login Authentication Test Passed"
            );

        } else {

            System.out.println(
                    "Login Authentication Test Failed"
            );
        }
    }
}