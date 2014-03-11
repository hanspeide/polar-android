package com.polarb.android.domain;

public class Image {
    private int sortOrder;
    private String url;

    public Image(int sortOrder, String url) {
        this.sortOrder = sortOrder;
        this.url = url;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getUrl() {
        return url;
    }
}
