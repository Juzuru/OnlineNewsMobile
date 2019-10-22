package com.example.onlinenewsmobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.onlinenewsmobile.adapters.ViewPagerAdapter;
import com.example.onlinenewsmobile.daos.CategoryDAO;
import com.example.onlinenewsmobile.daos.NewspaperDAO;
import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.models.NewspaperDTO;
import com.example.onlinenewsmobile.services.HttpRequestService;
import com.example.onlinenewsmobile.transformers.ZoomOutPageTransformer;
import com.example.onlinenewsmobile.views.HeaderView;
import com.example.onlinenewsmobile.views.TabView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String NEWSPAPER_URL = "http://api.etutor.top:8021/NewsMobile/api/newspaper";
    private static final String CATEGORY_URL = "http://api.etutor.top:8021/NewsMobile/api/category";

    private static final int NEWSPAPER_SELECTED = 220;
    private static final int SETTING_CHANGED = 210;

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
            try {
                NewspaperDTO newspaperDTO = (NewspaperDTO)data.getSerializableExtra("newspaper");
                headerView.setTitle(newspaperDTO.getName());
                categories = categoryDAO.getActiveByNewspaperId(newspaperDTO.getId());
                for (int i = 0; i < categories.size(); i++) {
                    categories.get(i).setNewspaper(newspaperDTO.getName());
                }
                ((LinearLayout) findViewById(R.id.linearLayoutMain)).removeViewAt(1);
                tabView = new TabView(this, categories);
                viewPagerAdapter = new ViewPagerAdapter(this, categories.size());
                viewPagerAdapter.setOrientation(isVertical);
                viewPager.setAdapter(viewPagerAdapter);
                initPagerView(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void initMainView(String headerName) {
        headerView = new HeaderView(this, headerName);
        tabView = new TabView(this, categories);

        setupViewPager();
    }

    private class DataSeed extends AsyncTask<Void, Void, Boolean> {
        private ArrayList<NewspaperDTO> newspaperDTOS;
        JSONArray newspaperJA, categoryJA;

        @Override
        protected Boolean doInBackground(Void... voids) {
            if ((newspaperDTOS = newspaperDAO.getAll()).size() == 0) {
                try {
                    newspaperJA = new JSONArray(HttpRequestService.executeGet(new URL(NEWSPAPER_URL)));
                    categoryJA = new JSONArray(HttpRequestService.executeGet(new URL(CATEGORY_URL)));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b) {
                JSONObject jo;
                NewspaperDTO newspaperDTO;
                for (int i = 0; i < newspaperJA.length(); i++) {
                    try {
                        newspaperDTO = new NewspaperDTO();
                        jo = newspaperJA.getJSONObject(i);
                        newspaperDTO.setName(jo.getString("name"));
                        newspaperDTO.setServerID(jo.getInt("id"));
                        newspaperDTO.setImageBase64("");
                        newspaperDAO.create(newspaperDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                newspaperDTOS = newspaperDAO.getAll();

                CategoryDTO categoryDTO;
                for (int i = 0; i < categoryJA.length(); i++) {
                    try {
                        categoryDTO = new CategoryDTO();
                        jo = categoryJA.getJSONObject(i);
                        categoryDTO.setRssLink(jo.getString("rss"));
                        categoryDTO.setName(jo.getString("name"));
                        int newspaperServerId = jo.getInt("newspaperId");
                        for (int j = 0; j < newspaperDTOS.size(); j++) {
                            if (newspaperServerId == newspaperDTOS.get(j).getServerID()) {
                                categoryDTO.setNewspaperId(newspaperDTOS.get(j).getId());
                                break;
                            }
                        }
                        categoryDAO.create(categoryDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            categories = categoryDAO.getActiveByNewspaperId(newspaperDTOS.get(0).getId());
            for (int i = 0; i < categories.size(); i++) {
                categories.get(i).setNewspaper(newspaperDTOS.get(0).getName());
            }
            initMainView(newspaperDTOS.get(0).getName());
        }
    }
}
