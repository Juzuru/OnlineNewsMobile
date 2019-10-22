package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.onlinenewsmobile.adapters.suggesst.SuggestedNewsAdapter;
import com.example.onlinenewsmobile.models.KA.TopNewsHighReadTimes;
import com.example.onlinenewsmobile.retrofit2.APIUtils;
import com.example.onlinenewsmobile.retrofit2.client.NewsClient;
import com.example.onlinenewsmobile.services.DocumentService;
import com.example.onlinenewsmobile.services.HttpRequestService;
import com.example.onlinenewsmobile.services.NewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsDetailActivity extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent intent = getIntent();
        ((TextView) findViewById(R.id.textViewCategory)).setText(intent.getStringExtra("newTypeName"));
        container = findViewById(R.id.linearNewsContainer);
        new NewsReader().execute("24h", intent.getStringExtra("link"));

        //Mai thêm

        getTopNewsHighReadTimes();
    }

    private void addContent(String content, float size) {
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setTextSize(size);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setPadding(30, 30, 30, 30);
        container.addView(textView);
    }

    private void addImage(Bitmap bitmap) {
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        container.addView(imageView);
    }

    private void addAuthor(String content, float size) {
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setTextSize(size);
        textView.setGravity(Gravity.END);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setPadding(30, 30, 30, 30);
        container.addView(textView);
    }

    public void clickToBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class NewsReader extends AsyncTask<String , Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(String... strings) {
            try {
                switch (strings[0]) {
                    case "24h":
                        NewsService newsService = new NewsService();
                        return newsService.read24h(DocumentService.parseHtml(strings[1]));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> objects) {
            try {
                String content;
                for (int i = 0; i < objects.size(); i++) {
                    if (objects.get(i) instanceof Bitmap) {
                        addImage((Bitmap) objects.get(i));
                    } else {
                        content = (String)objects.get(i);
                        if (!content.isEmpty()) {
                            switch (content.substring(0, 5)){
                                case "<hea>":
                                    addContent(content.substring(5), 24);
                                    break;
                                case "<des>":
                                    addContent(content.substring(5), 22);
                                    break;
                                case "<str>":
                                    addContent(content.substring(5), 20);
                                    break;
                                case "<tim>":
                                    addContent(content.substring(5), 18);
                                    break;
                                case "<aut>":
                                    addAuthor(content.substring(5), 18);
                                    break;
                                default:
                                    addContent(content, 18);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                new RecentRead().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class RecentRead extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            //container.addView(view);

        }
    }

    private void getTopNewsHighReadTimes() {
        NewsClient newsClient = APIUtils.getNewsClient();
        Call<List<TopNewsHighReadTimes>> listCall = newsClient.getTopNewsHighReadTimes();
        listCall.enqueue(new Callback<List<TopNewsHighReadTimes>>() {
            @Override
            public void onResponse(Call<List<TopNewsHighReadTimes>> call, Response<List<TopNewsHighReadTimes>> response) {
                if (response.isSuccessful()) {
                    List<TopNewsHighReadTimes> topNewsHighReadTimesList = new ArrayList<>();
                    topNewsHighReadTimesList = response.body();
                    RecyclerView recyclerView = findViewById(R.id.recycler_view_suggesst);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    SuggestedNewsAdapter suggestedNewsAdapter = new SuggestedNewsAdapter(topNewsHighReadTimesList, getApplicationContext());
                    recyclerView.setAdapter(suggestedNewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<TopNewsHighReadTimes>> call, Throwable t) {

            }
        });
    }
}
