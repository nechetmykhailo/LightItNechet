package com.example.lightitnechet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("title")
    @Expose
    private String title;

    private static final String IMG = "http://smktesting.herokuapp.com/static/";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return IMG + img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
