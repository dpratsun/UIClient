package com.kator.weightguard.ui.utils;

/**
 * Created by prats on 4/24/18.
 */
public class Base64 {
    public static String encode(String string) {
        return java.util.Base64.getEncoder().encodeToString(string.getBytes());
    }

    public static String decode(String string) {
        return new String(java.util.Base64.getDecoder().decode(string));
    }
}
