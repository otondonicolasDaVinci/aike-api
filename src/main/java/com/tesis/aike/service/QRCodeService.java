package com.tesis.aike.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeService {
    String generateQRCode(Long userId) throws IOException, WriterException;
}
