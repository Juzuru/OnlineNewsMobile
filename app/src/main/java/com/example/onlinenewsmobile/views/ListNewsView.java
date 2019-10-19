package com.example.onlinenewsmobile.views;

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

    private NewsCustomArrayAdapter adapter;

    public ListNewsView(AppCompatActivity context, @NonNull ArrayList<NewsDTO> list) {
        super(context);
        adapter = new NewsCustomArrayAdapter(context, R.layout.news_item_large, R.layout.news_item_small, list);
        this.setAdapter(adapter);
    }

    public void addNews(@NonNull ArrayList<NewsDTO> list) {
        this.adapter.addAll(list);
    }

    public void addNews(NewsTypeDTO newsTypeDTO) {
        new RssService().addNews(adapter, newsTypeDTO);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setOrientation(boolean orientation) {
        adapter.setOrientation(orientation);
    }
}