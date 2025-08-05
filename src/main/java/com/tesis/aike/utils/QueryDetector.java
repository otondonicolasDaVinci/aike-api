package com.tesis.aike.utils;

import com.tesis.aike.helper.ConstantValues;

import java.util.Arrays;

public class QueryDetector {

    public static boolean isAvailableCabinsQuery(String message) {
        return containsAny(message, ConstantValues.QueryKeywords.AVAILABLE_CABINS);
    }

    public static boolean isReservationQuery(String message) {
        if (!containsAny(message, ConstantValues.QueryKeywords.USER_RESERVATIONS)) return false;
        return containsAny(message, ConstantValues.QueryKeywords.RESERVATION_OWNERSHIP) || message.toLowerCase().contains("reservas");
    }

    public static boolean isAllReservationsQuery(String message) {
        return containsAny(message, ConstantValues.QueryKeywords.ALL_RESERVATIONS);
    }

    public static boolean isSensitiveDataQuery(String message) {
        return containsAny(message, ConstantValues.QueryKeywords.SENSITIVE_DATA)
                && !containsAny(message, ConstantValues.QueryKeywords.RESERVATION_OWNERSHIP);
    }

    public static boolean isProductsQuery(String message) {
        return containsAny(message, ConstantValues.QueryKeywords.PRODUCTS);
    }

    public static boolean isMyCabinQuery(String message) {
        return containsAny(message, ConstantValues.QueryKeywords.MY_CABIN);
    }

    public static boolean isTourQuery(String message) {
        return containsAny(message, ConstantValues.QueryKeywords.TOURS_AND_ACTIVITIES);
    }

    private static boolean containsAny(String text, String[] keywords) {
        if (text == null) return false;
        String lower = text.toLowerCase();
        return Arrays.stream(keywords).anyMatch(lower::contains);
    }
}
