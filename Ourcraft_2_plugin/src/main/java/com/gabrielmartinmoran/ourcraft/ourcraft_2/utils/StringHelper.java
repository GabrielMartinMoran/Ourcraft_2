package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

public class StringHelper {

    public static String center(String text, int totalSize) {
        int realLength = getColorCleanedStringLength(text);
        if (realLength == totalSize) return text;
        if (realLength > totalSize) return text.substring(0, totalSize + (text.length() - realLength));
        int totalPadding = totalSize - realLength;
        int leftPadding = totalPadding / 2;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < leftPadding; i++) {
            result.append(" ");
        }
        result.append(text);
        for (int i = 0; i < totalPadding - leftPadding; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    public static String padRight(String text, int totalSize) {
        int realLength = getColorCleanedStringLength(text);
        if (realLength == totalSize) return text;
        if (realLength > totalSize) return text.substring(0, totalSize + (text.length() - realLength));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < totalSize - realLength; i++) {
            result.append(" ");
        }
        result.append(text);
        return result.toString();
    }

    public static String padLeft(String text, int totalSize) {
        int realLength = getColorCleanedStringLength(text);
        if (realLength == totalSize) return text;
        if (realLength > totalSize) return text.substring(0, totalSize + (text.length() - realLength));
        StringBuilder result = new StringBuilder();
        result.append(text);
        for (int i = 0; i < totalSize - realLength; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    private static int getColorCleanedStringLength(String text) {
        // Ignoramos los ChatColors
        return text.replaceAll("§.", "").length();
    }
}
