package com.example.hiker_management_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HikeDatabase";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        // Create the hikes table
        String CREATE_TABLE = "CREATE TABLE hikes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, location TEXT, date TEXT, parking boolean, length TEXT, difficulty TEXT, description TEXT, NumOfParticipants INTEGER)";
        String CREATE_OBSERVATIONS_TABLE = "CREATE TABLE observations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hikeId INTEGER," +
                "observation TEXT," +
                "time TEXT," +
                "comments TEXT," +
                "FOREIGN KEY(hikeId) REFERENCES hikes(id))";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_OBSERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS hikes");
        Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Create tables again
        onCreate(db);
    }

    // Add other CRUD methods
    // Add Hike
    public void addHike(@NonNull Hike hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", hike.getName());
        values.put("location", hike.getLocation());
        values.put("date", hike.getDate());
        values.put("parking", hike.isParking());
        values.put("difficulty", hike.getDifficulty());
        values.put("length", hike.getLength());
        values.put("description", hike.getDescription());
        values.put("NumOfParticipants", hike.getNumOfParticipants());

        db.insert("hikes", null, values);
    }
    // Update Hike
    public boolean updateHike(@NonNull Hike hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", hike.getName());
        values.put("location", hike.getLocation());
        values.put("date", hike.getDate());
        values.put("parking", hike.isParking());
        values.put("length", hike.getLength());
        values.put("difficulty", hike.getDifficulty());
        values.put("description", hike.getDescription());
        values.put("NumOfParticipants", hike.getNumOfParticipants());

        int result = db.update("hikes", values, "id = ?", new String[] { String.valueOf(hike.getId()) });

        return result > 0;
    }
    // Delete Hike
    public void deleteHike(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("hikes", "id = ?", new String[] { String.valueOf(id) });

    }
    // fetch all hikes
    public List<Hike> getAllHikes() {
        List<Hike> hikeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hikes", null);

        if (cursor.moveToFirst()) {
            do {
                Hike hike = new Hike(
                        cursor.getInt(0), // id
                        cursor.getString(1), // name
                        cursor.getString(2), // location
                        cursor.getString(3), // date
                        cursor.getInt(4) == 1, // parking
                        cursor.getString(5), // length
                        cursor.getString(6), // difficulty
                        cursor.getString(7), // description
                        cursor.getInt(8) // NumOfParticipants
                );
                hikeList.add(hike);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return hikeList;
    }
    // fetch hike by id
    public Hike getHikeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("hikes", new String[]{"id", "name", "location", "date", "parking", "length", "difficulty", "description", "NumOfParticipants"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        Hike hike = new Hike(
                cursor.getInt(0), // id
                cursor.getString(1), // name
                cursor.getString(2), // location
                cursor.getString(3), // date
                cursor.getInt(4) == 1, // parking
                cursor.getString(5), // length
                cursor.getString(6), // difficulty
                cursor.getString(7), // description
                cursor.getInt(8) // NumOfParticipants
        );

        cursor.close();
        return hike;
    }

    // Search Hike
    public List<Hike> searchHikes(String query) {
        List<Hike> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hikes WHERE name LIKE ? OR location LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%"});

        if (cursor.moveToFirst()) {
            do {
                // Extract data and add to searchResults
                Hike hike = new Hike(
                        cursor.getInt(0), // id
                        cursor.getString(1), // name
                        cursor.getString(2), // location
                        cursor.getString(3), // date
                        cursor.getInt(4) == 1, // parking
                        cursor.getString(5), // length
                        cursor.getString(6), // difficulty
                        cursor.getString(7), // description
                        cursor.getInt(8) // NumOfParticipants
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return searchResults;
    }


    // Add Observation
    public void addObservation(@NonNull Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hikeId", observation.getHikeId());
        values.put("observation", observation.getName());
        values.put("time", observation.getDate());
        values.put("comments", observation.getDescription());

        db.insert("observations", null, values);
    }

    // fetch all observations
    public List<Observation> getObservationsForHike(int hikeId) {
        List<Observation> observations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("observations", new String[]{"id", "hikeId", "observation", "time", "comments"},
                "hikeId = ?", new String[]{String.valueOf(hikeId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Observation observation = new Observation(
                        cursor.getInt(0), // id
                        cursor.getInt(1), // hikeId
                        cursor.getString(2), // observation
                        cursor.getString(3), // time
                        cursor.getString(4)  // comments
                );
                observations.add(observation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return observations;
    }

    // fetch observation by id
    public Observation getObservationById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("observations", new String[]{"id", "hikeId", "observation", "time", "comments"},
                "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        Observation observation = new Observation(
                cursor.getInt(0), // id
                cursor.getInt(1), // hikeId
                cursor.getString(2), // observation
                cursor.getString(3), // time
                cursor.getString(4)  // comments
                // Add other fields as necessary
        );

        cursor.close();
        return observation;
    }

    // Update Observation
    public boolean updateObservation(@NonNull Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hikeId", observation.getHikeId());
        values.put("observation", observation.getName());
        values.put("time", observation.getDate());
        values.put("comments", observation.getDescription());

        int result = db.update("observations", values, "id = ?", new String[] { String.valueOf(observation.getId()) });

        return result > 0;
    }

    // Delete Observation
    public boolean deleteObservation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("observations", "id = ?", new String[] { String.valueOf(id) });

        return result > 0;
    }
}
