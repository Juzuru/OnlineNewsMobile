package com.example.onlinenewsmobile.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ConverterService {
    public String fromBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
    }

    public Bitmap fromStringToBitmap(String string) {
        byte[] encodeByte = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
    }
}
