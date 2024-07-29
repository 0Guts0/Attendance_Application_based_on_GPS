package com.example.attendanceapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerateQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to go back to main page
                Intent intent = new Intent(GenerateQRActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView qrCodeImageView = findViewById(R.id.qr_code_image);
        TextView qrCodeTextView = findViewById(R.id.qr_code_text);

        String qrCodeText = "Student ID: 2711683, Student Name: ZhanLiu";
        qrCodeTextView.setText(qrCodeText);

        QRCodeWriter writer = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(writer.encode(qrCodeText, BarcodeFormat.QR_CODE, 512, 512));
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private Bitmap toBitmap(com.google.zxing.common.BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return bitmap;
    }
}
