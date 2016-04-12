package com.example.adam.tunisia.Presenter.Presenters;

import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Model.Rest.RetrofitFeedback;
import com.example.adam.tunisia.View.Activities.SCFeedback;

public class SCFeedbackPresenter {

    private static final String TAG = "SCFeedbackPresenter";

    private SCFeedback SCFeedback;
    private RetrofitFeedback RetrofitFeedback;

    public SCFeedbackPresenter(SCFeedback VF) {
        Log.v(TAG, "Constructor");

        this.SCFeedback = VF;
        this.RetrofitFeedback = new RetrofitFeedback();

    }

    public void postFeedback(Feedback f){
        Log.v(TAG, "postFeedback");

        RetrofitFeedback.postFeedback(f);
        SCFeedback.redirect();

    }

}
