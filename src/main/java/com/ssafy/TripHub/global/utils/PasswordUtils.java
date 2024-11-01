package com.ssafy.TripHub.global.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String encodePassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

    private PasswordUtils() {
    }
}
