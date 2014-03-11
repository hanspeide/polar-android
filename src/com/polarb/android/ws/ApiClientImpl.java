package com.polarb.android.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.polarb.android.Util;
import com.polarb.android.domain.Poll;
import com.polarb.android.parser.PollParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import static com.polarb.android.Config.BASE_API_URL;
import static com.polarb.android.Config.INTERESTING;
import static com.polarb.android.Config.VOTE;

public class ApiClientImpl implements ApiClient {

    private final PollParser pollParser;
    private final DefaultHttpClient httpClient;

    public ApiClientImpl(PollParser pollParser) {
        this.pollParser = pollParser;
        this.httpClient = new DefaultHttpClient();
    }

    @Override
    public List<Poll> fetchPolls() {
        HttpGet httpGet = new HttpGet(BASE_API_URL + INTERESTING);
        List<Poll> polls = null;

        try {
            HttpResponse response = executeRequest(httpGet);
            polls = pollParser.parse(Util.getStringFromInputStream(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<Poll>(polls);
    }

    @Override
    public void submitVote(int pollId, int choiceId) {
        HttpPost httpPost = new HttpPost(BASE_API_URL + String.format(VOTE, pollId, choiceId));
        executeRequest(httpPost);
    }

    private HttpResponse executeRequest(HttpRequestBase request) {
        try {
            return httpClient.execute(request);
        } catch (HttpResponseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}