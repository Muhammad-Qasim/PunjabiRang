package com.example.myfyp.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;


import com.example.myfyp.Adapter.drawerNotificationHelper;
import com.example.myfyp.Clothes;
import com.example.myfyp.Dance;
import com.example.myfyp.FoodActivity;
import com.example.myfyp.Music;
import com.example.myfyp.Places;
import com.example.myfyp.R;
import com.example.myfyp.Sports;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home_frag extends Fragment {

    CircleImageView places, food, musical_instruments, sports, dances, clothes;

    ViewFlipper flipper;


    drawerNotificationHelper helper;



    @RequiresApi(api = Build.VERSION_CODES.O)



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_frag, container, false);

        places= view.findViewById(R.id.img_place);
        food= view.findViewById(R.id.img_food);
        musical_instruments= view.findViewById(R.id.img_musical);
        sports= view.findViewById(R.id.img_sport);
        dances= view.findViewById(R.id.img_dance);
        clothes= view.findViewById(R.id.img_cloth);

       /* String str_content= notification.getText().toString();
        String str_postid= notification.getPostid();
        String str_name= notification.getUserid();*/
//                    Bitmap bitmap= BitmapFactory.decodeFile(post.getPostimage());

        String title= "PINJABI RANG";

      //  String str_content= "You have new Notification";
       /* String str_postid= notification.getPostid();
        String str_name= notification.getUserid();
*/



        helper=new drawerNotificationHelper(getContext());
        android.app.Notification.Builder builder = helper.getQasimNotification(title);
        helper.getManager().notify(new Random().nextInt(),builder.build());



        int images[] = {R.drawable.food_slider, R.drawable.sports_slider, R.drawable.dance_slider, R.drawable.clothes_slider, R.drawable.punjabidhool_slider, R.drawable.place_slider};
        flipper= view.findViewById(R.id.flipper);

        /*for(int i=0 ; i<images.length; i++){
            flipperImages(images[i]);*/

            for(int image: images){
                flipperImages(image);
            }
       // }


        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), FoodActivity.class);
                startActivity(intent);

            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), Sports.class);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);




            }
        });

        dances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(getContext(), Dance.class);
                startActivity(intent);

            }
        });

        musical_instruments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Music.class);
                startActivity(intent);

            }
        });

        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Places.class);
                startActivity(intent);

            }
        });

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Clothes.class);
                startActivity(intent);

            }
        });

       return view;

    }


    public void flipperImages(int images){
        ImageView imageView= new ImageView(getContext());
        imageView.setBackgroundResource(images);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

    }




        }


