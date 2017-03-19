package com.codepath.nytimessearch.mediadata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hkanekal on 3/17/2017.
 */

public class multiMedia {
    // Grab labels from JSON
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("legacy")
    @Expose
    private mediaLegacy legacy; // use sub getter setter class mediaLegacy
    @SerializedName("type")
    @Expose
    private String type;
    // generated getters and setters

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public mediaLegacy getLegacy() {
        return legacy;
    }

    public void setLegacy(mediaLegacy legacy) {
        this.legacy = legacy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
