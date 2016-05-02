package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Entities.Perturbation;
import com.example.adam.tunisia.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.Collections;
import java.util.List;

public class PerturbationRVAdapter extends RecyclerView.Adapter<PerturbationRVAdapter.MyViewHolder>{

    Context C ;

    private final LayoutInflater inflater;
    List<Perturbation> List = Collections.emptyList();

    public PerturbationRVAdapter(Context context, List<Perturbation> L){
        inflater = LayoutInflater.from(context);
        this.C = context;
        this.List = L ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.perturbation_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder  holder, int position) {

        Perturbation P = List.get(position);

        holder.TVLigne.setText(P.getLIG().getSOC().getIDENTIFICATEUR());
        holder.TVDate.setText(P.getDATE());
        holder.TVTitre.setText(P.getLIG().getIDENTIFIANT());


        if(P.getLIG().getTYPE()!=null) {
            Log.v("TYPE", P.getLIG().getTYPE());
            switch (P.getLIG().getTYPE()) {
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

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView TVDate;
        TextView TVLigne;
        TextView TVTitre;
        ImageView IV;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            TVDate = (TextView) itemView.findViewById(R.id.TVDate);
            TVLigne = (TextView) itemView.findViewById(R.id.TVLigne);
            TVTitre = (TextView) itemView.findViewById(R.id.TVTitre);
            IV = (ImageView) itemView.findViewById(R.id.imageView3);

            // BDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            new LovelyStandardDialog(C)
                    .setTopColor(ContextCompat.getColor(C,R.color.colorPrimary))
                    .setButtonsColor(ContextCompat.getColor(C,R.color.colorAccent))
                    .setIcon(R.mipmap.perturbation)
                    .setTitle(List.get(getPosition()).getDATE())
                    .setMessage(List.get(getPosition()).getTEXTE())
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }

    }

}
