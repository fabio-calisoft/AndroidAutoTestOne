package com.crokky.androidautotestone;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * from here:
 * https://developer.android.com/training/auto/audio/index.html
 *
 * https://developer.android.com/training/auto/testing/index.html
 * adb forward tcp:5277 tcp:5277
 * Library/Android/sdk/extras/google/auto/desktop-head-unit
 *
 *
 * https://classroom.udacity.com/courses/ud875C/lessons/4395568889/concepts/44256886210923
 * http://casa.crokky.com/Anatomy_of_an_Auto_media_player.mp4
 * 
 */
public class MainActivity extends Activity {

    private static final String TAG = "fdl Android Auto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate ------- ");

        /**
         *  It is possible to determine if your app is selected as the current media app.
         *  Android Auto broadcasts an intent with com.google.android.gms.car.media.STATUS
         *  action when a user connects or disconnects from a media app.
         *  The broadcast intent is scoped to the package name of the media app selected.
         *  You can register a broadcast receiver in your app, preferably in your
         *  MediaBrowserService implementation and listen for this intent and adjust
         *  advertisements, metadata, and UI buttons in your app to operate safely in a vehicle.
         */
        IntentFilter filter = new IntentFilter("com.google.android.gms.car.media.STATUS");
        BroadcastReceiver receiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"onReceive ------- ");

                String status = intent.getStringExtra("media_connection_status");
                Log.d(TAG,"onReceive ------- status:" + status);

                boolean isConnectedToCar = "media_connected".equals(status);
                // adjust settings based on the connection status
                if (isConnectedToCar)
                    Log.d(TAG,"onReceive ------- YES connected to car");
                else
                    Log.d(TAG,"onReceive ------- NO, not connected to car");
            }
        };
        registerReceiver(receiver, filter);

        isCarUiMode(this);

    }


    /**
     * the app should determine if the phone is in car mode b
     * @param c Context
     * @return true is in car mode
     */
    public static boolean isCarUiMode(Context c) {
        UiModeManager uiModeManager = (UiModeManager) c.getSystemService(Context.UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_CAR) {
            Log.d(TAG, "Running in Car mode");
            return true;
        } else {
            Log.d(TAG, "Running on a non-Car mode");
            return false;
        }
    }




}
