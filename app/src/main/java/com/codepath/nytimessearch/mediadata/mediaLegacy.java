package com.codepath.nytimessearch.mediadata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hkanekal on 3/17/2017.
 */

public class mediaLegacy {
    @SerializedName("tnheight")
    @Expose
    private String tnheight;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("tnwidth")
    @Expose
    private String tnwidth;
    // generated getters and setters
    public String getTnheight() {
        return tnheight;
    }

    public void setTnheight(String tnheight) {
        this.tnheight = tnheight;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTnwidth() {
        return tnwidth;
    }

    public void setTnwidth(String tnwidth) {
        this.tnwidth = tnwidth;
    }

}
