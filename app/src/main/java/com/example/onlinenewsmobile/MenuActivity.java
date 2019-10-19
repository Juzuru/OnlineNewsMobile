package com.example.onlinenewsmobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private static final int LOGIN_SUCCESS = 200;
    private static final int LOGIN_FAIL = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void clickToLogin(View view) {
        startActivityForResult(new Intent(this, ProfileFacebookActivity.class), LOGIN_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOGIN_SUCCESS) {
            ((ImageView) findViewById(R.id.imageViewFBPhoto)).setImageBitmap((Bitmap) data.getParcelableExtra("image"));
            ((TextView) findViewById(R.id.textViewFBName)).setText(data.getStringExtra("first_name" + " " + data.getStringExtra("last_name")));
            //lay id gui server
        }
    }

    public void clicktoCategory(View view) {
        startActivity(new Intent(this, CategoryActivity.class));
    }
}
