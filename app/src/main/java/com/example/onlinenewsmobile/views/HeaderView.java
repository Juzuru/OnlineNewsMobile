package com.example.onlinenewsmobile.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinenewsmobile.MenuActivity;
import com.example.onlinenewsmobile.NewspaperActivity;
import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.views.search.SearchNewsActivity;

public class HeaderView {
    private static final int SETTING_CHANGED = 210;
    private static final int NEWSPAPER_SELECTED = 220;

    private AppCompatActivity context;
    private TextView textViewTitle;

    public HeaderView(AppCompatActivity context, String headerNasme) {
        this.context = context;

        LinearLayout linearLayout = context.findViewById(R.id.linearLayoutMain);
        context.getLayoutInflater().inflate(R.layout.header, linearLayout, true);
        RelativeLayout relativeLayout = context.findViewById(R.id.relativeLayoutHeader);

        ImageView imageViewMenu = relativeLayout.findViewById(R.id.imageViewMenu);
        imageViewMenu.setOnClickListener(onMenuClick());
        //relativeLayout.findViewById(R.id.imageSearch).setOnClickListener(onSearchClick());
        relativeLayout.findViewById(R.id.imageOpenBook).setOnClickListener(onNewspaperClick());
        textViewTitle = relativeLayout.findViewById(R.id.textViewNewspaper);
        textViewTitle.setText(headerNasme);
    }

    private View.OnClickListener onMenuClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_app_setting), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("currentNewspaper", textViewTitle.getText().toString());
                editor.apply();
                context.startActivityForResult(new Intent(context, MenuActivity.class), SETTING_CHANGED);
            }
        };
    }

//    private View.OnClickListener onBackClick() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show();
//            }
//        };
//    }
//
//    private View.OnClickListener onSearchClick() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SearchNewsActivity.class);
//                context.startActivity(intent);
//                Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show();
//            }
//        };
//    }

    private View.OnClickListener onNewspaperClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewspaperActivity.class);
                context.startActivityForResult(intent, NEWSPAPER_SELECTED);
            }
        };
    }

    public void setTitle(String title) {
        textViewTitle.setText(title);
    }
}
