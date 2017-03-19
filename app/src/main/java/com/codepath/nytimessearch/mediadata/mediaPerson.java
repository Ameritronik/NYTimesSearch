package com.codepath.nytimessearch.mediadata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hkanekal on 3/17/2017.
 */

public class mediaPerson {
    // Grab JSON labels
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("rank")
    @Expose
    private Integer rank;
// Generate getter and setter
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
