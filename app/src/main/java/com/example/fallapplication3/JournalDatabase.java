package com.example.fallapplication3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class JournalDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "journal.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "journal";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";

    public JournalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addIncident(Incident incident) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, incident.getDate());
        values.put(COLUMN_TIME, incident.getTime());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void deleteIncident(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Incident> getAllIncidents() {
        List<Incident> incidentList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                Incident incident = new Incident(id, date, time);
                incidentList.add(incident);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return incidentList;
    }

}
