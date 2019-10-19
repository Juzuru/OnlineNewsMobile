package com.example.onlinenewsmobile.views.search;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.adapters.SearchNewsAdapter;
import com.example.onlinenewsmobile.models.KA.SearchNewsDTO;
import com.example.onlinenewsmobile.retrofit2.APIUtils;
import com.example.onlinenewsmobile.retrofit2.client.NewsClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchNewsActivity extends AppCompatActivity {
    List<SearchNewsDTO> listResultNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.search_activity_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showSearchResult(newText);
                return true;
            }
        });

    }

    private void showSearchResult(String newsText) {
        //Call api get result
        NewsClient newsClient = APIUtils.getNewsClient();
        Call<List<SearchNewsDTO>> call = newsClient.findByLikeTitle(newsText);
        call.enqueue(new Callback<List<SearchNewsDTO>>() {
            @Override
            public void onResponse(Call<List<SearchNewsDTO>> call, Response<List<SearchNewsDTO>> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    listResultNews = response.body();
                    TextView txtNothing = findViewById(R.id.txtNoResult);
                    LinearLayout linearLayout = findViewById(R.id.lnResult);
                    if (listResultNews.isEmpty()) {
                        txtNothing.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.INVISIBLE);
                    } else {
                        linearLayout.setVisibility(View.VISIBLE);
                        txtNothing.setVisibility(View.INVISIBLE);
                        SearchNewsAdapter searchNewsAdapter = new SearchNewsAdapter(listResultNews, getApplicationContext());
                        RecyclerView recyclerView = findViewById(R.id.recycleview_searchResult);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                        recyclerView.setAdapter(searchNewsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SearchNewsDTO>> call, Throwable t) {

            }
        });
        //listResultNews = ????
//        listResultNews = new ArrayList<>();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
