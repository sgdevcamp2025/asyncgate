package com.asyncgate.user_server.support.utility;

/**
 * Utility class for password-related operations
 */
public class PasswordUtil {

    /**
     * Generate a random authentication code with the given length
     *
     * @param length The length of the authentication code
     * @return The generated authentication code
     */
    public static String generateAuthCode(final Integer length) {
        StringBuilder authCode = new StringBuilder();

        // 숫자로만 구성된 인증 코드 생성
        for (int i = 0; i < length; i++) {
            authCode.append((int) (Math.random() * 10));
        }

        return authCode.toString();
    }
}