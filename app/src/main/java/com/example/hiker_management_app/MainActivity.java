package com.example.hiker_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HikeAdapter adapter;
    private List<Hike> hikeList;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Search
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Hike> searchResults = db.searchHikes(query);
                adapter.updateData(searchResults); // Update the adapter with the search results
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Implement for real-time search
                return false;
            }
        });

        // Initializing RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initializing DatabaseHelper
        db = new DatabaseHelper(this);

        // FloatingActionButton to add a new hike
        FloatingActionButton addHikeButton = findViewById(R.id.addHikeButton);
        addHikeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddHikeActivity.class);
            startActivityForResult(intent, 1);
        });

        // Load hikes from database
        loadHikesFromDatabase();
    }

    private void loadHikesFromDatabase() {
        hikeList = db.getAllHikes();
        adapter = new HikeAdapter(hikeList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Refresh the hike list
            loadHikesFromDatabase();
        }
    }
}