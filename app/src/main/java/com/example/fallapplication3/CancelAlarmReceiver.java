package com.example.fallapplication3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CancelAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FallDetectionService fallDetectionService = FallDetectionService.getInstance();

        if (fallDetectionService != null) {
            fallDetectionService.cancelAlarm();
        }
    }
}
