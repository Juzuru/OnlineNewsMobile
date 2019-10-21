package com.example.onlinenewsmobile.adapters.suggesst;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinenewsmobile.R;
import com.example.onlinenewsmobile.models.KA.SearchNewsDTO;
import com.example.onlinenewsmobile.models.KA.TopNewsHighReadTimes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuggestedNewsAdapter extends RecyclerView.Adapter<SuggestedNewsAdapter.NewsHolder> {
    private List<TopNewsHighReadTimes> listSuggesst;
    private Context context;

    public SuggestedNewsAdapter(List<TopNewsHighReadTimes> listSuggesst, Context context) {
        this.listSuggesst = listSuggesst;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_search_news,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        TopNewsHighReadTimes newsSuggesst = listSuggesst.get(position);
        holder.txtNewsPaper.setText(newsSuggesst.getNewspaperName());
        Picasso.get().load(newsSuggesst.getImage()).into(holder.imgTitle);
        holder.txtTitle.setText(newsSuggesst.getTitle());
        holder.txtCategory.setText(newsSuggesst.getCategory());

    }

    @Override
    public int getItemCount() {
        return listSuggesst.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtNewsPaper, txtCategory;
        private ImageView imgTitle;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgTitle = itemView.findViewById(R.id.image);
            txtNewsPaper = itemView.findViewById(R.id.txtNewPaper);
            txtCategory = itemView.findViewById(R.id.txtCategory);
        }
    }
}
