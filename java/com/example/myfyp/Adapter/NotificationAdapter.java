package com.example.myfyp.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfyp.Model.Notification;


import com.bumptech.glide.Glide;
import com.example.myfyp.Fragments.PostDetailsFragment;
import com.example.myfyp.Fragments.ProfileFragment;
import com.example.myfyp.Model.Post;
import com.example.myfyp.Model.User;
import com.example.myfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Notification> mNotification;

    Post post;
    Notification notificationclass;

  //  private List<Post> mPosts;




    public NotificationAdapter(Context context, List<Notification> notification){
        mContext = context;
        mNotification = notification;

    }

    @NonNull
    @Override
    public NotificationAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ImageViewHolder holder, final int position) {

        final Notification notification = mNotification.get(position);


        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        holder.text.setText(notification.getText());

        getUserInfo(holder.image_profile, holder.username, notification.getUserid());

    //    nrNotifications();

        if (notification.isIspost()) {
            holder.post_image.setVisibility(View.VISIBLE);
            getPostImage(holder.post_image, notification.getPostid());
        } else {
            holder.post_image.setVisibility(View.GONE);
        }



        if(notification.isViewed()){
            holder.notification_layout.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.notification_layout.setBackgroundColor(Color.GRAY);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

                if (notification.isIspost()) {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("postid", notification.getPostid());
                    editor.apply();


                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            new PostDetailsFragment()).commit();

                    if(notification.getText().equals("liked your post")) {

                        updateViewed(post.getPublisher(), notification.getPostid());
                    }
                    else if(notification.getText().equals("Commented on Post")){
                        updateCommentnote(post.getPublisher(),notification.getNotificationId());
                    }
                } else {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", notification.getUserid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            new ProfileFragment()).commit();

                }


            }
        });


    }


    //
    @Override
    public int getItemCount() {
        return mNotification.size();

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image;
        public TextView username, text;

        public RelativeLayout notification_layout;

        public ImageViewHolder(View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            username = itemView.findViewById(R.id.username);
            text = itemView.findViewById(R.id.comment);

            notification_layout= itemView.findViewById(R.id.notificationLayout);

        }
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String publisherid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(publisherid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(imageView);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPostImage(final ImageView post_image, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Posts").child(postid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
        try {
            post = dataSnapshot.getValue(Post.class);
            Glide.with(mContext).load(post.getPostimage()).into(post_image);
            }catch (Exception e){
            Toast.makeText(mContext, "Error"+ e, Toast.LENGTH_SHORT).show();
    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void updateViewed(String publisherid, String postid){



  //      Toast.makeText(mContext, "jfjkd   "+database, Toast.LENGTH_SHORT).show();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("viewed", true);

        FirebaseDatabase.getInstance().getReference("Notifications").child(publisherid).child(postid)
        .updateChildren(hashMap);

        //        database.updateChildren(hashMap);

    }


    private void updateCommentnote(String publisherid, String postid) {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("viewed", true);

        FirebaseDatabase.getInstance().getReference("Notifications").child(publisherid).child(postid)
                .updateChildren(hashMap);
    }
    private void getViewed(String userid,String notificationid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationclass = dataSnapshot.getValue(Notification.class);

              String viewed= dataSnapshot.getValue(Notification.class).getText();
                Toast.makeText(mContext, "Notification"+viewed, Toast.LENGTH_SHORT).show();
           }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void nrNotifications(){
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        DatabaseReference referencedel = FirebaseDatabase.getInstance().getReference().child("Notifications").child(post.getPublisher());
        referencedel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(notificationclass.isViewed()){
                long no_of_viewed= dataSnapshot.getChildrenCount();
                //    reports.setText(dataSnapshot.getChildrenCount()+" reposts");

                Toast.makeText(mContext, "Viewed"+ dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
 }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}