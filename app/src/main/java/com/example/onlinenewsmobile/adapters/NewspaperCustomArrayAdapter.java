package com.example.onlinenewsmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.models.NewspaperDTO;

import java.util.List;

public class NewspaperCustomArrayAdapter extends ArrayAdapter<NewspaperDTO> {

    private Context context;
    private int resource;

    public NewspaperCustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<NewspaperDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }


        String text = getItem(position).getName();
        ((TextView) convertView.findViewById(R.id.textViewNewspaper)).setText(text);

        return convertView;
    }
}
