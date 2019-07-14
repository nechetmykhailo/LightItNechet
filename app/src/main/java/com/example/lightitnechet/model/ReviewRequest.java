package com.example.lightitnechet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewRequest {

    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("text")
    @Expose
    private String text;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
