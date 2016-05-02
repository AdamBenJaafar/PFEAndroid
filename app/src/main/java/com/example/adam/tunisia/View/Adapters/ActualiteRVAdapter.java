package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.Collections;
import java.util.List;

public class ActualiteRVAdapter extends RecyclerView.Adapter<ActualiteRVAdapter.MyViewHolder>{

    Context C ;

    private final LayoutInflater inflater;
    List<Actualite> List = Collections.emptyList();

    public ActualiteRVAdapter(Context context, List<Actualite> L){
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

        Actualite P = List.get(position);

        holder.TVTitre.setText(P.getSOC().getIDENTIFICATEUR());
        holder.TVLigne.setText(P.getSOC().getIDENTIFICATEUR());
        holder.TVDate.setText(P.getDATE());

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
