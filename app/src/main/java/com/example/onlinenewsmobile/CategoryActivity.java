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
import com.example.onlinenewsmobile.daos.NewspaperDAO;
import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.models.NewspaperDTO;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private static final int SETTING_CHANGED = 210;
    private static final int SETTING_NOT_CHANGED = 310;

    private boolean isChanged = false;

    private ListView listView;
    private  CategoryCustomAdapter adapter;

    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        NewspaperDAO newspaperDAO = new NewspaperDAO(this);
        CategoryDAO categoryDAO = new CategoryDAO(this);

        ArrayList<NewspaperDTO> newspaperDTOS = newspaperDAO.getAll();
        ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();
        String categoryName;
        int n = 0;
        for (int i = 0; i < newspaperDTOS.size(); i++) {
            categoryDTOS.addAll(categoryDAO.getByNewspaperIdForSetting(newspaperDTOS.get(i).getId()));
            for (int j = n; j < categoryDTOS.size(); j++) {
                categoryName = categoryDTOS.get(j).getName();
                categoryDTOS.get(j).setName(newspaperDTOS.get(i).getName() + " - " + categoryName);
            }
            n = categoryDTOS.size();
        }

        buttonSave = findViewById(R.id.btSave);
        listView = findViewById(R.id.listView);
        adapter = new CategoryCustomAdapter(this, R.layout.category_item, categoryDTOS, buttonSave);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        if (isChanged) {
            intent.putExtra("category", true);
            setResult(SETTING_CHANGED, intent);
        } else {
            setResult(SETTING_NOT_CHANGED, intent);
        }
        finish();
    }

    public void clickToBack(View view) {
        onBackPressed();
    }

    public void clickToSaveChanges(View view) {
        if (buttonSave.isActivated()) {
            CategoryDAO dao = new CategoryDAO(this);

            for (int i = 0; i < adapter.getCount(); i++) {
                CategoryDTO dto = (CategoryDTO) adapter.getItem(i);
                dao.update(dto);
            }
            buttonSave.setBackgroundResource(R.color.colorGray);
            buttonSave.setTextColor(Color.parseColor("#000000"));
            buttonSave.setActivated(false);
            isChanged = true;
        }
    }
}
