package com.tesis.aike.controller;

import com.google.zxing.WriterException;
import com.tesis.aike.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @Autowired
    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> generateQRCode(@PathVariable Long userId) throws IOException, WriterException {
        String base64 = qrCodeService.generateQRCode(userId);
        return ResponseEntity.ok(base64);
    }
}
