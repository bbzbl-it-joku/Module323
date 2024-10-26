package com.example.m323.utils;

import java.text.DecimalFormat;

/**
 * Utility class for formatting console tables and visualizations.
 * This class consolidates common formatting methods used across different functions.
 * 
 * @author Joshua Kunz
 * @version 1.0
 * @since 10.10.2024
 */
public class TableFormatterUtils {
    private static final String BAR_CHAR = "■";
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");
    
    /**
     * Creates a visualization bar of specified width.
     * 
     * @param value Current value to visualize
     * @param maxValue Maximum value in the dataset
     * @param barWidth Width of the bar in characters
     * @return Formatted bar string with proper padding
     */
    public static String createBar(double value, double maxValue, int barWidth) {
        int barLength = (int) (value * barWidth / maxValue);
        return String.format("%-" + barWidth + "s", BAR_CHAR.repeat(barLength));
    }

    /**
     * Creates a visualization bar for BigDecimal values.
     * 
     * @param value Current value to visualize
     * @param maxValue Maximum value in the dataset
     * @param barWidth Width of the bar in characters
     * @return Formatted bar string with proper padding
     */
    public static String createBar(java.math.BigDecimal value, java.math.BigDecimal maxValue, int barWidth) {
        int barLength = value.multiply(java.math.BigDecimal.valueOf(barWidth))
            .divide(maxValue, java.math.RoundingMode.HALF_UP)
            .intValue();
        return String.format("%-" + barWidth + "s", BAR_CHAR.repeat(barLength));
    }

    /**
     * Formats a numeric value with thousands separator and two decimal places.
     * 
     * @param value The value to format
     * @return Formatted string
     */
    public static String formatNumber(double value) {
        return df.format(value);
    }

    /**
     * Formats a BigDecimal value with thousands separator and two decimal places.
     * 
     * @param value The value to format
     * @return Formatted string
     */
    public static String formatNumber(java.math.BigDecimal value) {
        return df.format(value);
    }

    /**
     * Creates a horizontal border line with specified width.
     * 
     * @param width Total width of the line
     * @param style Character to use for the line (e.g., '═', '─')
     * @return Formatted border line
     */
    public static String createBorderLine(int width, char style) {
        return style + String.valueOf(style).repeat(width - 2) + style;
    }

    /**
     * Creates a separator line with multiple columns.
     * 
     * @param widths Array of column widths
     * @param style Character to use for the line
     * @param connector Character to use between columns
     * @return Formatted separator line
     */
    public static String createColumnSeparator(int[] widths, char style, char connector) {
        StringBuilder sb = new StringBuilder("║");
        for (int i = 0; i < widths.length; i++) {
            sb.append(String.valueOf(style).repeat(widths[i]));
            sb.append(i < widths.length - 1 ? connector : "║");
        }
        return sb.toString();
    }

    /**
     * Centers text within a specified width.
     * 
     * @param text Text to center
     * @param width Total width to center within
     * @return Centered text string
     */
    public static String centerText(String text, int width) {
        int padding = width - text.length();
        int leftPad = padding / 2;
        int rightPad = padding - leftPad;
        return " ".repeat(leftPad) + text + " ".repeat(rightPad);
    }
}