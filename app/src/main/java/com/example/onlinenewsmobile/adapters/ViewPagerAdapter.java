package com.example.onlinenewsmobile.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.models.NewsDTO;
import com.example.onlinenewsmobile.views.ListNewsView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    public ListNewsView[] listViewNews;

    public ViewPagerAdapter (AppCompatActivity context, int numberOfPage) {
        listViewNews = new ListNewsView[numberOfPage];
        String[] colors = context.getResources().getStringArray(R.array.color_hex_code);

        for (int i = 0, j = 0; i < numberOfPage; i++, j++) {
            listViewNews[i] = new ListNewsView(context, new ArrayList<NewsDTO>(0), colors[j]);
            if (j == colors.length - 1) j = -1;
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

    public void addNews(CategoryDTO categoryDTO, int position) {
        listViewNews[position].addNews(categoryDTO);
    }

    public void removeAllNews(){
        for (int i = 0; i < listViewNews.length; i++) {
            listViewNews[i].removeAll();
        }
    }

    public boolean isPageInit(int position) {
        return listViewNews[position].getCount() != 0;
    }

    public void setOrientation(boolean orientation) {
        for (int i = 0; i < listViewNews.length; i++) {
            try {
                listViewNews[i].setOrientation(orientation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
