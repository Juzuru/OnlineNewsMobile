package com.example.onlinenewsmobile.views;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinenewsmobile.MenuActivity;
import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.views.search.SearchNewsActivity;

public class HeaderView {
    private static final int SETTING_CHANGED = 210;

    private AppCompatActivity context;

    public HeaderView(AppCompatActivity context, boolean isDashboard, String headerNasme) {
        this.context = context;

        LinearLayout linearLayout = context.findViewById(R.id.linearLayoutMain);
        context.getLayoutInflater().inflate(R.layout.header, linearLayout, true);
        RelativeLayout relativeLayout = context.findViewById(R.id.relativeLayoutHeader);

        ImageView imageViewMenu = relativeLayout.findViewById(R.id.imageViewMenu);
        if (isDashboard) {
            imageViewMenu.setOnClickListener(onMenuClick());
        } else {
            imageViewMenu.setImageResource(R.drawable.less_than_icon);
        }

        relativeLayout.findViewById(R.id.imageSearch).setOnClickListener(onSearchClick());
        relativeLayout.findViewById(R.id.imageOpenBook).setOnClickListener(onNewspaperClick());
        ((TextView) relativeLayout.findViewById(R.id.textViewNewspaper)).setText(headerNasme);
    }

    private View.OnClickListener onMenuClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivityForResult(new Intent(context, MenuActivity.class), SETTING_CHANGED);
            }
        };
    }

    private View.OnClickListener onBackClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onSearchClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchNewsActivity.class);
                context.startActivity(intent);
                Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onNewspaperClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Newspaper", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
