package com.electrosoft.newsapplication.adapters;

import android.content.Context;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article> NewsList;
    private Context context;

    public NewsAdapter(List<Article> newsList, Context context) {
        NewsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        holder.newsTitle.setText(NewsList.get(position).getTitle());

        String[] a = NewsList.get(position).getPublishedAt().split("T");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.US);
        try {
            Date date = simpleDateFormat.parse(a[0] + a[1]);
            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.US);
                holder.date.setText(sdf.format(date));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("errordate", e.getLocalizedMessage());
        }

//        holder.date.setText("error");
        Glide.with(context).load(NewsList.get(position).getUrlToImage()).into(holder.newsImage);
        holder.author.setText(NewsList.get(position).getAuthor() == null ? NewsList.get(position).getSource().getName() : NewsList.get(position).getAuthor());
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
