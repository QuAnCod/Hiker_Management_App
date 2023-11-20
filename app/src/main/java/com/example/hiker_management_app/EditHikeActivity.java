package com.example.hiker_management_app;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class EditHikeActivity extends AppCompatActivity {

    private EditText editTextHikeName, editTextHikeLocation, editTextHikeDate, editTextHikeLength, editTextHikeDescription, editTextHikeNumOfParticipants;
    private CheckBox editTextHikeParking;
    private Spinner editTextHikeDifficulty;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);

        // Initialize your views
        editTextHikeName = findViewById(R.id.editTextHikeName);
        editTextHikeLocation = findViewById(R.id.editTextHikeLocation);
        editTextHikeDate = findViewById(R.id.editTextHikeDate);
        editTextHikeParking = findViewById(R.id.editParkingCheckbox);
        editTextHikeDifficulty = findViewById(R.id.editDifficultySpinner);
        editTextHikeLength = findViewById(R.id.editTextHikeLength);
        editTextHikeDescription = findViewById(R.id.editTextHikeDescription);
        editTextHikeNumOfParticipants = findViewById(R.id.editTextHikeParticipants);

        // Initialize Save and Cancel buttons
        Button buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        Button buttonCancelEdit = findViewById(R.id.buttonCancelEdit);

        // Initialize database helper
        db = new DatabaseHelper(this);

        // Get hike ID from intent
        int hikeId = getIntent().getIntExtra("HIKE_ID", -1);

        // Initialize the spinner with difficulty levels
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hikeDifficulty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextHikeDifficulty.setAdapter(adapter);

        // Load hike details
        if (hikeId != -1) {
            Hike hike = db.getHikeById(hikeId); // Fetch hike data

            if (hike != null) {
                // Populate fields with hike data
                editTextHikeName.setText(hike.getName());
                editTextHikeLocation.setText(hike.getLocation());
                editTextHikeDate.setText(hike.getDate());
                editTextHikeParking.setChecked(hike.isParking());
                setSpinnerToValue(editTextHikeDifficulty, hike.getDifficulty());
                editTextHikeLength.setText(hike.getLength());
                editTextHikeDescription.setText(hike.getDescription());
                editTextHikeNumOfParticipants.setText(hike.getNumOfParticipants());
            }
        }

        // Save Button Listener
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHikeChanges();
            }
        });

        // Cancel Button Listener
        buttonCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Simply close the activity without saving changes
            }
        });
    }

    // Method to set the Spinner to a specific value
    private void setSpinnerToValue(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(Objects.equals(adapter.getItem(position), value)) {
                spinner.setSelection(position);
                return;
            }
        }
    }

    private void saveHikeChanges() {
        // Get updated hike details from the input fields
        int hikeId = getIntent().getIntExtra("HIKE_ID", -1);
        String name = editTextHikeName.getText().toString().trim();
        String location = editTextHikeLocation.getText().toString().trim();
        String date = editTextHikeDate.getText().toString().trim();
        boolean parking = editTextHikeParking.isChecked();
        String difficulty = editTextHikeDifficulty.getSelectedItem().toString();
        String length = editTextHikeLength.getText().toString().trim();
        String description = editTextHikeDescription.getText().toString().trim();
        int NumOfParticipants = Integer.parseInt(editTextHikeNumOfParticipants.getText().toString().trim());

        // Validate inputs (example: check if any field is empty)
        if (name.isEmpty() || location.isEmpty() || date.isEmpty()) {
            // Show an error message or a toast
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create updated Hike object
        Hike updatedHike = new Hike(hikeId, name, location, date, parking, difficulty, length, description, NumOfParticipants);
        // ... set other attributes to the Hike object

        // Update hike in the database
        if (db.updateHike(updatedHike)) {
            Toast.makeText(this, "Hike updated successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Set result OK to refresh the list in MainActivity
        } else {
            Toast.makeText(this, "Failed to update hike", Toast.LENGTH_SHORT).show();
        }

        // Close the activity
        finish();
    }
}