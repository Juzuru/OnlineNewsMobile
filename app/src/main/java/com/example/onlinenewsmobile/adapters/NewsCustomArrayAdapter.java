package com.example.onlinenewsmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinenewsmobile.NewsDetailActivity;
import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.models.NewsDTO;

import java.util.List;

public class NewsCustomArrayAdapter extends ArrayAdapter<NewsDTO> {

    private AppCompatActivity context;
    private int verticalResource;
    private int horizontalResource;

    private LayoutInflater inflater;

    private boolean isVertical = true;

    public NewsCustomArrayAdapter(@NonNull AppCompatActivity context, int verticalResource, int horizontalResource, @NonNull List<NewsDTO> objects) {
        super(context, verticalResource, objects);
        this.context = context;
        this.verticalResource = verticalResource;
        this.horizontalResource = horizontalResource;
        inflater = (LayoutInflater)context.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(isVertical ? verticalResource : horizontalResource, parent, false);
        } else if (convertView.findViewById(R.id.textViewDescription) == null && isVertical) {
            convertView = inflater.inflate(verticalResource, parent, false);
        } else if (convertView.findViewById(R.id.textViewDescription) != null && !isVertical) {
            convertView = inflater.inflate(horizontalResource, parent, false);
        }

        try {
            NewsDTO dto = getItem(position);

            TextView textView = convertView.findViewById(R.id.textViewNewsType);
            textView.setText(dto.getNewsType());
            textView.setOnClickListener(onNewsTypeClickListener());

            textView = convertView.findViewById(R.id.textViewNewspaper);
            textView.setText(dto.getNewspaper());
            textView.setOnClickListener(onNewspaperClickListener());

            ((TextView) convertView.findViewById(R.id.textViewTitle)).setText(dto.getTitle());
            //((ImageView) convertView.findViewById(R.id.imageViewNews)).setImageBitmap(dto.getImageBitmap());
            if (isVertical) {
                ((TextView) convertView.findViewById(R.id.textViewDescription)).setText(dto.getDescription());
            }
            ImageView imageView = convertView.findViewById(R.id.imageViewBookMark);

            imageView.setOnClickListener(onBookMarkClickListener());
            convertView.setOnClickListener(onNewsClickListener(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private View.OnClickListener onNewsClickListener(final NewsDTO dto) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("newTypeName", dto.getNewsType());
                intent.putExtra("link", dto.getLink());
                context.startActivity(intent);
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

    public void setOrientation(boolean orientation) {
        isVertical = orientation;
    }
}
