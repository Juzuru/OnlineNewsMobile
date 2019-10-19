package com.example.onlinenewsmobile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.models.CategoryDTO;

import java.util.ArrayList;

public class CategoryCustomAdapter extends BaseAdapter {

    private Button buttonSave;
    private int resource;
    private ArrayList<CategoryDTO> list;

    private LayoutInflater inflater;
    private boolean isChanged = false;

    public CategoryCustomAdapter(AppCompatActivity context, int resource, @NonNull ArrayList<CategoryDTO> list, Button buttonSave) {
        this.resource = resource;
        this.list = list;
        this.buttonSave = buttonSave;
        inflater = (LayoutInflater)context.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        try {
            CategoryDTO dto = list.get(position);

            ((TextView) convertView.findViewById(R.id.textViewCategory)).setText(dto.getName());
            CheckBox checkBox = convertView.findViewById(R.id.checkBoxCategory);
            checkBox.setChecked(dto.isVisible());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setVisible(isChecked);
                    if (!isChanged) {
                        buttonSave.setBackgroundResource(R.color.colorDodgerblue);
                        buttonSave.setTextColor(Color.parseColor("#FFFFFF"));
                        isChanged = !isChanged;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
