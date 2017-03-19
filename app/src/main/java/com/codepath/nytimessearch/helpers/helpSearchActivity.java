package com.codepath.nytimessearch.helpers;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.util.TextUtils;

/**
 * Created by hkanekal on 3/17/2017.
 * Attempted to clean up main "search" activity, but
 * realized that the fragment call for updating these
 * requires too many "listners" and receivers ....
 * just easier to move this to main search activity!
 */

public class helpSearchActivity {
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
    public final static String WEB_URL = "web_url";
    private final static String KEY = "54c0153ff2024e0b9b1f52131bc427f0";
    private Toast toast;
    public enum Order {
        RECENT("recent"), OLD("old");

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

    public void showToast(Context context, String message) {
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

        public static String getOnDate() {
            return onDate;
        }

        public static String getToShow() {
            return toShow;
        }

        public static Order getOrder() {
            return order;
        }
        public static List<SelectedNews> getNewSelected() {
            return newSelected;
        }

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
