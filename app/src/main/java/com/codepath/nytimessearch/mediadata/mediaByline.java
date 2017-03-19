package com.codepath.nytimessearch.mediadata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hkanekal on 3/17/2017.
 */

public class mediaByline {
    // Grab labels from JSON
    @SerializedName("person")
    @Expose
    private List<mediaPerson> person = new ArrayList<mediaPerson>();
    @SerializedName("original")
    @Expose
    private String original;
 // generated getters and setters
    public List<mediaPerson> getPerson() {
        return person;
    }

    public void setPerson(List<mediaPerson> person) {
        this.person = person;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
