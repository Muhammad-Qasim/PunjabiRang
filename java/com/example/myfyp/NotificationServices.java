package com.example.myfyp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class NotificationServices extends Service {

    public Context context = this;
    public android.os.Handler handler = new Handler();
    public static  Runnable runnable= null;


    public NotificationServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;

    }

    @Override
    public void onCreate() {
        Log.e("Services", "Service Created!");

        runnable= new Runnable() {
            @Override
            public void run() {
                Log.e("Services", "Service is still running");
                Toast.makeText(context, "Service is still running", Toast.LENGTH_SHORT).show();

                handler.postDelayed(runnable, 50000);

            }
        };
        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onStart(Intent intent, int startId) {
            Log.e("Service", "Service Started by User");
            
    }
}
