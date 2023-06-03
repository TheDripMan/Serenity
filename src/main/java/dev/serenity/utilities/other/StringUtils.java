package dev.serenity.utilities.other;

public class StringUtils {
    public static String findLongestString(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        String longestString = array[0];

        for (int i = 1; i < array.length; i++) {
            String currentString = array[i];
            if (currentString.length() > longestString.length()) {
                longestString = currentString;
            }
        }

        return longestString;
    }
}
