package com.example.onlinenewsmobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlinenewsmobile.services.HttpRequestService;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFacebookActivity extends AppCompatActivity {

    private static final int LOGIN_SUCCESS = 200;
    private static final int LOGIN_FAIL = 400;

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_facebook);

        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions("public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginButton.callOnClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {

            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String id = object.getString("id");
                    String imageLink = "https://graph.facebook.com/" + id + "/pictures?type=normal";

                    new UserProfile().execute(first_name, last_name, id, imageLink);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameter = new Bundle();
        parameter.putString("fields", "first_name,last_name,id");
        graphRequest.setParameters(parameter);
        graphRequest.executeAsync();
    }

    private class UserProfile extends AsyncTask<String, Void, Bitmap> {
        private String first_name, last_name, id;

        @Override
        protected Bitmap doInBackground(String... strings) {
            this.first_name = strings[0];
            this.last_name = strings[1];
            this.id = strings[2];
            return HttpRequestService.getImageBitmap(strings[3]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Intent intent = new Intent();
            intent.putExtra("first_name", first_name);
            intent.putExtra("last_name", last_name);
            intent.putExtra("id", id);
            intent.putExtra("image", bitmap);
            setResult(LOGIN_SUCCESS, intent);
            finish();
        }
    }
}
