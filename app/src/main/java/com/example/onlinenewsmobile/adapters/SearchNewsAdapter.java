package com.example.onlinenewsmobile.adapters;

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
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.NewsHolder> {
    private List<SearchNewsDTO> searchNewsDTOList;
    private Context context;

    public SearchNewsAdapter(List<SearchNewsDTO> searchNewsDTOList, Context context) {
        this.searchNewsDTOList = searchNewsDTOList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_search_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        SearchNewsDTO searchNewsDTO = searchNewsDTOList.get(position);
        holder.imgTitle.setImageResource(R.drawable.bookmark_icon);
        holder.txtNewspaper.setText(searchNewsDTO.getNewspaper());
        holder.txtTitle.setText(searchNewsDTO.getTitle());
        Picasso.get().load(searchNewsDTO.getImage()).into(holder.imgTitle);
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime postedTime = LocalDateTime.parse(searchNewsDTO.getPostedTime(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
//
//        LocalDateTime postedTime = current.minusDays(1);
        long hours = postedTime.until( current, ChronoUnit.HOURS);

        if (hours <= 24) {
            holder.txtTimer.setText(hours + " giờ trước");
        } else {
            holder.txtTimer.setText((hours/24) + " ngày trước");
        }
    }

    @Override
    public int getItemCount() {
        return searchNewsDTOList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTimer, txtNewspaper;
        ImageView imgTitle;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtTimer = itemView.findViewById(R.id.txtTimer);
            txtNewspaper = itemView.findViewById(R.id.txtNewPaper);
            imgTitle = itemView.findViewById(R.id.image);
        }
    }
}
