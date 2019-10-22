package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.onlinenewsmobile.adapters.NewspaperCustomArrayAdapter;
import com.example.onlinenewsmobile.daos.NewspaperDAO;
import com.example.onlinenewsmobile.models.NewspaperDTO;

import java.util.ArrayList;

public class NewspaperActivity extends AppCompatActivity {

    private static final int NEWSPAPER_SELECTED = 220;

    private ListView listView;
    private NewspaperCustomArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

        NewspaperDAO newspaperDAO = new NewspaperDAO(this);

        ArrayList<NewspaperDTO> list = newspaperDAO.seed();

        adapter = new NewspaperCustomArrayAdapter(this, R.layout.newspaper_item, list);
        listView = findViewById(R.id.listViewNewspaper);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("newspaper", adapter.getItem(position));
                setResult(NEWSPAPER_SELECTED, intent);
                finish();
            }
        });
    }

    public void clickToBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        fileList();
    }
}
