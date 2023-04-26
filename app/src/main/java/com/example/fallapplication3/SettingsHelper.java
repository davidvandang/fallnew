package com.example.fallapplication3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsHelper {

    private static final String TIMER_COUNT_KEY = "timer_count";
    private static final int DEFAULT_TIMER_COUNT = 10;

    public static int getTimerCount(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(TIMER_COUNT_KEY, DEFAULT_TIMER_COUNT);
    }

    public static void setTimerCount(Context context, int timerCount) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TIMER_COUNT_KEY, timerCount);
        editor.apply();
    }
}
