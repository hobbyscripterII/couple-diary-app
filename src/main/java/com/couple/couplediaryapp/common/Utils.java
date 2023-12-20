package com.couple.couplediaryapp.common;

public class Utils {
    public static boolean isNotNull(String str) {
        return str == null ? false : true;
    }

    public static boolean isNotNull(int num) {
        return num == 0 ? false : true;
    }

    public static boolean isNotNull(Object object) {
        return object == null ? false : true;
    }
}
