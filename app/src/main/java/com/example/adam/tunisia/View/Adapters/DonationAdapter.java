package com.example.adam.tunisia.View.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 17.04.2016.
 */
public class DonationAdapter extends ArrayAdapter<Societe> {

    public DonationAdapter(Context context, List<Societe> donationOptions) {
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


        Societe option = getItem(position);


        DBAdapterLigne DBAL = new DBAdapterLigne(this.getContext());
        DBAL.open();

        boolean tram = false;
        boolean train = false;
        boolean bus = false;

        vh.Bus.setAlpha((float)0.25);
        vh.Train.setAlpha((float)0.25);
        vh.Metro.setAlpha((float)0.25);

        ArrayList<Ligne> L = DBAL.getAllLigneBySocieteAller(option.getIDENTIFICATEUR());
        for( Ligne l : L){
            if(l.getTYPE().equals("metro"))
                vh.Metro.setAlpha((float)1);
            else if (l.getTYPE().equals("train"))
                vh.Train.setAlpha((float)1);
            else if (l.getTYPE().equals("bus"))
                vh.Bus.setAlpha((float)1);
        }

        DBAL.close();

        vh.description.setText(option.getIDENTIFICATEUR());

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