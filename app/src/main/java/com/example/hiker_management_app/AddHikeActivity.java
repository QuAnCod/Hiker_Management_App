package com.example.hiker_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AddHikeActivity extends AppCompatActivity {
    private EditText nameInput, locationInput, dateInput;
    private CheckBox parkingCheckbox;
    private Spinner difficultySpinner;
    private EditText lengthInput, descriptionInput, NumOfParticipantsInput;
    private Button saveButton, cancelButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        locationInput = findViewById(R.id.locationInput);
        dateInput = findViewById(R.id.dateInput);
        parkingCheckbox = findViewById(R.id.parkingCheckbox);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        lengthInput = findViewById(R.id.lengthInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        NumOfParticipantsInput = findViewById(R.id.participantsInput);

        // Initialize the spinner with difficulty levels
        // You can define an array in strings.xml or use an ArrayAdapter

        db = new DatabaseHelper(this);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveHike());

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            // show confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure you want to cancel?");
            builder.setMessage("Any unsaved changes will be lost");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                // Close the activity
                finish();
            });
            finish(); // Close the activity
        });
    }

    private void saveHike() {
        String name = nameInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        boolean parking = parkingCheckbox.isChecked();
        String difficulty = difficultySpinner.getSelectedItem().toString();
        String length = lengthInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        int NumOfParticipants = Integer.parseInt(NumOfParticipantsInput.getText().toString().trim());

        // Input validation
        if(name.isEmpty() || location.isEmpty() || date.isEmpty() || difficulty.isEmpty() || length.isEmpty()) {
            // Show error
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("There are field(s) left empty");
            builder.setMessage("Please fill in all the fields");
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Close the dialog
                dialog.dismiss();
            });
            // Show the dialog
            builder.show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Hike Details");

        // Constructing the message
        String message = "Name: " + name + "\n"
                + "Location: " + location + "\n"
                + "Date: " + date + "\n"
                + "Parking: " + (parking ? "Yes" : "No") + "\n"
                + "Difficulty: " + difficulty
                + "Length: " + length
                + "Description: " + description
                + "Number of Participants: " + NumOfParticipants;

        builder.setMessage(message);

        // Positive Button - Confirm and Save
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            // Save to database
            Hike newHike = new Hike(0, name, location, date, parking, difficulty, length, description, NumOfParticipants); // Assume ID is auto-generated
            db.addHike(newHike);

            // Go back to main activity
            finish();
        });

        // Negative Button - Cancel
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Close the dialog
            dialog.dismiss();
        });

        builder.show();
    }
}
