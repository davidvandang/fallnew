package com.example.fallapplication3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class fall implements SensorEventListener {
    private boolean isFallDetected = false;
    private double previousAcceleration = 0;
    private static final double ALPHA = 0.8;
    private static final double FALL_THRESHOLD = 1;

    private Context context;

    public fall(Context context) {
        this.context = context;
        this.previousAcceleration = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("FallDetection", "Sensor data received");

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double acceleration = Math.sqrt(x * x + y * y + z * z);
        Log.i("FallDetection", "Current acceleration: " + acceleration);

        acceleration = acceleration * (1 - ALPHA) + previousAcceleration * ALPHA;
        Log.i("FallDetection", "Filtered acceleration: " + acceleration);

        double delta = Math.abs(acceleration - previousAcceleration);
        Log.i("FallDetection", "Acceleration delta: " + delta);

        if (delta > FALL_THRESHOLD && !isFallDetected) {
            isFallDetected = true;
            Log.i("FallDetection", "Fall detected");
            FallDetectionService.getInstance().onFallDetected();
        }

        previousAcceleration = acceleration;
    }

    public void stopDetection() {
        isFallDetected = false;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not needed for this application
    }
}
