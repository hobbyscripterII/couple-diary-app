package com.couple.couplediaryapp.common;

public class Utils {

    public static boolean strNotNull(String str) {
        return str == null ? false : true;
    }

    public static boolean numNotNull(int num) {
        return num == 0 ? false : true;
    }

    public static boolean objNotNull(Object object) {
        return object == null ? false : true;
    }
}
