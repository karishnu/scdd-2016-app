package com.startupsclub.scdd.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.startupsclub.scdd.RowElements.Agenda;
import com.startupsclub.scdd.R;

import java.util.List;

/**
 * Created by admin on 12/3/2015.
 */
public class AgendaRVAdapter extends RecyclerView.Adapter<AgendaRVAdapter.AgendaViewHolder> {

    public static class AgendaViewHolder extends RecyclerView.ViewHolder {

        TextView agendaTime;
        TextView agendaEvent;
        ImageView agenda_green_line;


        AgendaViewHolder(View itemView) {
            super(itemView);
            agendaTime = (TextView)itemView.findViewById(R.id.agenda_time);
            agendaEvent = (TextView)itemView.findViewById(R.id.agenda_event);
            agenda_green_line = (ImageView) itemView.findViewById(R.id.agenda_green_line);
        }
    }

    List<Agenda> agendas;

    public AgendaRVAdapter(List<Agenda> agendas){
        this.agendas = agendas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AgendaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agenda_row_layout, viewGroup, false);
        AgendaViewHolder avh = new AgendaViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(AgendaViewHolder personViewHolder, int i) {
        personViewHolder.agendaTime.setText(agendas.get(i).time);
        personViewHolder.agendaEvent.setText(agendas.get(i).event);

        if (i==(agendas.size()-1)){
            personViewHolder.agenda_green_line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }
}