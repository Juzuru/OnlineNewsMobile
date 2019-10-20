package com.example.onlinenewsmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinenewsmobile.services.HttpRequestService;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLEncoder;

public class ProfileFacebookActivity extends AppCompatActivity {

    private static final String UPLOAD_FACEBOOK_PROFILE_URL = "http://api.etutor.top:8021/NewsMobile/api/facebook-account";

    private static final int LOGIN_SUCCESS = 200;
    private static final int LOGIN_FAIL = 400;

    private String id, name, imageBase64, first_name;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_facebook);

        new LoadUserProfile().execute(getIntent().getStringExtra("access_token"));
    }

    public void clickToAcept(View view) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        imageBase64 = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);

        new UploadUserProfile().execute();

        Intent intent = getIntent();
        intent.putExtra("facebookId", id);
        intent.putExtra("faceBookName", name);
        intent.putExtra("facebookImageBase64", imageBase64);
        setResult(LOGIN_SUCCESS, intent);
        finish();
    }

    public void clickToCancel(View view) {
        finish();
    }

    private class UploadUserProfile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONObject jo = new JSONObject();
                jo.put("facebookId", id);
                jo.put("name", name);
                HttpRequestService.executePost(new URL(UPLOAD_FACEBOOK_PROFILE_URL), jo.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class LoadUserProfile extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = "https://graph.facebook.com/v4.0/me?access_token=" +
                    strings[0] +
                    "&fields=id,name,first_name&debug=all&format=json";
            String content = HttpRequestService.getContent(url);

            try {
                JSONObject jo = new JSONObject(content);
                id = jo.getString("id");
                name = jo.getString("name");
                first_name = jo.getString("first_name");
                String imageLink = "https://graph.facebook.com/" + id + "/picture?type=normal";

                return HttpRequestService.getImageBitmap(imageLink);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageBitmap = bitmap;
            ((Button) findViewById(R.id.btAcept)).setText("Continue as " + first_name);
            ((ImageView) findViewById(R.id.faceBookImage)).setImageBitmap(bitmap);
        }
    }

//    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            if (currentAccessToken == null) {
//                String s= "";
//                s += "dawdfefsef";
//                String qwewq = s.substring(2);
//            } else {
//                loadUserProfile(currentAccessToken);
//            }
//        }
//    };
//
//    private void loadUserProfile(AccessToken newAccessToken) {
//        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                try {
//                    String name = object.getString("name");
//                    String id = object.getString("id");
//                    String imageLink = "https://graph.facebook.com/" + id + "/pictures?type=normal";
//
//                    new UserProfile().execute(name, id, imageLink);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        Bundle parameter = new Bundle();
//        parameter.putString("fields", "id,name");
//        parameter.putString("accesstoken", newAccessToken.getToken());
//        graphRequest.setParameters(parameter);
//        graphRequest.executeAsync();
//    }
//
//    private class UserProfile extends AsyncTask<String, Void, Bitmap> {
//        private String name, id, imageLink;
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            this.name = strings[0];
//            this.id = strings[1];
//            this.imageLink = strings[2];
//
//            try {
//                JSONObject jo = new JSONObject();
//                jo.put("facebookId", strings[1]);
//                jo.put("name", strings[0]);
//                HttpRequestService.executePost(new URL(FB_URL), jo.toString());
//
//                return HttpRequestService.getImageBitmap(strings[2]);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//            //return HttpRequestService.getImageBitmap(strings[3]);
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            Intent intent = getIntent();
//            if (bitmap != null) {
//                ((TextView) findViewById(R.id.faceBookName)).setText(name);
//                ((ImageView) findViewById(R.id.faceBookImage)).setImageBitmap(bitmap);
//
//                intent.putExtra("name", name);
//                intent.putExtra("id", id);
//                intent.putExtra("imageLink", imageLink);
//                intent.putExtra("imageBitmap", bitmap);
//                setResult(LOGIN_SUCCESS, intent);
//            } else {
//                setResult(LOGIN_FAIL, intent);
//            }
//            finish();
//        }
//    }
}
