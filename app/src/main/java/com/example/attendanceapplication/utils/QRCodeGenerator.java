package com.example.attendanceapplication.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static Bitmap generateQRCode(String data, int width, int height) {
        try {
            // Creating a QRCodeWriter instance
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Setting the encoding parameters
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // character encoding
            hints.put(EncodeHintType.MARGIN, 1); // Margins, minimum 0
            // Generate BitMatrix (matrix representation of QR code)
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            // Initialize Bitmap
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = Color.BLACK;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }
            }

            // Generate Bitmap from pixel array
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}