package com.startupsclub.scdd.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.startupsclub.scdd.RowElements.Speaker;
import com.startupsclub.scdd.R;

import java.util.List;

/**
 * Created by admin on 12/3/2015.
 */
public class SpeakersRVAdapter extends RecyclerView.Adapter<SpeakersRVAdapter.SpeakerViewHolder> {

    public static class SpeakerViewHolder extends RecyclerView.ViewHolder {

        TextView speakerName;
        TextView speakerDescription;
        ImageView speakerImage;


        SpeakerViewHolder(View itemView) {
            super(itemView);
                speakerName = (TextView) itemView.findViewById(R.id.speaker_name);
                speakerDescription = (TextView) itemView.findViewById(R.id.speaker_desc);
                speakerImage = (ImageView) itemView.findViewById(R.id.speaker_pic);
        }
    }

    List<Speaker> speakers;

    public SpeakersRVAdapter(List<Speaker> speakers){
        this.speakers = speakers;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SpeakerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.speaker_card_layout, viewGroup, false);
        SpeakerViewHolder avh = new SpeakerViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(SpeakerViewHolder speakerViewHolder, int i) {
        speakerViewHolder.speakerImage.setImageResource(speakers.get(i).image);
        speakerViewHolder.speakerName.setText(speakers.get(i).name);
        speakerViewHolder.speakerDescription.setText(speakers.get(i).description);
    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }
}