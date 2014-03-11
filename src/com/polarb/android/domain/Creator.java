package com.polarb.android.domain;

public class Creator {
    private int userId;
    private String name;
    private String username;
    private String profilePhotoSmall;

    public Creator(int userId, String name, String username, String profilePhotoSmall) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.profilePhotoSmall = profilePhotoSmall;
    }

    public String getProfilePhotoSmall() {
        return profilePhotoSmall;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }
}
