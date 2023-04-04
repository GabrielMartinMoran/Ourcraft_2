package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

public class ManaTextFormatter {

    public static String formatManaAmount(int amount) {
        if (amount < 1000) return String.format("%smL", amount);
        return String.format("%.2fL", amount / 1000f);
    }
}
