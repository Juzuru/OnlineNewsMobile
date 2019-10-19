package com.example.onlinenewsmobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlinenewsmobile.adapters.ViewPagerAdapter;
import com.example.onlinenewsmobile.daos.CategoryDAO;
import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.models.NewsDTO;
import com.example.onlinenewsmobile.models.NewsTypeDTO;
import com.example.onlinenewsmobile.transformers.ZoomOutPageTransformer;
import com.example.onlinenewsmobile.views.HeaderView;
import com.example.onlinenewsmobile.views.ListNewsView;
import com.example.onlinenewsmobile.views.TabView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int SETTING_CHANGED = 210;
    private static final String HEADER_NAME = "NEWS";

    private ArrayList<CategoryDTO> categories;

    private boolean isLightModel;
    private boolean isVertical;

    public ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabView tabView;

    private ArrayList<NewsTypeDTO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadSetting();

        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
        list.add(new NewsTypeDTO("Bong da", "Dan tri", ""));
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
        categories = categoryDAO.getAllActive();

        new HeaderView(this, true, HEADER_NAME);
        tabView = new TabView(this, categories);

        setupViewPager();
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.viewPagerNews);
        viewPagerAdapter = new ViewPagerAdapter(this, categories.size());
        viewPagerAdapter.setOrientation(isVertical);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tabView.notifyPageChanged(position);
                initPagerView(position);
            }
        });
        initPagerView(0);
    }

    private void initPagerView(int position) {
        if (!viewPagerAdapter.isPageInit(position)) {
            viewPagerAdapter.addNews(list.get(position), position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SETTING_CHANGED) {
            if (data.getBooleanExtra("category", false)) {
                categories = new CategoryDAO(this).getAllActive();
                tabView.notifyTabChanged(categories);
                viewPagerAdapter = new ViewPagerAdapter(this, data.getIntExtra("activeCategories", 0));
                viewPager.setAdapter(viewPagerAdapter);
            }
            if (isVertical != data.getBooleanExtra("isVertical", false)) {
                isVertical = !isVertical;
                viewPagerAdapter.removeAllNews();
            }
            viewPager.setCurrentItem(0);
            initPagerView(0);
            viewPagerAdapter.setOrientation(isVertical);
            saveSetting();
        }
    }

    private void loadSetting() {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_app_setting), Context.MODE_PRIVATE);
        isLightModel = sharedPref.getBoolean("lightMode", true);
        isVertical = sharedPref.getBoolean("largeDisplay", true);
    }

    private void saveSetting() {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_app_setting), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("lightMode", isLightModel);
        editor.putBoolean("largeDisplay", isVertical);
        editor.apply();
    }
}
