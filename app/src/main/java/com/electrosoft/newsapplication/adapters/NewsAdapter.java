package com.electrosoft.newsapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.pojos.Article;
import com.electrosoft.newsapplication.pojos.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article> NewsList;
    private Context context;

    public NewsAdapter(List<Article> newsList, Context context) {
        NewsList = newsList;
        this.context = context;
    }

    public void setNewsList(List<Article> newsList) {
        NewsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        holder.newsTitle.setText(NewsList.get(position).getTitle());
        holder.date.setText(NewsList.get(position).getPublishedAt());
        Glide.with(context).load(NewsList.get(position).getUrlToImage()).into(holder.newsImage);
        holder.author.setText(NewsList.get(position).getAuthor());
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, NewsList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail, newsImage;
        TextView author, newsTitle, date;
        ImageButton shareButton;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.newsThumbnail);
            author = itemView.findViewById(R.id.newsPoster);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsImage = itemView.findViewById(R.id.newsImage);
            date = itemView.findViewById(R.id.newsDate);
            shareButton = itemView.findViewById(R.id.newsShareButton);
        }
    }
}
