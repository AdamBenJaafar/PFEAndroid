package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.Collections;
import java.util.List;

public class StationsRVAdapter extends RecyclerView.Adapter<StationsRVAdapter.MyViewHolder>{

    Context C ;

    private final LayoutInflater inflater;
    List<Station> List = Collections.emptyList();

    public StationsRVAdapter(Context context, List<Station> L){
        inflater = LayoutInflater.from(context);
        this.C = context;
        this.List = L ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.station_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder  holder, int position) {

        Station P = List.get(position);

        holder.TVTitre.setText(P.getNOM());

        if(position!=0)
            holder.IV.setImageResource(R.mipmap.station);
        else
            holder.IV.setImageResource(R.mipmap.majeur);

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
            new LovelyStandardDialog(C)
                    .setTopColor(ContextCompat.getColor(C,R.color.colorPrimary))
                    .setButtonsColor(ContextCompat.getColor(C,R.color.colorAccent))
                    .setIcon(R.mipmap.station)
                    .setTitle("")
                    .setMessage("")
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }

    }

}
