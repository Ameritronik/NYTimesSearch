package com.codepath.nytimessearch.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.activities.ArticleActivity;
import com.codepath.nytimessearch.activities.SearchActivity;
import com.codepath.nytimessearch.mediadata.mediaArticle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TextViews extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tvArticleText) public TextView tvArticleText;
        List<mediaArticle> articles;
        Context mContext;
        // Construct
        public TextViews(Context context, View view, List<mediaArticle> mArticles) {
            super(view);
            this.articles = mArticles;
            this.mContext = context;

            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }
        // If a click on view
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            mediaArticle article = articles.get(position);
            SearchActivity.showToast(mContext, "Please wait...");
            Intent i = new Intent(mContext, ArticleActivity.class);
            i.putExtra("webUrl", article.webUrl);
            Log.d("DEBUG","TextView, Got webUrl "+ article.webUrl);
            mContext.startActivity(i);
        }
        // send the article view back to requester
        public TextView getTvArticleText() {
            return tvArticleText;
        }
        // set the article view from the requester
        public void setTvArticleText(String tvArticleText) {
            this.tvArticleText.setText(tvArticleText);
        }
}

