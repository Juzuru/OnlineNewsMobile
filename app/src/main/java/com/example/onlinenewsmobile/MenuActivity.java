package com.example.onlinenewsmobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class MenuActivity extends AppCompatActivity {

    private static final int SETTING_CHANGED = 210;
    private static final int LOGIN_SUCCESS = 200;
    private static final int LOGIN_FAIL = 400;

    private boolean isLightModel;
    private boolean isVertical;
    private String faceBookName;
    private String facebookImageBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_app_setting), Context.MODE_PRIVATE);
        isLightModel = sharedPref.getBoolean("lightMode", true);
        isVertical = sharedPref.getBoolean("largeDisplay", true);
        faceBookName = sharedPref.getString("faceBookName", "");
        facebookImageBase64 = sharedPref.getString("facebookImageBase64", "");

        if (!faceBookName.isEmpty()) {
            loadFacebookUser();
        }
        ((TextView ) findViewById(R.id.textViewChangeDisplay)).setText(isVertical ? "Large" : "Small");
    }

    private void loadFacebookUser() {
        ((TextView) findViewById(R.id.textViewFBName)).setText(faceBookName);
        try {
            byte[] encodeByte = Base64.decode(facebookImageBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            ((ImageView) findViewById(R.id.imageViewFBPhoto)).setImageBitmap(bitmap);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    CallbackManager callbackManager;
    public void clickToLogin(View view) {
        if (faceBookName.isEmpty()) {
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    startLoginActivity(loginResult.getAccessToken().getToken());
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
            LoginManager.getInstance().logIn(this, Arrays.asList("public_profile"));
        }
    }

    private void startLoginActivity(String accessToken) {
        Intent intent = new Intent(this, ProfileFacebookActivity.class);
        intent.putExtra("access_token", accessToken);
        startActivityForResult(intent, LOGIN_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == LOGIN_SUCCESS) {
            faceBookName = data.getStringExtra("faceBookName");
            facebookImageBase64 = data.getStringExtra("facebookImageBase64");
            loadFacebookUser();

            SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_app_setting), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("facebookId", data.getStringExtra("facebookId"));
            editor.putString("faceBookName", faceBookName);
            editor.putString("facebookImageBase64", facebookImageBase64);
            editor.apply();

            //lay id gui server
        } else if (resultCode == SETTING_CHANGED) {
            getIntent().putExtra("category", true);
            getIntent().putExtra("activeCategories", data.getIntExtra("activeCategories", 0));
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
            Intent intent = new Intent(getApplicationContext(), RecentAndMarkedActivity.class);
            startActivity(intent);
    }

    public void clickToChangeReadMode(View view) {
    }

    public void clickToSendEmail(View view) {
    }
}
