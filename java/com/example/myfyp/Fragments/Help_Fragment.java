package com.example.myfyp.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myfyp.R;


public class Help_Fragment extends Fragment {


    TextView t1, t2, t3, t4, t5, t6, t7, t8;
    TextView a1, a2, a3, a4, a5, a6, a7, a8;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_help, container, false);

        t1= view.findViewById(R.id.t1);
        t2= view.findViewById(R.id.t2);
        t3= view.findViewById(R.id.t3);
        t4= view.findViewById(R.id.t4);
        t5= view.findViewById(R.id.t5);
        t6= view.findViewById(R.id.t6);
        t7= view.findViewById(R.id.t7);
        t8= view.findViewById(R.id.t8);

        a1= view.findViewById(R.id.a1);
        a2= view.findViewById(R.id.a2);
        a3= view.findViewById(R.id.a3);
        a4= view.findViewById(R.id.a4);
        a5= view.findViewById(R.id.a5);
        a6= view.findViewById(R.id.a6);
        a7= view.findViewById(R.id.a7);
        a8= view.findViewById(R.id.a8);


       /* t1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
       */
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    t1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_down,0);
                    a1.setVisibility(View.VISIBLE);

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setVisibility(View.GONE);
                a3.setVisibility(View.GONE);
                a4.setVisibility(View.GONE);
                a5.setVisibility(View.GONE);
                a6.setVisibility(View.GONE);
                a7.setVisibility(View.GONE);
                a8.setVisibility(View.GONE);

                a2.setVisibility(View.VISIBLE);
                t2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_down,0);

                t1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t3.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t5.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t6.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t7.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t8.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);

            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setVisibility(View.GONE);
                a2.setVisibility(View.GONE);
                a4.setVisibility(View.GONE);
                a5.setVisibility(View.GONE);
                a6.setVisibility(View.GONE);
                a7.setVisibility(View.GONE);
                a8.setVisibility(View.GONE);

                a3.setVisibility(View.VISIBLE);
                t3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_down, 0);

                t1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_up, 0);
                t2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_up, 0);
                t4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_up, 0);
                t5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_up, 0);
                t6.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_up, 0);
                t7.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_up, 0);
                t8.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dorp_up, 0);

            }

        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setVisibility(View.GONE);
                a2.setVisibility(View.GONE);
                a3.setVisibility(View.GONE);
                a5.setVisibility(View.GONE);
                a6.setVisibility(View.GONE);
                a7.setVisibility(View.GONE);
                a8.setVisibility(View.GONE);

                a4.setVisibility(View.VISIBLE);
                t4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_down,0);

                t1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t3.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t5.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t6.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t7.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t8.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setVisibility(View.GONE);
                a2.setVisibility(View.GONE);
                a3.setVisibility(View.GONE);
                a4.setVisibility(View.GONE);
                a6.setVisibility(View.GONE);
                a7.setVisibility(View.GONE);
                a8.setVisibility(View.GONE);

                a5.setVisibility(View.VISIBLE);
                t5.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_down,0);

                t1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t3.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t6.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t7.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t8.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setVisibility(View.GONE);
                a2.setVisibility(View.GONE);
                a3.setVisibility(View.GONE);
                a4.setVisibility(View.GONE);
                a5.setVisibility(View.GONE);
                a7.setVisibility(View.GONE);
                a8.setVisibility(View.GONE);

                a6.setVisibility(View.VISIBLE);
                t6.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_down,0);

                t1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t3.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t5.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t7.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t8.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
            }
        });
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setVisibility(View.GONE);
                a2.setVisibility(View.GONE);
                a3.setVisibility(View.GONE);
                a4.setVisibility(View.GONE);
                a5.setVisibility(View.GONE);
                a6.setVisibility(View.GONE);
                a8.setVisibility(View.GONE);

                a7.setVisibility(View.VISIBLE);
                t7.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_down,0);

                t1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t3.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t5.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t6.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t8.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
            }
        });
        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setVisibility(View.GONE);
                a2.setVisibility(View.GONE);
                a3.setVisibility(View.GONE);
                a4.setVisibility(View.GONE);
                a5.setVisibility(View.GONE);
                a6.setVisibility(View.GONE);
                a7.setVisibility(View.GONE);

                a8.setVisibility(View.VISIBLE);
                t8.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_down,0);

                t1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t3.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t5.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t6.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
                t7.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrow_dorp_up,0);
            }
        });


        return view;



    }

}
