package com.example.onlinenewsmobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private static final int SETTING_CHANGED = 210;
    private static final int LOGIN_SUCCESS = 200;
    private static final int LOGIN_FAIL = 400;

    private boolean isLightModel;
    private boolean isVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_app_setting), Context.MODE_PRIVATE);
        isLightModel = sharedPref.getBoolean("lightMode", true);
        isVertical = sharedPref.getBoolean("largeDisplay", true);

        ((TextView ) findViewById(R.id.textViewChangeDisplay)).setText(isVertical ? "Large" : "Small");
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
        } else if (resultCode == SETTING_CHANGED) {
            getIntent().putExtra("category", true);
            getIntent().putExtra("activeCategories", data.getIntExtra("activeCategories", 0));
        }
    }

    public void clicktoCategory(View view) {
        startActivityForResult(new Intent(this, CategoryActivity.class), SETTING_CHANGED);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("isVertical", isVertical);
        setResult(SETTING_CHANGED, intent);
        finish();
    }

    public void clickToBack(View view) {
        onBackPressed();
    }

    public void clickToChangeDisplay(View view) {
        isVertical = !isVertical;
        ((TextView ) findViewById(R.id.textViewChangeDisplay)).setText(isVertical ? "Large" : "Small");
    }

    public void clickToGoToRecent(View view) {

    }

    public void clickToChangeReadMode(View view) {
    }

    public void clickToSendEmail(View view) {
    }
}
