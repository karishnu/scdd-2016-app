package com.startupsclub.scdd.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.startupsclub.scdd.AgendaFragment;
import com.startupsclub.scdd.RowElements.City;
import com.startupsclub.scdd.MainActivity;
import com.startupsclub.scdd.R;

import java.util.List;

/**
 * Created by admin on 12/3/2015.
 */
public class CityRVAdapter extends RecyclerView.Adapter<CityRVAdapter.CityViewHolder> {

    public static class CityViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;
        TextView cityDate;

        CityViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView)itemView.findViewById(R.id.city_name);
            cityDate = (TextView)itemView.findViewById(R.id.city_date);
        }
    }

    List<City> cities;
    Context context;

    public CityRVAdapter(List<City> cities, Context context){
        this.cities = cities;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cities_card_layout, viewGroup, false);
        CityViewHolder pvh = new CityViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CityViewHolder cityViewHolder, int i) {
        cityViewHolder.cityName.setText(cities.get(i).name);
        cityViewHolder.cityDate.setText(cities.get(i).date);

        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AgendaFragment();
                ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities,fragment,"1st page")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}