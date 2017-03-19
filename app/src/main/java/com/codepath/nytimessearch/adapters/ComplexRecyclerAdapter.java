package com.codepath.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.mediadata.mediaArticle;
import com.codepath.nytimessearch.viewholders.ImageViews;
import com.codepath.nytimessearch.viewholders.TextViews;

import java.util.ArrayList;
import java.util.List;

public class ComplexRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int NO_IMAGE=0;       // This article has no image
    private final int IMAGE_THUMB= ~NO_IMAGE; // has image
    private static List<mediaArticle> articles;
    private static Context mContext;

    // construct
    public ComplexRecyclerAdapter(Context context, ArrayList<mediaArticle> articles) {
        this.articles = articles;
        mContext = context;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        mediaArticle article = articles.get(position);
        if (TextUtils.isEmpty(article.getArticleImageUrl())){
            return NO_IMAGE;
        }
        return IMAGE_THUMB;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case NO_IMAGE:
                View textOnly = inflater.inflate(R.layout.grid_view_articles_text,
                        viewGroup, false);
                viewHolder = new TextViews(mContext, textOnly, articles);
                break;
            case IMAGE_THUMB:
                View FullView = inflater.inflate(R.layout.grid_view_articles,
                        viewGroup, false);
                viewHolder = new ImageViews(mContext, FullView, articles);
                break;
            default:
                FullView = inflater.inflate(R.layout.grid_view_articles,
                        viewGroup, false);
                viewHolder = new ImageViews(mContext, FullView, articles);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case NO_IMAGE:
                TextViews tvHolder = (TextViews) viewHolder;
                configureTextViews(tvHolder, position);
                break;
            case IMAGE_THUMB:
                ImageViews ivHolder = (ImageViews) viewHolder;
                configureImageViews(ivHolder, position);
                break;
            default:
                ivHolder = (ImageViews) viewHolder;
                configureImageViews(ivHolder, position);
                break;
        }
    }

    private void configureTextViews(TextViews viewHolder, int position) {
        mediaArticle article = articles.get(position);
        Log.d("DEBUG"," ComplexRecyc Text view: "+(article.getHeadline().getMain()));
        viewHolder.setTvArticleText(article.getHeadline().getMain());
    }

    private void configureImageViews(ImageViews viewHolder,
                                     int position) {
        mediaArticle article = articles.get(position);

        viewHolder.ivArticle.setImageResource(0); //clearoff
        if (!TextUtils.isEmpty(article.getArticleImageUrl())) {
            Glide.with(mContext).load(article.getArticleImageUrl())
                    .placeholder(R.drawable.my_placeholder)
                    .into(viewHolder.ivArticle);
        }
        viewHolder.setTvArticle(article.getHeadline().getMain());
    }
} // end ComplexRecyclerAdapter
