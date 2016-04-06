package com.example.adam.tunisia.Presenter.Presenters;

import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Model.Rest.RetrofitFeedback;
import com.example.adam.tunisia.View.Activities.SCFeedback;

public class SCFeedbackPresenter {

    private static final String TAG = "SCFeedbackPresenter";

    private SCFeedback VF;
    private RetrofitFeedback RF;

    public SCFeedbackPresenter(SCFeedback VF) {
        this.VF = VF;
        this.RF = new RetrofitFeedback();
    }

    public void postFeedback(Feedback f){
        RF.postFeedback(f);
        VF.merci();
    }

}
