package com.tesis.aike.exception;

import com.tesis.aike.helper.ConstantValues;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CabinAlreadyReservedException extends RuntimeException {
    private final List<Map<String, LocalDate>> reservations;

    public CabinAlreadyReservedException(List<Map<String, LocalDate>> reservations) {
        super(ConstantValues.ReservationService.CABIN_NOT_AVAILABLE);
        this.reservations = reservations;
    }

    public List<Map<String, LocalDate>> getReservations() {
        return reservations;
    }
}
