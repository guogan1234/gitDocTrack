/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.util;

import java.util.Date;

/**
 * Created by zhoujs on 2017/5/27.
 * 工具类
 */
public class Validator {

    public static boolean equals(Integer s1, Integer s2) {
        if ((s1 == null) && (s2 == null)) {
            return true;
        } else if ((s1 == null) || (s2 == null)) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }

    public static boolean equals(String s1, String s2) {
        if ((s1 == null) && (s2 == null)) {
            return true;
        } else if ((s1 == null) || (s2 == null)) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }

    public static boolean equals(Date s1, Date s2) {
        if ((s1 == null) && (s2 == null)) {
            return true;
        } else if ((s1 == null) || (s2 == null)) {
            return false;
        } else {
            return s1.compareTo(s2) == 1;
        }
    }

    public static boolean isChar(char c) {
        return Character.isLetter(c);
    }

    public static boolean isChar(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isChar(c[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean isDigit(char c) {
        int x = c;

        if ((x >= MagicNumber.FOUR_EIGHT) && (x <= MagicNumber.FIVE_SEVEN)) {
            return true;
        }

        return false;
    }

    public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }

    /*public static boolean isDate(int month, int day, int year) {
        return isGregorianDate(month, day, year);
    }

    public static boolean isGregorianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if (((year % 4) == 0) && ((year % 100) != 0) ||
                    ((year % 400) == 0)) {

                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    public static boolean isJulianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if ((year % 4) == 0) {
                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }*/


    public static boolean isEmailAddressSpecialChar(char c) {

        // LEP-1445

        for (int i = 0; i < _EMAIL_ADDRESS_SPECIAL_CHAR.length; i++) {
            if (c == _EMAIL_ADDRESS_SPECIAL_CHAR[i]) {
                return true;
            }
        }

        return false;
    }


    public static boolean isName(String name) {
        if (isNull(name)) {
            return false;
        }

        char[] c = name.trim().toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (((!isChar(c[i])) &&
                    (!Character.isWhitespace(c[i]))) ||
                    (c[i] == ',')) {

                return false;
            }
        }

        return true;
    }

    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        char[] c = number.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNull(Object obj) {
        if (obj instanceof Long) {
            return isNull((Long) obj);
        } else if (obj instanceof String) {
            return isNull((String) obj);
        } else {
            return obj == null;
        }
    }

    public static boolean isNull(Long l) {
        return (l == null) || l.longValue() == 0;
    }

    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        s = s.trim();

        if ((s.equals(StringPool.NULL)) || (s.equals(StringPool.BLANK))) {
            return true;
        }

        return false;
    }

    public static boolean isNull(Object[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNotNull(Long l) {
        return !isNull(l);
    }

    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    public static boolean isNotNull(Object[] array) {
        return !isNull(array);
    }

    public static boolean isPassword(String password) {
        if (isNull(password)) {
            return false;
        }

        if (password.length() < MagicNumber.FOUR) {
            return false;
        }

        char[] c = password.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if ((!isChar(c[i])) &&
                    (!isDigit(c[i]))) {

                return false;
            }
        }

        return true;
    }

    private static char[] _EMAIL_ADDRESS_SPECIAL_CHAR = new char[]{
            '.', '!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^',
            '_', '`', '{', '|', '}', '~'
    };

}