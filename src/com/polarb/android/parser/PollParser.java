package com.polarb.android.parser;

import com.polarb.android.domain.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PollParser {

    public List<Poll> parse(String from) {
        List<Poll> polls;
        try {
             polls = parsePolls(from);
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return polls;
    }

    private List<Poll> parsePolls(String from) throws JSONException {
        JSONArray pollsJsonArray = new JSONArray(from);
        ArrayList<Poll> polls = new ArrayList<Poll>();

        for (int i = 0; i < pollsJsonArray.length(); i++){
            Poll poll = parsePoll((JSONObject)pollsJsonArray.get(i));
            polls.add(poll);
        }

        return polls;
    }

    private Poll parsePoll(JSONObject pollObject) throws JSONException {
        return new Poll(pollObject.getInt("pollID"),
                pollObject.getString("url"),
                pollObject.getString("shortUrl"),
                pollObject.getString("caption"),
                pollObject.optString("pollType"),
                pollObject.getString("created"),
                pollObject.getInt("commentCount"),
                parseCreator(pollObject.getJSONObject("creator")),
                parseChoices(pollObject.getJSONArray("choices")),
                parseImages(pollObject.getJSONArray("images")),
                parseComments(pollObject.optJSONArray("comments")));
    }

    private List<Comment> parseComments(JSONArray commentsJsonArray) throws JSONException {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        for (int i = 0; i < commentsJsonArray.length(); i++){
            Comment comment = parseComment((JSONObject) commentsJsonArray.get(i));
            comments.add(comment);
        }

        return comments;
    }

    private Comment parseComment(JSONObject jsonObject) throws JSONException {
        return new Comment(jsonObject.getInt("commentID"),
                jsonObject.getString("body"),
                jsonObject.getString("date"),
                jsonObject.getInt("userID"),
                jsonObject.getString("username"),
                jsonObject.optString("profilePhotoSmall"));
    }

    private List<Image> parseImages(JSONArray imagesJsonArray) throws JSONException {
        ArrayList<Image> images = new ArrayList<Image>();

        for (int i = 0; i < imagesJsonArray.length(); i++){
            Image image = parseImage((JSONObject) imagesJsonArray.get(i));
            images.add(image);
        }

        return images;
    }

    private Image parseImage(JSONObject imageJsonObject) throws JSONException {
        return new Image(imageJsonObject.getInt("sortOrder"),
                imageJsonObject.getString("url"));
    }

    private Creator parseCreator(JSONObject creatorJsonObject) throws JSONException {
        return new Creator(creatorJsonObject.getInt("userID"),
                creatorJsonObject.getString("name"),
                creatorJsonObject.getString("username"),
                creatorJsonObject.optString("profilePhotoSmall"));
    }

    private List<Choice> parseChoices(JSONArray choicesJsonArray) throws JSONException {
        ArrayList<Choice> choices = new ArrayList<Choice>();

        for (int i = 0; i < choicesJsonArray.length(); i++){
            Choice choice = parseChoice((JSONObject) choicesJsonArray.get(i));
            choices.add(choice);
        }

        return choices;
    }

    private Choice parseChoice(JSONObject choiceJsonObject) throws JSONException {
        return new Choice(choiceJsonObject.getString("name"),
                choiceJsonObject.getInt("sortOrder"),
                choiceJsonObject.getInt("voteCount"));
    }
}
