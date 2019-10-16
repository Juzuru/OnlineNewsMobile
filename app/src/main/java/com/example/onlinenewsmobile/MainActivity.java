package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.onlinenewsmobile.adapter.ViewPagerAdapter;
import com.example.onlinenewsmobile.models.NewsTypeDTO;
import com.example.onlinenewsmobile.transformers.ZoomOutPageTransformer;
import com.example.onlinenewsmobile.views.HeaderView;
import com.example.onlinenewsmobile.views.TabView;

public class MainActivity extends AppCompatActivity {

    private static final String HEADER_NAME = "TIN HOT";

    public ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private TabView tabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new HeaderView(this, true, HEADER_NAME);
        tabView = new TabView(this, new String[]{"Bong da", "Thoi su", "Thoi trang", "thoi tiet",
                "Bong da", "Thoi su", "Thoi trang", "thoi tiet",
                "Bong da", "Thoi su", "Thoi trang", "thoi tiet",
                "Bong da", "Thoi su", "Thoi trang", "thoi tiet",
                "Bong da", "Thoi su", "Thoi trang", "thoi tiet"});

        setupViewPager();
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.viewPagerNews);
        viewPagerAdapter = new ViewPagerAdapter(this, new NewsTypeDTO[]{new NewsTypeDTO("Bong da", "San co 24h", ""),
                new NewsTypeDTO("Bong da", "San co 24h", ""),
                new NewsTypeDTO("Bong da", "San co 24h", "")});
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.getListViewNews(0).initView();
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                viewPagerAdapter.getListViewNews(position).initView();
                tabView.notifyPageChanged(position);
            }
        });
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }
}
