package com.example.myfyp.Adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myfyp.MainActivity;
import com.example.myfyp.Model.Post;
import com.example.myfyp.R;

import java.io.InputStream;

public class drawerNotificationHelper extends ContextWrapper {


    Post post= new Post();

    private static  String CHANNEL_ID= "com.example.myfyp.Adapter";
    private static String CHANNEL_NAME="PUNJABI RANG";
    private NotificationManager manager;



    Bitmap postimage= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground);



    public drawerNotificationHelper(Context base) {
        super(base);
        createChanels();

    }

    private void createChanels() {
        NotificationChannel qasimChanel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            qasimChanel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            qasimChanel.enableLights(true);
            qasimChanel.enableVibration(true);
            qasimChanel.setLightColor(Color.GREEN);
            qasimChanel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(qasimChanel);
        }
    }

    public NotificationManager getManager() {

        if(manager== null)
            manager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getQasimNotification(String title ){

        Intent intent= new Intent(this, MainActivity.class);
        PendingIntent resultpendingIntent=PendingIntent.getActivity(this,1,intent, PendingIntent.FLAG_UPDATE_CURRENT);


        return new Notification.Builder(getApplicationContext(),CHANNEL_ID)
          //      .setContentText(body)
                .setContentTitle("You have new Notification")
                .setSmallIcon(R.drawable.app_logo)
                .setLargeIcon(postimage)
                .setContentIntent(resultpendingIntent)

                .setAutoCancel(true);

    }





}

