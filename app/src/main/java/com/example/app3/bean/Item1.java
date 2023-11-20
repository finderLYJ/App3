package com.example.app3.bean;


public class Item1 {
    private String text;
    private int imgPath;

    public Item1(String text, int imgPath) {
        this.text = text;
        this.imgPath = imgPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }
}

