package com.example.onlinenewsmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onlinenewsmobile.MainActivity;
import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.models.NewsDTO;
import com.example.onlinenewsmobile.models.NewsTypeDTO;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<NewsDTO> {

    private MainActivity context;
    private NewsTypeDTO newsTypeDTO;
    private int resource;

    private LayoutInflater inflater;

    public CustomArrayAdapter(@NonNull MainActivity context, int resource, @NonNull List<NewsDTO> objects, NewsTypeDTO newsTypeDTO) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.newsTypeDTO = newsTypeDTO;
        inflater = (LayoutInflater)context.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        try {
            NewsDTO dto = getItem(position);

            TextView textView = convertView.findViewById(R.id.textViewNewsType);
            textView.setText(newsTypeDTO == null ? dto.getNewsType() : newsTypeDTO.getName());
            textView.setOnClickListener(onNewsTypeClickListener());

            ((TextView) convertView.findViewById(R.id.textViewNewspaper)).setText(newsTypeDTO == null ? dto.getNewspaper() : newsTypeDTO.getNewspaper());
            ((TextView) convertView.findViewById(R.id.textViewTitle)).setText(dto.getTitle());
            ((ImageView) convertView.findViewById(R.id.imageViewNews)).setImageBitmap(dto.getImageBitmap());
            ((TextView) convertView.findViewById(R.id.textViewDescription)).setText(dto.getDescription());
            ImageView imageView = convertView.findViewById(R.id.imageViewBookMark);

            imageView.setOnClickListener(onBookMarkClickListener());
            convertView.setOnClickListener(onNewsClickListener());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private View.OnClickListener onNewsClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "News Clicked", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onBookMarkClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bookmark click", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onNewsTypeClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "News Type click", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onNewspaperClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Newspaper click", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
