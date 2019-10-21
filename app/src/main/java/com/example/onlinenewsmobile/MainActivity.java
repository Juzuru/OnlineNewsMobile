package com.example.onlinenewsmobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.onlinenewsmobile.adapters.ViewPagerAdapter;
import com.example.onlinenewsmobile.daos.CategoryDAO;
import com.example.onlinenewsmobile.daos.NewspaperDAO;
import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.models.NewspaperDTO;
import com.example.onlinenewsmobile.transformers.ZoomOutPageTransformer;
import com.example.onlinenewsmobile.views.HeaderView;
import com.example.onlinenewsmobile.views.TabView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int NEWSPAPER_SELECTED = 220;
    private static final int SETTING_CHANGED = 210;
    private static final String HEADER_NAME = "NEWS";

    private ArrayList<CategoryDTO> categories;

    private boolean isLightModel;
    private boolean isVertical;

    private HeaderView headerView;
    private TabView tabView;
    public ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private NewspaperDAO newspaperDAO;
    private CategoryDAO categoryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadSetting();

        newspaperDAO = new NewspaperDAO(this);
        categoryDAO = new CategoryDAO(this);
        new DataSeed().execute();
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

    public void initPagerView(int position) {
        if (!viewPagerAdapter.isPageInit(position)) {
            viewPagerAdapter.addNews(categories.get(position), position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SETTING_CHANGED) {
            if (data.getBooleanExtra("category", false)) {
                categories = categoryDAO.getAllActive();
                tabView.notifyTabChanged(categories);
                viewPagerAdapter = new ViewPagerAdapter(this, data.getIntExtra("activeCategories", 0));
                viewPager.setAdapter(viewPagerAdapter);
            }
            if (isVertical != data.getBooleanExtra("isVertical", false)) {
                isVertical = !isVertical;
                viewPagerAdapter.removeAllNews();
            }
            viewPager.setCurrentItem(0);
            viewPagerAdapter.setOrientation(isVertical);
            saveSetting();
        } else if (resultCode == NEWSPAPER_SELECTED) {
            NewspaperDTO newspaperDTO = (NewspaperDTO)data.getSerializableExtra("newspaper");
            headerView.setTitle(newspaperDTO.getName());
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

    private void initMainView() {
        headerView = new HeaderView(this, true, "24h");
        tabView = new TabView(this, categories);

        setupViewPager();
    }

    private class DataSeed extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (newspaperDAO.getAll().size() == 0) {
                newspaperDAO.seed();
                categories = categoryDAO.seed(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            initMainView();
        }
    }
}
