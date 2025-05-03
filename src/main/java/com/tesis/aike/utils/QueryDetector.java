package com.tesis.aike.utils;

import com.tesis.aike.helper.ConstantValues;

public class QueryDetector {

    public static boolean isAvailableCabinsQuery(String message) {
        if (message == null) return false;
        String lower = message.toLowerCase();
        for (String kw : ConstantValues.QueryKeywords.AVAILABLE_CABINS) {
            if (lower.contains(kw)) return true;
        }
        return false;
    }

    public static boolean isReservationQuery(String message) {
        if (message == null) return false;
        String lower = message.toLowerCase();
        for (String kw : ConstantValues.QueryKeywords.USER_RESERVATIONS) {
            if (lower.contains(kw)) return true;
        }
        if (lower.contains("reservas")) {
            for (String own : ConstantValues.QueryKeywords.RESERVATION_OWNERSHIP) {
                if (lower.contains(own)) return true;
            }
        }
        return false;
    }
}
