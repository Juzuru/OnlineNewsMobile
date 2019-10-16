package com.example.onlinenewsmobile.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinenewsmobile.MainActivity;
import com.example.onlinenewsmobile.R;

public class HeaderView {
    private MainActivity context;

    public HeaderView(MainActivity context, boolean isDashboard, String headerNasme) {
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
                Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show();
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
