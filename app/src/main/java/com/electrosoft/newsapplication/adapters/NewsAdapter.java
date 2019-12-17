package com.electrosoft.newsapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.electrosoft.newsapplication.Common.Common;
import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.activities.NewsDetailed;
import com.electrosoft.newsapplication.pojos.Post;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return 99;
        return position%3;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType ==99)
            return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false));
       if(viewType ==0)
            return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_ad, parent, false));
        else
            return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        if(position%3==0&&position!=0)
        {
          //  NewsViewHolder view = new NewsViewHolder(LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.layout_for_ad, null,false));



            AdLoader.Builder builder = new AdLoader.Builder(context, Common.native_id);

            final FrameLayout frameLayout =
                    holder.itemView.findViewById(R.id.fl_adplaceholder);
            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                // OnUnifiedNativeAdLoadedListener implementation.
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    // You must call destroy on old ads when you are done with them,
                    // otherwise you will have a memory leak.

                    //  Toast.makeText(MoreApps.this, "Ad Loaded" , Toast.LENGTH_SHORT).show();
                    UnifiedNativeAdView adView = (UnifiedNativeAdView) LayoutInflater.from(context).inflate(R.layout.ad_unified, null);
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                }

            });
//                VideoOptions videoOptions = new VideoOptions.Builder()
//                        .setStartMuted(startVideoAdsMuted.isChecked())
//                        .build();
//
//                NativeAdOptions adOptions = new NativeAdOptions.Builder()
//                        .setVideoOptions(videoOptions)
//                        .build();

  //              builder.withNativeAdOptions(adOptions);

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    Log.d("AdView","Failed to load Adview");
//                        refresh.setEnabled(true);
//                        Toast.makeText(MainActivity.this, "Failed to load native ad: "
//                                + errorCode, Toast.LENGTH_SHORT).show();
                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().addTestDevice("41B2730621B6EDDB8BF5E8148E0CC1CE").addTestDevice("348BB8081B57DE9119AB5673A77574FF").addTestDevice("3BED85CCA5AB7BB12769D13A02C90211").addTestDevice("90D7D195DE3F308550AFED9E113EDFD1").addTestDevice("E025B2F218684D5D4A1BFFD17B97BEBD").build());

        }
        else
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, NewsDetailed.class).putExtra("Extra_NewsArticle", NewsList.get(position)));

                }
            });
//        holder.setListener(new clickListener() {
//            @Override
//            public void onClick(int i, View v) {
//            }
//        });
        holder.newsTitle.setText(String.valueOf(Html.fromHtml(NewsList.get(position).getTitle())).replace("\n", ""));
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


        if (NewsList.get(position).getImage_url().equals("")) {
            holder.newsImage.setVisibility(View.GONE);
        } else
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

    class NewsViewHolder extends RecyclerView.ViewHolder  {

        ImageView thumbnail, newsImage;
        TextView author, newsTitle, date;
        ImageButton shareButton;

        clickListener listener;

        public void setListener(clickListener listener) {
            this.listener = listener;
        }

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsImage = itemView.findViewById(R.id.newsImage);
            date = itemView.findViewById(R.id.newsDate);
            shareButton = itemView.findViewById(R.id.newsShareButton);
        }


    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);



    }

    private interface clickListener {
        void onClick(int i, View v);
    }
}
