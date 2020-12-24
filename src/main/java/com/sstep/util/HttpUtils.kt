package com.sstep.util

/**
 * @author sstepanov
 */

private val ALLOWED_FILE_EXTENSIONS = setOf("html", "xml")

@JvmOverloads
fun String.isValidFile(allowedExtensions: List<String> = emptyList()): Boolean {
    val allowedFileExtensions = if (allowedExtensions.isEmpty()) ALLOWED_FILE_EXTENSIONS else allowedExtensions
    if (indexOf('.') == -1) {
        return false
    }
    val extension = substringAfterLast('.')
    return extension in allowedFileExtensions
}