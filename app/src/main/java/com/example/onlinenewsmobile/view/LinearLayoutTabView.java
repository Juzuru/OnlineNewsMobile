package com.example.onlinenewsmobile.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.onlinenewsmobile.MainActivity;
import com.example.onlinenewsmobile.R;

public class LinearLayoutTabView extends LinearLayout {

    private MainActivity context;
    private int tabCount;

    public LinearLayoutTabView(MainActivity context, int tabCount) {
        super(context);
        this.context = context;
        this.tabCount = tabCount;
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater)context.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {
            for (int i = 0; i < tabCount; i++) {
                addView(inflater.inflate(R.layout.tab_item, this, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTabCount() {
        return tabCount;
    }
}
