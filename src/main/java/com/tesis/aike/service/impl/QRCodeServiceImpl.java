package com.tesis.aike.service.impl;

import com.google.zxing.WriterException;
import com.tesis.aike.security.JwtTokenUtil;
import com.tesis.aike.service.QRCodeService;
import com.tesis.aike.service.ReservationService;
import com.tesis.aike.utils.QRGenerator;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.util.Date;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private final ReservationService reservationService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public QRCodeServiceImpl(ReservationService reservationService, JwtTokenUtil jwtTokenUtil) {
        this.reservationService = reservationService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public String generateQRCode(Long userId) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long authenticatedId = Long.valueOf(auth.getPrincipal().toString());

        if (!authenticatedId.equals(userId)) {
            throw new AccessDeniedException("No tienes permiso para generar el QR de otro usuario");
        }

        if (!reservationService.hasActiveReservation(userId)) {
            throw new IllegalStateException("El usuario no tiene una reserva activa");
        }

        try {
            Instant now = Instant.now();
            String jwt = Jwts.builder()
                    .setSubject(userId.toString())
                    .claim("role", "CLIENT")
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(5, ChronoUnit.SECONDS)))
                    .signWith(jwtTokenUtil.getKey())
                    .compact();

            ByteArrayOutputStream stream = QRGenerator.generateToStream(jwt, 300, 300);
            return Base64.getEncoder().encodeToString(stream.toByteArray());

        } catch (IOException | WriterException e) {
            throw new IllegalStateException("No se pudo generar el código QR", e);
        }
    }


}
