package com.polarb.android.domain;

public class Choice {

    private String name;
    private int sortOrder;
    private int voteCount;

    public Choice(String name, int sortOrder, int voteCount) {
        this.name = name;
        this.sortOrder = sortOrder;
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getName() {
        return name;
    }
}