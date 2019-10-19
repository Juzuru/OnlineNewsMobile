package com.example.onlinenewsmobile.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.example.onlinenewsmobile.models.NewsDTO;
import com.example.onlinenewsmobile.models.NewsTypeDTO;
import com.example.onlinenewsmobile.views.ListNewsView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    public static final boolean VERTICAL = true;
    public static final boolean HORIZONTAL = false;

    private ListNewsView[] listViewNews;

    public ViewPagerAdapter (AppCompatActivity context, int numberOfPage) {
        listViewNews = new ListNewsView[numberOfPage];
        for (int i = 0; i < numberOfPage; i++) {
            listViewNews[i] = new ListNewsView(context, new ArrayList<NewsDTO>(0));
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

        return listViewNews[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    public void addNews(ArrayList<NewsDTO> list, int position){
        listViewNews[position].addNews(list);
    }

    public void addNews(NewsTypeDTO newsTypeDTO, int position) {
        listViewNews[position].addNews(newsTypeDTO);
    }

    public boolean isPageInit(int position) {
        return listViewNews[position].getCount() != 0;
    }

    public void setOrientation(boolean orientation) {
        for (int i = 0; i < listViewNews.length; i++) {
            listViewNews[i].setOrientation(orientation);
        }
    }
}
