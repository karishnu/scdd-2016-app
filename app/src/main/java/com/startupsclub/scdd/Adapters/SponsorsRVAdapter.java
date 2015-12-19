package com.startupsclub.scdd.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.startupsclub.scdd.R;
import com.startupsclub.scdd.RowElements.Sponsor;

import java.util.List;

/**
 * Created by admin on 12/3/2015.
 */
public class SponsorsRVAdapter extends RecyclerView.Adapter<SponsorsRVAdapter.SponsorViewHolder> {

    public static class SponsorViewHolder extends RecyclerView.ViewHolder {

        TextView sponsorName;
        ImageView sponsorImage;


        SponsorViewHolder(View itemView) {
            super(itemView);
                sponsorName = (TextView) itemView.findViewById(R.id.sponsor_name);
                sponsorImage = (ImageView) itemView.findViewById(R.id.sponsor_image);
        }
    }

    List<Sponsor> sponsors;

    public SponsorsRVAdapter(List<Sponsor> sponsors){
        this.sponsors = sponsors;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SponsorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sponsor_card_layout, viewGroup, false);
        SponsorViewHolder avh = new SponsorViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(SponsorViewHolder sponsorViewHolder, int i) {
        sponsorViewHolder.sponsorImage.setImageResource(sponsors.get(i).image);
        sponsorViewHolder.sponsorName.setText(sponsors.get(i).name);
    }

    @Override
    public int getItemCount() {
        return sponsors.size();
    }
}