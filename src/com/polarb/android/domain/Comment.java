package com.polarb.android.domain;

public class Comment {
    private int commentId;
    private String body;
    private String date;
    private int userID;
    private String username;
    private String profilePhotoSmall;

    public Comment(int commentId, String body, String date, int userID, String username, String profilePhotoSmall) {
        this.commentId = commentId;
        this.body = body;
        this.date = date;
        this.userID = userID;
        this.username = username;
        this.profilePhotoSmall = profilePhotoSmall;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePhotoSmall() {
        return profilePhotoSmall;
    }
}