package com.startupsclub.scdd;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 12/3/2015.
 */
public class AgendaRVAdapter extends RecyclerView.Adapter<AgendaRVAdapter.AgendaViewHolder> {

    public static class AgendaViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView agendaTime;
        TextView agendaEvent;


        AgendaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.agenda_cv);
            agendaTime = (TextView)itemView.findViewById(R.id.agenda_time);
            agendaEvent = (TextView)itemView.findViewById(R.id.agenda_event);
        }
    }

    List<Agenda> agendas;

    AgendaRVAdapter(List<Agenda> agendas){
        this.agendas = agendas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AgendaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agenda_card_layout, viewGroup, false);
        AgendaViewHolder avh = new AgendaViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(AgendaViewHolder personViewHolder, int i) {
        personViewHolder.agendaTime.setText(agendas.get(i).time);
        personViewHolder.agendaEvent.setText(agendas.get(i).event);
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }
}