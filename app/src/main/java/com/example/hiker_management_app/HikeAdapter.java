package com.example.hiker_management_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.ViewHolder>{

    private List<Hike> hikeList;
    private Context context;

    public HikeAdapter(List<Hike> hikeList, Context context) {
        this.hikeList = hikeList;
        this.context = context;
    }

    @NonNull
    @Override
    public HikeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HikeAdapter.ViewHolder holder, int position) {
        Hike hike = hikeList.get(position);

        holder.hikeName.setText(hike.getName());
        holder.hikeLocation.setText(hike.getLocation());
        holder.hikeDate.setText(hike.getDate());
        holder.hikeParking.setText(hike.isParking() ? "Yes" : "No");
        holder.hikeDifficulty.setText(hike.getDifficulty());
        holder.hikeLength.setText(hike.getLength());
        holder.hikeDescription.setText(hike.getDescription());
        holder.hikeNumOfParticipants.setText(String.valueOf(hike.getNumOfParticipants()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewHikeActivity.class);
            intent.putExtra("HIKE_ID", String.valueOf(hike.getId()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hikeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hikeName, hikeLocation, hikeDate, hikeParking, hikeLength, hikeDifficulty, hikeDescription, hikeNumOfParticipants;
        // Other UI components

        public ViewHolder(View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.hikeName);
            hikeLocation = itemView.findViewById(R.id.hikeLocation);
            hikeDate = itemView.findViewById(R.id.hikeDate);
            hikeParking = itemView.findViewById(R.id.hikeParking);
            hikeLength = itemView.findViewById(R.id.hikeLength);
            hikeDifficulty = itemView.findViewById(R.id.hikeDifficulty);
            hikeDescription = itemView.findViewById(R.id.hikeDescription);
            hikeNumOfParticipants = itemView.findViewById(R.id.NumOfParticipants);
        }
    }

    // Update data for search
    public void updateData(List<Hike> hikeList) {
        this.hikeList = hikeList;
        notifyDataSetChanged();
    }

}
