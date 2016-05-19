package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Database.DBAdapterPerturbation;
import com.example.adam.tunisia.Model.Entities.Perturbation;
import com.example.adam.tunisia.Model.Entities.Perturbation;
import com.example.adam.tunisia.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 17.04.2016.
 */
public class PertubationsDialogAdapter extends ArrayAdapter<Perturbation> {

    public PertubationsDialogAdapter(Context context, List<Perturbation> donationOptions) {
        super(context, 0, donationOptions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_donate_option, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else vh = (ViewHolder) convertView.getTag();

        Perturbation option = getItem(position);

        vh.description.setText(option.getLIG().getIDENTIFIANT());

        vh.Bus.setVisibility(View.INVISIBLE);
        vh.Train.setVisibility(View.INVISIBLE);
        vh.Metro.setVisibility(View.INVISIBLE);


        return convertView;
    }

    private static final class ViewHolder {
        TextView description;
        ImageView Bus;
        ImageView Metro;
        ImageView Train;

        public ViewHolder(View v) {
            description = (TextView) v.findViewById(R.id.description);
            Bus = ( ImageView ) v.findViewById(R.id.IVBus);
            Metro = ( ImageView ) v.findViewById(R.id.IVMetro);
            Train = ( ImageView ) v.findViewById(R.id.IVTrain);
        }
    }


}