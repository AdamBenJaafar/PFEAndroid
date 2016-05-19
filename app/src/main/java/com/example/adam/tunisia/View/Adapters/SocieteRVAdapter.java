package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Activities.SCDetails;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SocieteRVAdapter extends RecyclerView.Adapter<SocieteRVAdapter.MyViewHolder>{

    Context C ;

    private final LayoutInflater inflater;
    List<Societe> List = Collections.emptyList();

    public SocieteRVAdapter(Context context, List<Societe> L){
        inflater = LayoutInflater.from(context);
        this.C = context;
        this.List = L ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.societe_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder  holder, int position) {

        Societe P = List.get(position);

        holder.TVTitre.setText(P.getIDENTIFICATEUR());
        holder.TVDate.setText(P.getNOMCOMPLET());

        DBAdapterLigne DBAS = new DBAdapterLigne(C);
        DBAS.open();

        holder.IVBus.setAlpha((float)0.25);
        holder.IVTrain.setAlpha((float)0.25);
        holder.IVTram.setAlpha((float)0.25);

        for( Ligne l : DBAS.getAllLigneBySocieteAller(P.getIDENTIFICATEUR()) ){
            if(l.getTYPE().equals("metro"))
                holder.IVTram.setAlpha((float)1);
            else if (l.getTYPE().equals("train"))
                holder.IVTrain.setAlpha((float)1);
            else if (l.getTYPE().equals("bus"))
                holder.IVBus.setAlpha((float)1);
        }

        DBAdapterSociete DBAd = new DBAdapterSociete(C);
        DBAd.open();

        ArrayList<Societe> LS = DBAd.getAllSociete();
        try {


            byte[] blob = Base64.decode(LS.get(position).getLOGO(), Base64.DEFAULT);


            //  String X = LS.get(0).getLogo();
            //  Log.v("TeSTADDD AE", X.substring(X.length()-10,X.length()));
            //  Log.v("TEST",blob+"");

            Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);


            holder.IV.setImageBitmap(Bitmap.createScaledBitmap(bmp, 400, 400, false));

            // IV.setImageBitmap(bmp);



            //Toast.makeText(this,LS.get(0).toString(),Toast.LENGTH_LONG).show();

            DBAd.close();


        }catch(Exception e){

        }


        DBAS.close();


    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView TVDate;
        TextView TVTitre;
        ImageView IVBus;
        ImageView IVTrain;
        ImageView IVTram;
        CircleImageView IV;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            TVDate = (TextView) itemView.findViewById(R.id.TVDate);
            TVTitre = (TextView) itemView.findViewById(R.id.TVTitre);

            IVBus = (ImageView ) itemView.findViewById(R.id.IVBus);
            IVTrain = (ImageView ) itemView.findViewById(R.id.IVTrain);
            IVTram = (ImageView ) itemView.findViewById(R.id.IVTram);
            IV = (CircleImageView) itemView.findViewById(R.id.imageView14);

        }

        @Override
        public void onClick(View v) {


            Intent i = new Intent(C, SCDetails.class)   ;
            i.putExtra("SocIDEN",List.get(getPosition()).getIDENTIFICATEUR());
            i.putExtra("SocID",List.get(getPosition()).getROW_ID());
            //////////// implements serializable to pass a class between two actvities or juste some informations

            C.startActivity(i);


        }

    }

}
