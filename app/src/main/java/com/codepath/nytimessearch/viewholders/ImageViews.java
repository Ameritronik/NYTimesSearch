package com.codepath.nytimessearch.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.activities.ArticleActivity;
import com.codepath.nytimessearch.activities.SearchActivity;
import com.codepath.nytimessearch.mediadata.mediaArticle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hkanekal on 3/18/2017.
 */

public class ImageViews extends RecyclerView.ViewHolder implements View.OnClickListener
{
    @Bind(R.id.ivArticle) public ImageView ivArticle;
    @Bind(R.id.tvArticle) public TextView tvArticle;
    List<mediaArticle>      articles;
    Context                 mContext;
    // construct
    public ImageViews(Context context, View view, List<mediaArticle> mArticles) {
        super(view);
        this.articles = mArticles;
        this.mContext = context;
        view.setOnClickListener(this);
        ButterKnife.bind(this, view);
    }
    // if clicked do this
    @Override
    public void onClick(View view) {
        int position = getLayoutPosition();
        mediaArticle article = articles.get(position);
        SearchActivity.showToast(mContext, "Please wait...");
        Intent i = new Intent(mContext, ArticleActivity.class);
        i.putExtra("webUrl", article.webUrl);
        mContext.startActivity(i);
    }

    // getters and setters
    public ImageView getIvArticle() {
        return ivArticle;
    }
    public void setIvArticle(ImageView ivArticle) {
        this.ivArticle = ivArticle;
    }
    public TextView getTvArticle() {
        return tvArticle;
    }
    public void setTvArticle(String tvArticle) {
        this.tvArticle.setText(tvArticle);
    }
}



