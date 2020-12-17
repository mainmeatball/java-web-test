package com.sstep.util;


/**
 * @author sstepanov
 */
public class StringUtils {
    public static boolean isEmpty(final String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }
}
