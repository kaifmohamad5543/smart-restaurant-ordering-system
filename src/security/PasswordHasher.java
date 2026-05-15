// ===============================
// FILE: src/security/PasswordHasher.java
// ===============================

package security;

import java.security.MessageDigest;

// PasswordHasher secures passwords using SHA-256 hashing.
public class PasswordHasher {

    // Static method hashes password.
    public static String hashPassword(String password) {

        try {

            // SHA-256 hashing algorithm.
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            // Converts password into hash bytes.
            byte[] hashBytes =
                    digest.digest(password.getBytes());

            // StringBuilder stores hexadecimal hash.
            StringBuilder builder =
                    new StringBuilder();

            // Loop converts bytes into hexadecimal format.
            for (byte b : hashBytes) {

                builder.append(
                        String.format("%02x", b)
                );
            }

            return builder.toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Password hashing failed."
            );
        }
    }
}