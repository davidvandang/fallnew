package com.example.fallapplication3;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class FallDetectionService extends Service {

    private SensorManager sensorManager;
    private fall fallDetector;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "FallDetectionChannel";

    // Add instance variable and getInstance() method
    private static FallDetectionService instance;

    public static FallDetectionService getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        fallDetector = new fall(getApplicationContext());

        // Set the instance variable
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            sensorManager.registerListener(fallDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Fall Detection")
                .setContentText("Fall detection is running")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(NOTIFICATION_ID, notification);

        return START_NOT_STICKY;
    }

    public void onFallDetected() {
        // Send a notification and start the countdown
        showFallNotificationWithCountdown();

        // Make the phone call if permission is granted
        if (hasPhoneCallPermission()) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "07874063378"));

            try {
                startActivity(callIntent);
            } catch (SecurityException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Phone call permission is required.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Phone call permission is required.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPhoneCallPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(fallDetector);
        instance = null;
    }
    public void cancelAlarm() {
        fallDetector.stopDetection();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showFallNotificationWithCountdown() {
        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        // Create a custom layout for the notification that includes a timer
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_timer);
        builder.setCustomContentView(remoteViews);

        // Set the initial time on the timer
        int countdownTime = SettingsHelper.getTimerCount(this);
        remoteViews.setTextViewText(R.id.timer_textview, String.format("%02d:%02d", countdownTime / 60, countdownTime % 60));

        // Create an intent to cancel the alarm
        Intent cancelIntent = new Intent(this, FallDetectionService.class);
        cancelIntent.setAction("CANCEL_ALARM");
        PendingIntent cancelPendingIntent = PendingIntent.getService(this, 0, cancelIntent, 0);

        // Add a cancel button to the notification that cancels the alarm
        remoteViews.setOnClickPendingIntent(R.id.cancel_button, cancelPendingIntent);

        // Build the notification
        Notification notification = builder
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        // Show the notification with the timer
        startForeground(NOTIFICATION_ID, notification);

        // Start the countdown timer and update the timer display every second
        new CountDownTimer(countdownTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display every second
                int secondsRemaining = (int) millisUntilFinished / 1000;
                remoteViews.setTextViewText(R.id.timer_textview, String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60));
            }

            @Override
            public void onFinish() {
                // When the timer finishes, remove the notification and cancel the alarm
                stopForeground(true);
                stopSelf();
                FallDetectionService.getInstance().cancelAlarm();
            }
        }.start();
    }

}
