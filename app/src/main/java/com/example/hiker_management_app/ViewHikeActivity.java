package com.example.hiker_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewHikeActivity extends AppCompatActivity {
    private TextView textViewHikeName, textViewHikeLocation, textViewHikeDate,  textViewHikeParking, textViewHikeDifficulty, textViewHikeLength, textViewHikeDescription, textViewHikeNumOfParticipants;
    private Button buttonEditHike, buttonDeleteHike;
    private DatabaseHelper db;
    private String hikeId; // The ID of the displayed hike

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hike);

        // Initialize views
        textViewHikeName = findViewById(R.id.textViewHikeName);
        textViewHikeLocation = findViewById(R.id.textViewHikeLocation);
        textViewHikeDate = findViewById(R.id.textViewHikeDate);
        textViewHikeParking = findViewById(R.id.textViewHikeParking);
        textViewHikeDifficulty = findViewById(R.id.textViewHikeDifficulty);
        textViewHikeLength = findViewById(R.id.textViewHikeLength);
        textViewHikeDescription = findViewById(R.id.textViewHikeDescription);
        textViewHikeNumOfParticipants = findViewById(R.id.textViewHikeParticipants);

        // Initialize buttons
        buttonEditHike = findViewById(R.id.buttonEditHike);
        buttonDeleteHike = findViewById(R.id.buttonDeleteHike);

        // Database Helper
        db = new DatabaseHelper(this);

        // Get the passed hike ID
        hikeId = String.valueOf(getIntent().getIntExtra("HIKE_ID", -1));
        loadHikeDetails();

        // Edit Button
        buttonEditHike.setOnClickListener(v -> {
            Intent intent = new Intent(ViewHikeActivity.this, EditHikeActivity.class);
            intent.putExtra("HIKE_ID", hikeId);
            startActivityForResult(intent, 1);
        });

        // Delete Button
        buttonDeleteHike.setOnClickListener(v -> new AlertDialog.Builder(ViewHikeActivity.this)
                .setTitle("Delete Hike")
                .setMessage("Are you sure you want to delete this hike?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    db.deleteHike(Integer.parseInt(hikeId));
                    setResult(RESULT_OK);
                    finish();
                })
                .setNegativeButton("No", null)
                .show());
        // Observation Button
        Button addObservationButton = findViewById(R.id.buttonObservations);
        addObservationButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewHikeActivity.this, ObservationListActivity.class);
            intent.putExtra("HIKE_ID", hikeId);
            startActivity(intent);
        });
    }

    private void loadHikeDetails() {
        Hike hike = db.getHikeById(Integer.parseInt(hikeId));
        if (hike != null) {
            textViewHikeName.setText(hike.getName());
            textViewHikeLocation.setText(hike.getLocation());
            textViewHikeDate.setText(hike.getDate());
            textViewHikeParking.setText(hike.isParking() ? "Yes" : "No");
            textViewHikeDifficulty.setText(hike.getDifficulty());
            textViewHikeLength.setText(hike.getLength());
            textViewHikeDescription.setText(hike.getDescription());
            textViewHikeNumOfParticipants.setText(String.valueOf(hike.getNumOfParticipants()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Refresh the hike details
            loadHikeDetails();
        }
    }
}