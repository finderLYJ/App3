package com.example.app3.bean;

public class Item {
    private int tubiao,arrow;
    private String title;

    public Item() {
    }

    public Item(int tubiao, int arrow, String title) {
        this.tubiao = tubiao;
        this.arrow = arrow;
        this.title = title;
    }

    public int getTubiao() {
        return tubiao;
    }

    public void setTubiao(int tubiao) {
        this.tubiao = tubiao;
    }

    public int getArrow() {
        return arrow;
    }

    public void setArrow(int arrow) {
        this.arrow = arrow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
