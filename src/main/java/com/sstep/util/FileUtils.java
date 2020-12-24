package com.sstep.util;

import java.util.Collection;
import java.util.Set;


/**
 * @author sstepanov
 */
public class FileUtils {

    private static final Set<String> ALLOWED_FILE_EXTENSIONS = Set.of("html", "xml");

    public static boolean isValidFile(final String fileName) {
        return isValidFile(fileName, ALLOWED_FILE_EXTENSIONS);
    }

    public static boolean isValidFile(final String fileName, final Collection<String> allowedExtensions) {
        if (fileName.indexOf('.') == -1) {
            return false;
        }
        final String extension = fileName.substring(fileName.lastIndexOf('.') + 1).trim();
        return allowedExtensions.contains(extension);
    }

}
