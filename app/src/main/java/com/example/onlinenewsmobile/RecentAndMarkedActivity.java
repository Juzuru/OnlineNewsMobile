package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.onlinenewsmobile.adapter.ViewPagerAdapter;

public class RecentAndMarkedActivity extends AppCompatActivity {

    private static final int TAB_HEIGHT = 20;

    private Resources resources;
    private int deviceWidth;

    private TextView activeTextView;
    private RelativeLayout relativeLayoutStripBottom;
    private ViewPager viewPager;

    private ViewGroup.LayoutParams active;
    private ViewGroup.LayoutParams inactive;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_and_marked);

        resources = getResources();
        deviceWidth = resources.getDisplayMetrics().widthPixels;

        activeTextView = findViewById(R.id.textViewRecent);
        relativeLayoutStripBottom = findViewById(R.id.relativeLayoutStripBottom);
        viewPager = findViewById(R.id.viewPager);

        active = inactive = activeTextView.getLayoutParams();
        active.width = deviceWidth /2;
        active.height += TAB_HEIGHT;
        activeTextView.setLayoutParams(active);

        activeTextView.setOnClickListener(onClickListener());
        findViewById(R.id.imageViewBookMark).setOnClickListener(onClickListener());

        adapter = new ViewPagerAdapter(this, 2);
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activeTextView.equals(v)) {
                    v.setLayoutParams(active);
                    activeTextView.setLayoutParams(inactive);
                    activeTextView = (TextView) v;

                    if (activeTextView.getText().toString().equals("Recent")) {
                        relativeLayoutStripBottom.setBackgroundResource(R.color.colorTeal);

                    } else {
                        relativeLayoutStripBottom.setBackgroundResource(R.color.colorOrimson);
                    }
                }
            }
        };
    }
}
