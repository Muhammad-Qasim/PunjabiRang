package com.example.myfyp.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfyp.Adapter.NotificationAdapter;
import com.example.myfyp.Adapter.SuggestionAdapter;
import com.example.myfyp.Adapter.drawerNotificationHelper;
import com.example.myfyp.Model.Notification;
import com.example.myfyp.Model.Post;
import com.example.myfyp.Model.Suggestion;
import com.example.myfyp.Model.User;
import com.example.myfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;
    private List<Post> postsList;

    Post post;
    User user;

    drawerNotificationHelper helper;

    private List<Suggestion> suggestionList;
    private SuggestionAdapter suggestionAdapter;

    private RelativeLayout notificationLayout;

    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getContext(), notificationList );
        recyclerView.setAdapter(notificationAdapter);


        helper=new drawerNotificationHelper(getContext());

    notificationLayout= view.findViewById(R.id.notificationLayout);

        readNotifications();

        return view;
    }

    private void readNotifications(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Notification notification = snapshot.getValue(Notification.class);
                    notificationList.add(notification);

                    String str_content= notification.getText().toString();
                    String str_postid= notification.getPostid();

                    String str_name= notification.getUserid();
//                    Bitmap bitmap= BitmapFactory.decodeFile(post.getPostimage());

                    String title= "PINJABI RANG";
                    String content="my First Notification";




/*

                    android.app.Notification.Builder builder = helper.getQasimNotification(str_content, str_postid);
                    helper.getManager().notify(new Random().nextInt(),builder.build());
*/

                }

                Collections.reverse(notificationList);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}


