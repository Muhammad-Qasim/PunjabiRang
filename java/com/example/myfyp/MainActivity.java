package com.example.myfyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myfyp.Fragments.NotificationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new NotificationFragment()).commit();


    }
}
