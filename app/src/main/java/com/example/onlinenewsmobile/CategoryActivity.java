package com.example.onlinenewsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.onlinenewsmobile.adapters.CategoryCustomAdapter;
import com.example.onlinenewsmobile.daos.CategoryDAO;
import com.example.onlinenewsmobile.models.CategoryDTO;

public class CategoryActivity extends AppCompatActivity {

    private static final int SETTING_CHANGED = 210;
    private boolean isChanged = false;
    private int activeCategories;

    private ListView listView;
    private  CategoryCustomAdapter adapter;

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

    @Override
    public void onBackPressed() {
        if (isChanged) {
            Intent intent = getIntent();
            intent.putExtra("category", true);
            intent.putExtra("activeCategories", activeCategories);
            setResult(SETTING_CHANGED, intent);
        }
        finish();
    }

    public void clickToBack(View view) {
        onBackPressed();
    }

    public void clickToSaveChanges(View view) {
        if (buttonSave.isActivated()) {
            activeCategories = 0;
            CategoryDAO dao = new CategoryDAO(this);

            for (int i = 0; i < adapter.getCount(); i++) {
                CategoryDTO dto = (CategoryDTO) adapter.getItem(i);
                dao.update(dto);

                if (dto.isVisible())
                    activeCategories++;
            }
            buttonSave.setBackgroundResource(R.color.colorGray);
            buttonSave.setTextColor(Color.parseColor("#000000"));
            buttonSave.setActivated(false);
            isChanged = true;
        }
    }
}
