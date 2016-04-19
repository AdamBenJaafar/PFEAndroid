package com.example.adam.tunisia.View.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.R;

import java.util.List;

public class LignesAdapter extends BaseAdapter{

    List<Station> L ;
    Context context;

    public LignesAdapter(List<Station> l,Context c) {
        L = l;
        context = c;
    }

    @Override
    public int getCount() {
        return L.size();
    }

    @Override
    public Object getItem(int position) {
        return L.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate(R.layout.lignelist, parent, false);


        ImageView I = (ImageView) row.findViewById(R.id.imageView2);
        TextView title = (TextView) row.findViewById(R.id.textView4);
        TextView desc = (TextView) row.findViewById(R.id.textView6);

        Station temps = L.get(position);

        if(position==0 || position == L.size()-1 )
        I.setImageResource(R.mipmap.dot);
        else
            I.setImageResource(R.mipmap.more);



        title.setText(temps.getNOM());
        desc.setText(temps.getSOCIETE_ID()+"");

        return row;
    }
}
