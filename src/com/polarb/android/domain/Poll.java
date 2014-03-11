package com.polarb.android.domain;

import java.util.Date;
import java.util.List;

public class Poll {
    private int pollId;
    private String url;
    private String shortUrl;
    private String caption;
    private String pollType;
    private String created;
    private int commentsCount;
    private Creator creator;
    private List<Choice> choices;
    private List<Image> images;
    private List<Comment> comments;

    private int userVoted = 0;

    public Poll(int pollId,
                String url,
                String shortUrl,
                String caption,
                String pollType,
                String created,
                int commentsCount,
                Creator creator,
                List<Choice> choices,
                List<Image> images,
                List<Comment> comments) {
        this.pollId = pollId;
        this.url = url;
        this.shortUrl = shortUrl;
        this.caption = caption;
        this.pollType = pollType;
        this.created = created;
        this.commentsCount = commentsCount;
        this.creator = creator;
        this.choices = choices;
        this.images = images;
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public int getPollId() {
        return pollId;
    }

    public String getUrl() {
        return url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getCaption() {
        return caption;
    }

    public String getPollType() {
        return pollType;
    }

    public String getCreated() {
        return created;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public Creator getCreator() {
        return creator;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public List<Image> getImages() {
        return images;
    }

    public int getUserVoted() {
        return userVoted;
    }

    public void setUserVoted(int userVoted) {
        this.userVoted = userVoted;
    }
}
