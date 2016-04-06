package com.example.adam.tunisia.View.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Presenter.Presenters.SCFeedbackPresenter;
import com.example.adam.tunisia.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SCFeedback extends AppCompatActivity {

    private static final String TAG = "SCFeedback";

    private SCFeedbackPresenter Presenter;

    @Bind(R.id.ratingBar) RatingBar RB;
    @Bind(R.id.editText) EditText ET;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpfeedback);

        ButterKnife.bind(this);

        toolbar.setTitle("Votre avis compte");
        setSupportActionBar(toolbar);

        Presenter = new SCFeedbackPresenter(this);

    }

    public void envoyerFeedback(View view){
        Presenter.postFeedback(new Feedback("AAAA",5,"VVVV","CCCC",5));
    }

    public void merci(){
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Merci");

        // Setting Dialog Message
        alertDialog.setMessage("Votre avis a été envoyé");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_warning_24dp);

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(SCFeedback.this,SCDetails.class);
                startActivity(i);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
