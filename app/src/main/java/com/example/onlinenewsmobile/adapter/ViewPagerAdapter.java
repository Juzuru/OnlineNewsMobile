package com.example.onlinenewsmobile.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.onlinenewsmobile.MainActivity;
import com.example.onlinenewsmobile.models.NewsTypeDTO;
import com.example.onlinenewsmobile.views.ListNewsView;

public class ViewPagerAdapter extends PagerAdapter {

    private ListNewsView[] listViewNews;

    public ViewPagerAdapter (MainActivity context, @NonNull NewsTypeDTO[] newsTypeDTOS) {
        listViewNews = new ListNewsView[newsTypeDTOS.length];
        for (int i = 0; i < newsTypeDTOS.length; i++) {
            listViewNews[i] = new ListNewsView(context, newsTypeDTOS[i]);
        }
    }

    @Override
    public int getCount() {
        return listViewNews.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        if (listViewNews[position].getParent() == null) {
            container.addView(listViewNews[position]);
        }

        //listViewNews[position].initView();

        return listViewNews[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    public ListNewsView getListViewNews(int position) {
        return listViewNews[position];
    }
}
