package com.example.hiker_management_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ViewHolder>{
    private List<Observation> observationList;
    private Context context;

    // Constructor
    public ObservationAdapter(List<Observation> observationList, Context context) {
        this.observationList = observationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ObservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationAdapter.ViewHolder holder, int position) {
        Observation observation = observationList.get(position);
        holder.textViewObservation.setText(observation.getName());
        holder.textViewTime.setText(observation.getDate());
        holder.textViewComments.setText(observation.getDescription());
    }

    @Override
    public int getItemCount() {
        return observationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewObservation, textViewTime, textViewComments;
        // Other views as needed

        public ViewHolder(View itemView) {
            super(itemView);
            textViewObservation = itemView.findViewById(R.id.textViewObservation);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComments = itemView.findViewById(R.id.textViewComments);
        }
    }
}
