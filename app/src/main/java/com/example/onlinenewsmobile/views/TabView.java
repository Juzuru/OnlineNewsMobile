package com.example.onlinenewsmobile.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.onlinenewsmobile.MainActivity;
import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.daos.CategoryDAO;
import com.example.onlinenewsmobile.models.CategoryDTO;

import java.util.ArrayList;

public class TabView {

    private static final int TAB_HEIGHT = 20;

    private MainActivity context;
    private String[] colors;
    private int deviceWidth;

    private TextView previousTab;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private HorizontalScrollView horizontalScrollView;

    private boolean isTabTouch = false;
    private TextView[] textViews;

    public TabView(MainActivity context, ArrayList<CategoryDTO> categories) {
        this.context = context;
        Resources resources = context.getResources();
        textViews = new TextView[categories.size()];
        colors = resources.getStringArray(R.array.color_hex_code);
        deviceWidth = resources.getDisplayMetrics().widthPixels;

        initView();
        setupTab(categories);
    }

    private void initView() {
        linearLayout = context.findViewById(R.id.linearLayoutMain);
        context.getLayoutInflater().inflate(R.layout.tab, linearLayout, true);
        FrameLayout frameLayout = context.findViewById(R.id.frameLayoutTab);
        relativeLayout = frameLayout.findViewById(R.id.relativeLayoutStripBottom);
        horizontalScrollView = frameLayout.findViewById(R.id.horizontalScrollViewTab);
        linearLayout = frameLayout.findViewById(R.id.linearLayoutTab);
    }

    private void setupTab(ArrayList<CategoryDTO> categories) {
        LayoutInflater inflater = (LayoutInflater)context.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        textViews[0] = (TextView) inflater.inflate(R.layout.tab_item, linearLayout, false);
        ((GradientDrawable) textViews[0].getBackground()).setColor(Color.parseColor(colors[0]));
        textViews[0].setText(categories.get(0).getName());
        textViews[0].setOnClickListener(onClickListener(0));

        previousTab = textViews[0];
        ViewGroup.LayoutParams layoutParams = previousTab.getLayoutParams();
        layoutParams.height += TAB_HEIGHT;
        previousTab.setLayoutParams(layoutParams);
        linearLayout.addView(previousTab);

        for (int i = 1, c = 1, p = 1; i < categories.size(); i++) {
            textViews[i] = (TextView) inflater.inflate(R.layout.tab_item, linearLayout, false);
            textViews[i].setText(categories.get(i).getName());
            if (categories.get(i).isVisible()) {
                textViews[i].setOnClickListener(onClickListener(p));
                ((GradientDrawable) textViews[i].getBackground()).setColor(Color.parseColor(colors[c++]));
                linearLayout.addView(textViews[p++]);
            }
            if (c == colors.length) c = 0;
        }
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTabTouch = true;
                if (!previousTab.equals(view)) {
                    context.viewPager.setCurrentItem(position, false);

                    ViewGroup.LayoutParams layoutParams = previousTab.getLayoutParams();
                    layoutParams.height -= TAB_HEIGHT;
                    previousTab.setLayoutParams(layoutParams);
                    previousTab = (TextView) view;
                    layoutParams = previousTab.getLayoutParams();
                    layoutParams.height += TAB_HEIGHT;
                    previousTab.setLayoutParams(layoutParams);

                    horizontalScrollView.setScrollX(view.getLeft() - ((deviceWidth - view.getWidth()) / 2));
                    relativeLayout.setBackgroundColor(Color.parseColor(colors[position]));
                }
                isTabTouch = false;
            }
        };
    }

    public void notifyPageChanged(int position) {
        if (!isTabTouch) {
            ViewGroup.LayoutParams layoutParams = previousTab.getLayoutParams();
            layoutParams.height -= TAB_HEIGHT;
            previousTab.setLayoutParams(layoutParams);
            previousTab = (TextView) linearLayout.getChildAt(position);
            layoutParams = previousTab.getLayoutParams();
            layoutParams.height += TAB_HEIGHT;
            previousTab.setLayoutParams(layoutParams);

            relativeLayout.setBackgroundColor(Color.parseColor(colors[position]));
            horizontalScrollView.setScrollX(previousTab.getLeft() - ((deviceWidth - previousTab.getWidth()) / 2));
        }
    }

    public void notifyTabChanged(ArrayList<CategoryDTO> categories) {
        linearLayout.removeAllViews();
        setupTab(categories);
    }
}
