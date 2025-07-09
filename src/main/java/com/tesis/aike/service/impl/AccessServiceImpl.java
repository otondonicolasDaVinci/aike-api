package com.tesis.aike.service.impl;

import com.tesis.aike.security.JwtTokenUtil;
import com.tesis.aike.service.AccessService;
import com.tesis.aike.service.ReservationService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    private final JwtTokenUtil jwtTokenUtil;
    private final ReservationService reservationService;

    public AccessServiceImpl(JwtTokenUtil jwtTokenUtil, ReservationService reservationService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.reservationService = reservationService;
    }

    @Override
    public boolean verifyAccessToken(String token) {
        try {
            Claims claims = jwtTokenUtil.parse(token);
            Long userId = Long.valueOf(claims.get("s", String.class));

            return reservationService.hasActiveReservation(userId);

        } catch (Exception e) {
            return false;
        }
    }
}
