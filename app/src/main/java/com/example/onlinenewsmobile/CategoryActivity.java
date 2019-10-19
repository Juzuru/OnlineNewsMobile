package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.onlinenewsmobile.adapters.CategoryCustomAdapter;
import com.example.onlinenewsmobile.daos.CategoryDAO;
import com.example.onlinenewsmobile.models.CategoryDTO;

public class CategoryActivity extends AppCompatActivity {

    private ListView listView;
    CategoryCustomAdapter adapter;

    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoryDAO dao = new CategoryDAO(this);

        buttonSave = findViewById(R.id.btSave);
        listView = findViewById(R.id.listView);
        adapter = new CategoryCustomAdapter(this, R.layout.category_item, dao.getAll(), buttonSave);
        listView.setAdapter(adapter);
    }

    public void clickToBack(View view) {
        finish();
    }

    public void clickToSaveChanges(View view) {
        CategoryDAO dao = new CategoryDAO(this);

        for (int i = 0; i < adapter.getCount(); i++) {
            dao.update((CategoryDTO) adapter.getItem(i));
        }
        buttonSave.setBackgroundResource(R.color.colorGray);
        buttonSave.setTextColor(Color.parseColor("#000000"));
    }
}
