package com.example.adam.tunisia.View.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Presenter.Presenters.SCFeedbackPresenter;
import com.example.adam.tunisia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SCFeedback extends AppCompatActivity {

    private static final String TAG = "SCFeedback";

    public final static String ID_EXTRA="com.example.adam.tunisia.View.Activities.SCList._ID";

    String Societe;
    long SocID;



    private SCFeedbackPresenter Presenter;

    @Bind(R.id.ratingBar) RatingBar RB;
    @Bind(R.id.ETEmail) EditText ETEmail;
    @Bind(R.id.ETCommentaires) EditText ETCommentaires;
    @Bind(R.id.toolbar) Toolbar toolbar;

    String Soc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpfeedback);

        ButterKnife.bind(this);

        toolbar.setTitle("Votre avis compte");
        setSupportActionBar(toolbar);

        Societe = getIntent().getStringExtra("Societe");
        SocID = getIntent().getLongExtra("SocID",0);

        Presenter = new SCFeedbackPresenter(this);

        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Feedback");

        Soc = getIntent().getStringExtra("Societe");

    }

    public void envoyerFeedback(View view){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        if(!( ETCommentaires.getText().toString().length() == 0   ))
        Presenter.postFeedback(new Feedback(date,(int)RB.getRating()+"",ETCommentaires.getText().toString(),ETEmail.getText().toString(),1),Soc);
        else
            Toast.makeText(this,"Veuillez saisir votre commentaire",Toast.LENGTH_LONG).show();
    }

    public void redirect(){
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
                i.putExtra("SocIDEN",Societe);
                i.putExtra("SocID",SocID);
                startActivity(i);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SCDetails.class);
                intent.putExtra("SocIDEN",Societe);
                intent.putExtra("SocID",SocID);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
