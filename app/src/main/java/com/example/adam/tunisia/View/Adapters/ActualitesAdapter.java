package com.example.adam.tunisia.View.Adapters;

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.R;

import java.util.List;

public class    ActualitesAdapter extends RecyclerView.Adapter<ActualitesAdapter.MyViewHolder> {

    private List<Actualite> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
          //  year = (TextView) view.findViewById(R.id.year);
        }
    }


    public ActualitesAdapter(List<Actualite> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actualite_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Actualite movie = moviesList.get(position);
        holder.title.setText(movie.getTitre());
        holder.genre.setText(movie.getDATE());
       // holder.year.setText(movie.getDATE());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}