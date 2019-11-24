package com.electrosoft.newsapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.electrosoft.newsapplication.activities.NewsDetailed;
import com.electrosoft.newsapplication.pojos.Article;
import com.electrosoft.newsapplication.pojos.News;
import com.electrosoft.newsapplication.pojos.Post;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Post> NewsList;
    private Context context;

    public NewsAdapter(List<Post> newsList, Context context) {
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
        holder.setListener(new clickListener() {
            @Override
            public void onClick(int i, View v) {
                context.startActivity(new Intent(context, NewsDetailed.class).putExtra("Extra_NewsArticle", NewsList.get(i)));
            }
        });
        holder.newsTitle.setText(String.valueOf(Html.fromHtml(NewsList.get(position).getTitle())).replace("\n",""));
        String[] a = NewsList.get(position).getDate().split("T");
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


        if(NewsList.get(position).getImage_url().equals(""))
        {
            holder.newsImage.setVisibility(View.GONE);
        }
        else
        Glide.with(context).load(NewsList.get(position).getImage_url()).into(holder.newsImage);
//        try {
//            Glide.with(context).load(getSourceUrl(NewsList.get(position).getUrl())).into(holder.thumbnail);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
      //  holder.author.setText(NewsList.get(position).getSource().getName());
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, NewsList.get(position).getPostLink());
                intent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(intent, null);
                context.startActivity(shareIntent);
            }
        });
    }

    private String getSourceUrl(String url) throws URISyntaxException {
        URI uri = new URI(url);
        if (uri.getHost() != null) {
            Log.i("HostName ->", "logo.clearbit.com/" + uri.getHost());
            return "https://logo.clearbit.com/" + uri.getHost();
        }
        return "";
    }


    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail, newsImage;
        TextView author, newsTitle, date;
        ImageButton shareButton;

        clickListener listener;

        public void setListener(clickListener listener) {
            this.listener = listener;
        }

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsImage = itemView.findViewById(R.id.newsImage);
            date = itemView.findViewById(R.id.newsDate);
            shareButton = itemView.findViewById(R.id.newsShareButton);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(getAdapterPosition(), v);
        }
    }

    private interface clickListener {
        void onClick(int i, View v);
    }
}
