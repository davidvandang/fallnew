package com.example.fallapplication3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Incident {
    private int id;
    private String date;
    private String time;

    public Incident() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        this.date = dateFormat.format(new Date());
        this.time = timeFormat.format(new Date());
    }

    public Incident(int id, String date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
