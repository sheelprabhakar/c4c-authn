package com.c4c.auth.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Helpers.
 */
public class Helpers {
  /**
   * Generate random string string.
   *
   * @param length the length
   * @return the string
   */
  public static String generateRandomString(int length) {
    String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < length; i += 1) {
      int position = (int) Math.floor(Math.random() * possibleChars.length());
      result.append(possibleChars.charAt(position));
    }

    return result.toString();
  }

  /**
   * Gets file extension.
   *
   * @param fileName the file name
   * @return the file extension
   */
  public static String getFileExtension(String fileName) {
    if (fileName == null) {
      return null;
    }
    String[] fileNameParts = fileName.split("\\.");

    return fileNameParts[fileNameParts.length - 1];
  }

  /**
   * Capitalize string.
   *
   * @param str the str
   * @return the string
   */
  public static String capitalize(String str) {
    char[] chars = str.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);

    return String.valueOf(chars);
  }

  /**
   * Update error hash map map.
   *
   * @param errors  the errors
   * @param field   the field
   * @param message the message
   * @return the map
   */
  public static Map<String, List<String>> updateErrorHashMap(
      Map<String, List<String>> errors, String field, String message
  ) {
    if (errors.containsKey(field)) {
      List<String> strings = errors.get(field);
      strings.add(message);

      errors.put(field, strings);
    } else {
      List<String> strings = new ArrayList<>();
      strings.add(message);

      errors.put(field, strings);
    }

    return errors;
  }
}
