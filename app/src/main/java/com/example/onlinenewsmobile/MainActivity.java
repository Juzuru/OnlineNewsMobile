package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.onlinenewsmobile.adapters.ViewPagerAdapter;
import com.example.onlinenewsmobile.daos.CategoryDAO;
import com.example.onlinenewsmobile.models.NewsTypeDTO;
import com.example.onlinenewsmobile.transformers.ZoomOutPageTransformer;
import com.example.onlinenewsmobile.views.HeaderView;
import com.example.onlinenewsmobile.views.TabView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String HEADER_NAME = "NEWS";

    public ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabView tabView;

    private ArrayList<NewsTypeDTO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));

        CategoryDAO categoryDAO = new CategoryDAO(this);
        if (categoryDAO.getAll().size() == 0) {
            categoryDAO.seed();
        }


        new HeaderView(this, true, HEADER_NAME);
        tabView = new TabView(this, categoryDAO.getAll());

        setupViewPager(8);
        initPagerView(0);
    }

    private void setupViewPager(int numberOfPage) {
        viewPager = findViewById(R.id.viewPagerNews);
        viewPagerAdapter = new ViewPagerAdapter(this, numberOfPage);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tabView.notifyPageChanged(position);
                initPagerView(position);
            }
        });

    }

    private void initPagerView(int position) {
        if (!viewPagerAdapter.isPageInit(position)) {
            viewPagerAdapter.addNews(list.get(position), position);
        }
    }
}
