package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Entities.Station_Ligne_Horaire;
import com.example.adam.tunisia.Model.Entities.Station_Ligne_Horaire;
import com.example.adam.tunisia.Model.Entities.Station_Ligne_Horaire;
import com.example.adam.tunisia.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 17.04.2016.
 */
public class StationDialogAdapter extends ArrayAdapter<Station_Ligne_Horaire> {

    public StationDialogAdapter(Context context, List<Station_Ligne_Horaire> donationOptions) {
        super(context, 0, donationOptions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.horaire, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else vh = (ViewHolder) convertView.getTag();

        Station_Ligne_Horaire option = getItem(position);



       vh.TV.setText(option.getHORAIRE());

        return convertView;
    }

    private static final class ViewHolder {
        TextView TV;


        public ViewHolder(View v) {

            TV = (TextView ) v.findViewById(R.id.TVTitre);
        }
    }


}