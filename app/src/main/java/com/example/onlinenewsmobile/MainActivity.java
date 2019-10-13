package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.onlinenewsmobile.transformer.ZoomOutPageTransformer;
import com.example.onlinenewsmobile.view.LinearLayoutTabView;

public class MainActivity extends AppCompatActivity {

    private Resources resources;
    private String[] colors;
    private int deviceWidth;

    private HorizontalScrollView horizontalScrollViewTab;
    private RelativeLayout relativeLayoutStripBottom;
    private ViewPager viewPagerNews;
    private LinearLayoutTabView linearLayoutTabView;

    private TextView previousTextView;
    private LinearLayout.LayoutParams active;
    private LinearLayout.LayoutParams inactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources = getResources();
        colors = resources.getStringArray(R.array.color_hex_code);
        deviceWidth = resources.getDisplayMetrics().widthPixels;

        linearLayoutTabView = new LinearLayoutTabView(this, 20);

        viewPagerNews = findViewById(R.id.viewPagerNews);
        relativeLayoutStripBottom = findViewById(R.id.relativeLayoutStripBottom);
        horizontalScrollViewTab = findViewById(R.id.horizontalScrollViewTab);
        horizontalScrollViewTab.addView(linearLayoutTabView);

        setupTab();
        setupViewPager();
        addContentToViewPager();
    }

    private void setupTab(){
        int tabCount = linearLayoutTabView.getTabCount();

        previousTextView = (TextView) linearLayoutTabView.getChildAt(0);
        previousTextView.setBackgroundColor(Color.parseColor(colors[0]));
        previousTextView.setOnClickListener(onClickListener(0));

        active = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.MATCH_PARENT);
        inactive = new LinearLayout.LayoutParams(300, previousTextView.getLayoutParams().height);
        previousTextView.setLayoutParams(active);

        View view;
        for (int i = 1; i < tabCount; i++) {
            view = linearLayoutTabView.getChildAt(i);
            view.setBackgroundColor(Color.parseColor(colors[i]));
            view.setOnClickListener(onClickListener(i));
        }
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!previousTextView.equals(view)) {
                    view.setLayoutParams(active);
                    previousTextView.setLayoutParams(inactive);
                    previousTextView = (TextView) view;

                    horizontalScrollViewTab.setScrollX(active.width*position - ((deviceWidth - active.width) / 2));
                    relativeLayoutStripBottom.setBackgroundColor(Color.parseColor(colors[position]));

                    viewPagerNews.setCurrentItem(position, false);
                }
            }
        };
    }

    private void setupViewPager() {
        viewPagerNews.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                previousTextView.setLayoutParams(inactive);
                previousTextView = (TextView) linearLayoutTabView.getChildAt(position);
                previousTextView.setLayoutParams(active);

                relativeLayoutStripBottom.setBackgroundColor(Color.parseColor(colors[position]));
                horizontalScrollViewTab.setScrollX(((deviceWidth / 4) - 10)*position - ((deviceWidth - ((deviceWidth / 4) - 10)) / 2));
            }
        });
        viewPagerNews.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private void addContentToViewPager() {

    }
}
