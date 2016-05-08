package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Activities.Home;
import com.example.adam.tunisia.View.Activities.Stations;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.Collections;
import java.util.List;

public class LigneRVAdapter extends RecyclerView.Adapter<LigneRVAdapter.MyViewHolder>{

    Context C ;

    private final LayoutInflater inflater;
    List<Ligne> List = Collections.emptyList();

    public LigneRVAdapter(Context context, List<Ligne> L){
        inflater = LayoutInflater.from(context);
        this.C = context;
        this.List = L ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ligne_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder  holder, int position) {

        Ligne P = List.get(position);


        holder.TVTitre.setText(P.getIDENTIFIANT());


            switch (P.getTYPE()) {
                case "bus":
                    holder.IV.setImageResource(R.mipmap.ic_directions_bus_black_24dp);
                    break;
                case "metro":
                    holder.IV.setImageResource(R.mipmap.tram);
                    break;
                case "train":
                    holder.IV.setImageResource(R.mipmap.tracking);
                    break;

            }

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView TVTitre;
        ImageView IV;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            TVTitre = (TextView) itemView.findViewById(R.id.TVTitre);
            IV = (ImageView) itemView.findViewById(R.id.imageView3);

            // BDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(C, Stations.class);
            intent.putExtra("ligne",List.get(getPosition()).getIDENTIFIANT());
            C.startActivity(intent);
        }

    }

}
