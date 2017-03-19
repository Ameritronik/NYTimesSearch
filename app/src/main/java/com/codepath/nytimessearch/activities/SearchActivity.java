package com.codepath.nytimessearch.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.adapters.ComplexRecyclerAdapter;
import com.codepath.nytimessearch.fragments.SearchSettingsFragment;
import com.codepath.nytimessearch.mediadata.mediaArticle;
import com.codepath.nytimessearch.network.checknetwork;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.rvArticles) RecyclerView newsItemsRecyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;

    public static String defaultQuery = "Tomato";
    ArrayList<mediaArticle> articles = new ArrayList<mediaArticle>();
    ComplexRecyclerAdapter mComplexRecyclerAdapter;

    public final static String NYT_URL =
            "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    public final static String API_KEY = "api-key";
    public final static String PAGE = "page";
    public final static String BEGIN_DATE = "begin_date";
    public final static String FILTER_QUERY = "fq";
    public final static String SORT_ORDER = "sort";
    public final static String NORMAL_QUERY = "q";
    public final static String RESPONSE = "response";
    public final static String DOCS = "docs";
    private final static String KEY = "54c0153ff2024e0b9b1f52131bc427f0";
    private static Toast toast;
    private static String toShow;

    public enum Order {
        RECENT("newest"), OLD("oldest");
        private String sort;
        Order(String sort) {
            this.sort = sort;
        } // how to sort?
        public String getSort() {
            return sort;
        } // return that order
    }

    public enum SelectedNews {
        ARTS("Arts"), FASHION_AND_STYLE("Fashion & Style"), SPORTS("Sports");
        private String news;
        SelectedNews(String news) {
            this.news = news;
        } // init news type
        public String getSelectedNews() {
            return news;
        } // return one of enum'd values
    }

    public static void showToast(Context context, String message) {
        if (toast != null) { // prevent multi-toasts
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static class QueryDefaultValues { // set defaults

        static String onDate =  "";
        static String toShow =  "";
        static Order order = Order.RECENT;
        static List<SelectedNews> newSelected = new ArrayList<>();

        public static Order getOrder() {
            return order;
        }
        public static List<SelectedNews> getNewSelected() {
            return newSelected;
        }
        public static String getToShow() {
            return toShow;
        }

        public static void setOrder(Order order) {
            QueryDefaultValues.order = order;
        }

        public static void setOnDate(String onDate) {
            QueryDefaultValues.onDate = onDate;
        }

        public static void setToShow(String toShow) {
            QueryDefaultValues.toShow = toShow;
        }

        public static void setNewSelected(List<SelectedNews> newSelected) {
            QueryDefaultValues.newSelected = newSelected;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("New York Times search");
        mComplexRecyclerAdapter = new ComplexRecyclerAdapter(this, articles);
        StaggeredGridLayoutManager staggeredDisplayManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredDisplayManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        newsItemsRecyclerView.addOnScrollListener(
                new EndlessRecyclerViewScrollListener(staggeredDisplayManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        loadNextDataFromApi(page);
                    }
                });
        // Use the Recycler Adapter
        newsItemsRecyclerView.setAdapter(mComplexRecyclerAdapter);
        // to connect to display manager
        newsItemsRecyclerView.setLayoutManager(staggeredDisplayManager);
        // Do some basic network testing and if network available, fire off searches
        boolean HaveCloud = new checknetwork(getBaseContext()).HaveCloud();
        if(HaveCloud){ // OK, networked and on line so get the articles
            searchForArticle(defaultQuery, 0);
        }
      } // end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // implement a listener here to generate the menu options on newquery
        MenuItem  mSearchMenuItem =  menu.findItem(R.id.action_newsearch);
        final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newquery) {
                resetAdapter(newquery);
                mSearchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newquery) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search_settings) {
            SearchSettingsFragment searchSettingsDialog = new SearchSettingsFragment();
            FragmentManager mFragManager = getSupportFragmentManager();
            searchSettingsDialog.show(mFragManager, "Settings");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadNextDataFromApi(int offset) {
        searchForArticle(defaultQuery, offset);
        //int mSize = newsItemsRecyclerView.getAdapter().getItemCount();
        int mArticleSize = articles.size() - 1;
        //newsItemsRecyclerView.notifyItemRangeInserted(mSize, mArticleSize);
        newsItemsRecyclerView.setItemViewCacheSize(mArticleSize);
        mComplexRecyclerAdapter.notifyDataSetChanged();
    }

    public void searchForArticle(String query, int page) {

        Log.d("DEBUG", "query=> " + query);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = this.BuildQueryParameters(query, page);
        Log.d("DEBUG", "Built up query=> " + query+" Params "+requestParams.toString());
        client.get(NYT_URL, requestParams, new TextHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String  response) {
                //super.onSuccess(statusCode, headers, response);
                try {
                    if (response != null) {
                        Gson gson = new GsonBuilder().create();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject = gson.fromJson(response, JsonObject.class);
                        if (jsonObject.has(RESPONSE)) {
                            JsonObject jsonResponseObject = jsonObject.getAsJsonObject(RESPONSE);
                            Log.d("DEBUG","Json Object returned");
                            if (jsonResponseObject != null) {
                                Log.d("DEBUG","Json Object not null");
                                JsonArray jsonDocsArray = new JsonArray();
                                jsonDocsArray = jsonResponseObject.getAsJsonArray(DOCS);
                                Type collectionType = new TypeToken<List<mediaArticle>>() {
                                }.getType();
                                ArrayList<mediaArticle> fetchedArticles = new ArrayList<mediaArticle>();
                                fetchedArticles = gson.fromJson(jsonDocsArray,collectionType);
                                articles.addAll(fetchedArticles);
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    String JsonErrorMessage = "Json message corrupted";
                    Log.d("DEBUG",JsonErrorMessage);
                    showToast(getBaseContext(), JsonErrorMessage);
                }
                mComplexRecyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToast(getBaseContext(), " Possible HTTP failure");
                boolean HaveCloud = new checknetwork(getBaseContext()).HaveCloud();
            }
        });
    }

    public void resetAdapter(String newquery) {
        if(articles != null) {
            articles.clear();
            mComplexRecyclerAdapter.notifyDataSetChanged();
        }
        searchForArticle(newquery, 0);
    }

    public RequestParams BuildQueryParameters(String query, int page) {
        RequestParams requestParams = new RequestParams();
        requestParams.put(API_KEY, KEY);
        if (!TextUtils.isEmpty(QueryDefaultValues.onDate)) {
            requestParams.put(BEGIN_DATE, QueryDefaultValues.onDate);
        }
        if (QueryDefaultValues.newSelected.size() > 0) {
            StringBuilder QueryBuilder = new StringBuilder();
            QueryBuilder.append("news_desk:(");
            // if more than one query, separate the queries by space, tahink care not
            // to add a space in the end
            if (QueryDefaultValues.newSelected.size() > 1) { // if more than one
                for (int i = 0; i < QueryDefaultValues.newSelected.size() - 1; i++) {
                    QueryBuilder.append(QueryDefaultValues.newSelected.get(i).getSelectedNews());
                    QueryBuilder.append(" "); // add space between strings
                }
                // then get the last one
                QueryBuilder.append(QueryDefaultValues.newSelected.get(QueryDefaultValues.newSelected.size() - 1).getSelectedNews());
            } else { // there is only one query string
                QueryBuilder.append(QueryDefaultValues.newSelected.get(0).getSelectedNews());
            }

            QueryBuilder.append(")");
            Log.d("DEBUG","Query is:"+QueryBuilder.toString());
            requestParams.put(FILTER_QUERY, QueryBuilder.toString());
        }
        // Now add the sort order, page number and normal query string
        requestParams.put(SORT_ORDER, QueryDefaultValues.order.getSort());
        requestParams.put(PAGE, page);
        requestParams.put(NORMAL_QUERY, query);
        // finally return the requested parameters
        return requestParams;
    }
}
