package com.example.onlinenewsmobile.views;

import android.graphics.Color;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.adapters.NewsCustomArrayAdapter;
import com.example.onlinenewsmobile.models.NewsDTO;
import com.example.onlinenewsmobile.models.NewsTypeDTO;
import com.example.onlinenewsmobile.services.RssService;

import java.util.ArrayList;

public class ListNewsView extends ListView {

    public NewsCustomArrayAdapter adapter;
    public ArrayList<NewsDTO> list;

    public ListNewsView(AppCompatActivity context, @NonNull ArrayList<NewsDTO> list, String color) {
        super(context);
        this.list = list;
        adapter = new NewsCustomArrayAdapter(context, R.layout.news_item_large, R.layout.news_item_small, this.list);
        this.setAdapter(adapter);
        this.setId(R.id.list_view_news);
        this.setBackgroundColor(Color.parseColor(color));
    }

    public void addNews(@NonNull ArrayList<NewsDTO> list) {
        this.adapter.addAll(list);
    }

    public void addNews(NewsTypeDTO newsTypeDTO) {
        new RssService().addNews(adapter, newsTypeDTO);
    }

    public void removeAll() {
        adapter.clear();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setOrientation(boolean orientation) {
        adapter.setOrientation(orientation);
    }
}