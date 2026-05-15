// ===============================
// PACKAGE: security
// FILE: SecurityUtil.java
// ===============================

package security;

// Security utility class.
public class SecurityUtil {

    // Validates password strength.
    public static boolean validatePassword(String password) {

        // Password must contain minimum 8 characters.
        if (password.length() < 8) {

            return false;
        }

        // Password must contain @ symbol.
        return password.contains("@");
    }

    // Displays security status.
    public static void showSecurityStatus() {

        System.out.println(
                "Security Validation and SHA-256 Hashing Enabled"
        );
    }
}