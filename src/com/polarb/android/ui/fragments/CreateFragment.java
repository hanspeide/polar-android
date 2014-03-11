package com.polarb.android.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.polarb.android.PolarApplication;
import com.polarb.android.R;
import com.polarb.android.Util;
import com.polarb.android.ws.ApiClient;

import javax.inject.Inject;


public class CreateFragment extends Fragment {

    @Inject private ApiClient apiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PolarApplication) getActivity().getApplication()).inject(this);
    }

    @Override
    public LinearLayout onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.fragment_create, container, false);

        View pollView = inflater.inflate(R.layout.view_poll_create, layout, true);

        Pair imageViewSize = Util.getPollImageSize(getActivity());
        pollView.findViewById(R.id.poll_image).setLayoutParams(new LinearLayout.LayoutParams((Integer) imageViewSize.first, (Integer) imageViewSize.second));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((Integer)imageViewSize.first, 280);
        params.gravity = Gravity.CENTER;
        pollView.findViewById(R.id.create_button_layout).setLayoutParams(params);

        return layout;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        final EditText userAsksEditText = (EditText)view.findViewById(R.id.poll_question);
        final EditText alternative1EditText = (EditText)view.findViewById(R.id.poll_alternative_1);
        final EditText alternative2EditText = (EditText)view.findViewById(R.id.poll_alternative_1);
        final ImageView userImage = (ImageView)view.findViewById(R.id.user_profile_picture);
        final ImageView hideKeyboard = (ImageView)view.findViewById(R.id.hide_keyboard);

        userAsksEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EditText userAsksEditText = (EditText)view;
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(userAsksEditText, InputMethodManager.SHOW_IMPLICIT);
                userAsksEditText.requestFocus();
                if (userAsksEditText.getText().toString().equals("Add A Question...")){
                    userAsksEditText.setText("?");
                    userAsksEditText.setSelection(0);
                }
                userImage.setVisibility(View.GONE);

                hideKeyboard.setBaselineAlignBottom(true);

                Animation in = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.slide_in_from_bottom);
                hideKeyboard.setAnimation(in);
                hideKeyboard.setVisibility(View.VISIBLE);

                return true;
            }
        });

        hideKeyboard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userAsksEditText.getWindowToken(), 0);

                if (userAsksEditText.isFocused()){
                    userAsksEditText.clearFocus();
                } else if (alternative1EditText.isFocused()){
                    alternative1EditText.clearFocus();
                } else if (alternative2EditText.isFocused()){
                    alternative2EditText.clearFocus();
                }


                Animation out = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.slide_out_to_bottom);
                Animation in = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.slide_in_from_bottom);

                hideKeyboard.setAnimation(out);
                hideKeyboard.setVisibility(View.GONE);

                userImage.setAnimation(in);
                userImage.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }
}
