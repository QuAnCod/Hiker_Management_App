
package com.example.hiker_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ObservationListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewObservations;
    private ObservationAdapter observationAdapter;
    private List<Observation> observationList;
    private DatabaseHelper db;
    private int hikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_list);

        recyclerViewObservations = findViewById(R.id.recyclerViewObservations);
        recyclerViewObservations.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database helper
        db = new DatabaseHelper(this);

        // Get the hike ID from the intent
        hikeId = getIntent().getIntExtra("HIKE_ID", -1);

        // Fetch observations and set up the adapter
        if (hikeId != -1) {
            observationList = db.getObservationsForHike(hikeId);
            observationAdapter = new ObservationAdapter(observationList, this);
            recyclerViewObservations.setAdapter(observationAdapter);
        }
    }
}