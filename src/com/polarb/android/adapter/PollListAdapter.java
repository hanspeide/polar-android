package com.polarb.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.polarb.android.PolarApplication;
import com.polarb.android.R;
import com.polarb.android.Util;
import com.polarb.android.asynctask.SubmitVoteAsyncTask;
import com.polarb.android.domain.Poll;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PollListAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Poll> polls;


    public PollListAdapter(Activity activity, List<Poll> polls){
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.polls = polls;
    }

    @Override
    public int getCount() {
        return polls.size();
    }

    @Override
    public Poll getItem(int position) {
        return polls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return polls.get(position).getPollId();
    }

    @Override
    public boolean isEnabled(int position){
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View  view = convertView;
        final ViewHolder viewHolder;
        final Poll poll = polls.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.view_poll, parent, false);
            viewHolder = new ViewHolder();
            assignViewsToViewHolder(view, viewHolder);
            viewHolder.pollImage.setLayoutParams(new RelativeLayout.LayoutParams(Util.getPollImageSize(activity).first, Util.getPollImageSize(activity).second));
            viewHolder.pollImageProgress.setLayoutParams(new LinearLayout.LayoutParams(Util.getPollImageSize(activity).first, Util.getPollImageSize(activity).second));

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (poll.getUserVoted() == 1){
            viewHolder.pollAlternative1.setBackgroundResource(R.color.poll_alternative_chosen);
            viewHolder.pollAlternative2.setBackgroundResource(R.color.poll_alternative);
            viewHolder.pollAlternative1.setBackgroundResource(R.color.poll_alternative_chosen);
            viewHolder.pollAlternative2.setBackgroundResource(R.color.poll_alternative);
            viewHolder.pollVoteIndicatorLeft.setImageResource(R.drawable.poll_vote_voted_left);
            viewHolder.pollVoteIndicatorRight.setImageResource(R.drawable.poll_vote_not_voted_right);
            viewHolder.pollVoteLeft.setText(String.valueOf(poll.getChoices().get(0).getVoteCount() + 1));
            viewHolder.pollVoteRight.setText(String.valueOf(poll.getChoices().get(1).getVoteCount()));
            viewHolder.pollVotes.setVisibility(View.VISIBLE);
        } else if (poll.getUserVoted() == 2){
            viewHolder.pollAlternative1.setBackgroundResource(R.color.poll_alternative);
            viewHolder.pollAlternative2.setBackgroundResource(R.color.poll_alternative_chosen);
            poll.setUserVoted(2);
            viewHolder.pollVoteIndicatorLeft.setImageResource(R.drawable.poll_vote_not_voted_left);
            viewHolder.pollVoteIndicatorRight.setImageResource(R.drawable.poll_vote_voted_right);
            viewHolder.pollAlternative2.setBackgroundResource(R.color.poll_alternative_chosen);
            viewHolder.pollAlternative1.setBackgroundResource(R.color.poll_alternative);
            viewHolder.pollVoteLeft.setText(String.valueOf(poll.getChoices().get(0).getVoteCount()));
            viewHolder.pollVoteRight.setText(String.valueOf(poll.getChoices().get(1).getVoteCount() + 1));
            viewHolder.pollVotes.setVisibility(View.VISIBLE);
        } else {
            viewHolder.pollAlternative1.setBackgroundResource(R.color.poll_alternative);
            viewHolder.pollAlternative2.setBackgroundResource(R.color.poll_alternative);
        }

        if (!poll.getCreator().getProfilePhotoSmall().equals("")){
            Picasso.with(activity).load(poll.getCreator().getProfilePhotoSmall()).into(viewHolder.userImage);
        }

        viewHolder.userSays.setText(String.format(activity.getString(R.string.user_asks), poll.getCreator().getName()));
        viewHolder.pollQuestion.setText(poll.getCaption());

        if (poll.getChoices().get(0).getSortOrder() == 1){
            viewHolder.pollAlternative1.setText(poll.getChoices().get(0).getName());
            viewHolder.pollAlternative2.setText(poll.getChoices().get(1).getName());
        } else {
            viewHolder.pollAlternative1.setText(poll.getChoices().get(1).getName());
            viewHolder.pollAlternative2.setText(poll.getChoices().get(0).getName());
        }

        viewHolder.userImageProgress.setVisibility(View.VISIBLE);
        viewHolder.userImage.setVisibility(View.GONE);
        viewHolder.pollImageProgress.setVisibility(View.VISIBLE);
        viewHolder.pollImage.setVisibility(View.GONE);
        viewHolder.pollVotes.setVisibility(View.GONE);

        Picasso.with(activity).load(poll.getImages().get(0).getUrl()).into(viewHolder.pollImage, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.userImageProgress.setVisibility(View.GONE);
                viewHolder.userImage.setVisibility(View.VISIBLE);
                viewHolder.pollImageProgress.setVisibility(View.GONE);
                viewHolder.pollImage.setVisibility(View.VISIBLE);
                if (poll.getUserVoted() != 0){
                    viewHolder.pollVotes.setVisibility(View.VISIBLE);
                }

                setPollImageOnTouchListener(poll,
                        viewHolder.pollAlternative1,
                        viewHolder.pollAlternative2,
                        viewHolder.pollVotes,
                        viewHolder.pollVoteLeft,
                        viewHolder.pollVoteIndicatorLeft,
                        viewHolder.pollVoteRight,
                        viewHolder.pollVoteIndicatorRight,
                        viewHolder.pollImage);
            }

            @Override
            public void onError() {

            }
        });

        return view;
    }

    private void setPollImageOnTouchListener(final Poll poll, final TextView pollAlternative1, final TextView pollAlternative2, final LinearLayout pollVotes, final TextView pollVoteLeft, final ImageView pollVoteIndicatorLeft, final TextView pollVoteRight, final ImageView pollVoteIndicatorRight, ImageView pollImage) {
        pollImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (motionEvent.getX() < view.getWidth() / 2){
                        if (poll.getUserVoted() == 1){
                            poll.setUserVoted(0);
                            pollVotes.setVisibility(View.GONE);
                            pollAlternative1.setBackgroundResource(R.color.poll_alternative);

                        } else {
                            pollAlternative1.setBackgroundResource(R.color.poll_alternative_chosen);
                            pollAlternative2.setBackgroundResource(R.color.poll_alternative);
                            pollVoteIndicatorLeft.setImageResource(R.drawable.poll_vote_voted_left);
                            pollVoteIndicatorRight.setImageResource(R.drawable.poll_vote_not_voted_right);
                            poll.setUserVoted(1);

                            if (poll.getChoices().get(0).getSortOrder() == 0){
                                pollVoteLeft.setText(String.valueOf(poll.getChoices().get(0).getVoteCount() + 1));
                                pollVoteRight.setText(String.valueOf(poll.getChoices().get(1).getVoteCount()));
                            } else {
                                pollVoteLeft.setText(String.valueOf(poll.getChoices().get(1).getVoteCount() + 1));
                                pollVoteRight.setText(String.valueOf(poll.getChoices().get(0).getVoteCount()));
                            }
                            Log.d("Polar", "Left part of poll image got clicked");
                            pollVotes.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (poll.getUserVoted() == 2){
                            poll.setUserVoted(0);
                            pollVotes.setVisibility(View.GONE);
                            pollAlternative2.setBackgroundResource(R.color.poll_alternative);
                            pollVoteRight.setText(String.valueOf(poll.getChoices().get(1).getVoteCount()));
                        } else {
                            poll.setUserVoted(2);
                            pollVoteIndicatorLeft.setImageResource(R.drawable.poll_vote_not_voted_left);
                            pollVoteIndicatorRight.setImageResource(R.drawable.poll_vote_voted_right);
                            pollAlternative2.setBackgroundResource(R.color.poll_alternative_chosen);
                            pollAlternative1.setBackgroundResource(R.color.poll_alternative);
                            if (poll.getChoices().get(0).getSortOrder() == 0){
                                pollVoteLeft.setText(String.valueOf(poll.getChoices().get(0).getVoteCount()));
                                pollVoteRight.setText(String.valueOf(poll.getChoices().get(1).getVoteCount() + 1));
                            } else {
                                pollVoteLeft.setText(String.valueOf(poll.getChoices().get(1).getVoteCount()));
                                pollVoteRight.setText(String.valueOf(poll.getChoices().get(0).getVoteCount() + 1));
                            }
                            Log.d("Polar", "Right part of poll image got clicked");
                            pollVotes.setVisibility(View.VISIBLE);
                        }
                    }

                }

                return true;
            }
        });
    }

    private void submitVote(int pollId, int choice) {
        new SubmitVoteAsyncTask((PolarApplication)activity.getApplication(), pollId, choice).execute();
    }

    private void assignViewsToViewHolder(View view, ViewHolder viewHolder) {
        viewHolder.userImage = (ImageView) view.findViewById(R.id.user_profile_image);
        viewHolder.userImageProgress = (ProgressBar) view.findViewById(R.id.user_profile_image_progress);
        viewHolder.userSays = (TextView) view.findViewById(R.id.poll_user_asks);
        viewHolder.pollQuestion = (TextView) view.findViewById(R.id.poll_question);
        viewHolder.pollAlternative1 = (TextView) view.findViewById(R.id.poll_alternative_1);
        viewHolder.pollAlternative2 = (TextView) view.findViewById(R.id.poll_alternative_2);
        viewHolder.pollImage = (ImageView) view.findViewById(R.id.poll_image);
        viewHolder.pollImageProgress = (FrameLayout) view.findViewById(R.id.poll_image_progress);
        viewHolder.pollVotes = (LinearLayout) view.findViewById(R.id.poll_votes);
        viewHolder.pollVoteLeft = (TextView) view.findViewById(R.id.poll_vote_left);
        viewHolder.pollVoteIndicatorLeft = (ImageView)view.findViewById(R.id.poll_vote_indicator_left);
        viewHolder.pollVoteRight = (TextView) view.findViewById(R.id.poll_vote_right);
        viewHolder.pollVoteIndicatorRight = (ImageView)view.findViewById(R.id.poll_vote_indicator_right);
    }

    static class ViewHolder {
        ImageView userImage;
        ProgressBar userImageProgress;
        TextView userSays;
        TextView pollQuestion;
        TextView pollAlternative1;
        TextView pollAlternative2;
        ImageView pollImage;
        FrameLayout pollImageProgress;
        LinearLayout pollVotes;
        TextView pollVoteLeft;
        ImageView pollVoteIndicatorLeft;
        TextView pollVoteRight;
        ImageView pollVoteIndicatorRight;
    }
}
