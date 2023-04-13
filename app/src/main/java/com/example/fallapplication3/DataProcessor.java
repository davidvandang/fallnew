package com.example.fallapplication3;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    public static final String CSV_FILE_PATH = "Accelerometer.csv";
    public static final String[] ACTIVITY_LABELS = {"standing", "walking", "falling"};

    public static List<DataPoint> readData() throws IOException, CsvValidationException {
        List<DataPoint> dataPoints = new ArrayList<>();

        CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String timestamp = nextLine[0];
            double accelX = Double.parseDouble(nextLine[1]);
            double accelY = Double.parseDouble(nextLine[2]);
            double accelZ = Double.parseDouble(nextLine[3]);
            String activityLabel = nextLine[4];

            DataPoint dataPoint = new DataPoint(timestamp, accelX, accelY, accelZ, activityLabel);
            dataPoints.add(dataPoint);
        }

        return dataPoints;
    }
}