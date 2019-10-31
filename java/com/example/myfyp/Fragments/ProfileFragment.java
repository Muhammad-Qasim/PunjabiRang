package com.example.myfyp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myfyp.Adapter.MyFotosAdapter;
import com.example.myfyp.EditProfileActivity;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView image_profile;
    ImageView options;
    TextView posts, email,phone_no, username;
    Button edit_profile;
    TextView poststext, postsnr;


    private RecyclerView recyclerView;
    private MyFotosAdapter myFotosAdapter;
    private List<Post> postList;


    private List<String> mySaves;

    private RecyclerView recyclerView_saves;
    private MyFotosAdapter myFotosAdapter_saves;
    private List<Post> postList_saves;

    FirebaseUser firebaseUser;
    String profileid ;


    ImageButton my_fotos, saved_fotos;
 //   private List<Post> mPosts;


     Post post;


    int nr_post;
    String str_username;
    long likes_count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        image_profile = view.findViewById(R.id.image_profile_fp);
        posts = view.findViewById(R.id.nrposts);
        edit_profile = view.findViewById(R.id.edit_profile);
        username = view.findViewById(R.id.username_fp);
  //      my_fotos = view.findViewById(R.id.my_fotos);
        saved_fotos = view.findViewById(R.id.saved_fotos);
        options = view.findViewById(R.id.options);

        poststext= view.findViewById(R.id.posts_txt);



        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        postList = new ArrayList<>();
        myFotosAdapter = new MyFotosAdapter(getContext(), postList);

        recyclerView.setAdapter(myFotosAdapter);


        recyclerView_saves = view.findViewById(R.id.recycler_view_save);
        recyclerView_saves.setHasFixedSize(true);
        LinearLayoutManager mLayoutManagers = new GridLayoutManager(getContext(), 3);
        recyclerView_saves.setLayoutManager(mLayoutManagers);
        postList_saves = new ArrayList<>();
        myFotosAdapter_saves = new MyFotosAdapter(getContext(), postList_saves);
        recyclerView_saves.setAdapter(myFotosAdapter_saves);

//        recyclerView.setVisibility(View.VISIBLE);
        recyclerView_saves.setVisibility(View.VISIBLE);

        userInfo();
        getNrPosts();

  //      userLiked(post.getPublisher());
      //  nrLikes();
       // getNrlikes();
    //    myFotos();
        mySaves();
        readSaves();
/*
        if (profileid.equals(firebaseUser.getUid())) {
            edit_profile.setText("Edit Profile");
        } else {
      //      saved_fotos.setVisibility(View.GONE);
        }*/

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = edit_profile.getText().toString();

      //          if (btn.equals("Edit Profile")) {

                    startActivity(new Intent(getContext(), EditProfileActivity.class));

     //           }
            }
        });
     /*   options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //              startActivity(new Intent(getContext(), OptionsActivity.class));
            }
        });
*/

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.frame_container, new My_UploadsFragment()).commit();
            }
        });
        poststext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new My_UploadsFragment()).commit();
            }
        });
/*
        my_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_saves.setVisibility(View.GONE);
            }
        });

*/


        saved_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerView.setVisibility(View.GONE);
                recyclerView_saves.setVisibility(View.VISIBLE);
            }
        });


return view;

    }


    private void userInfo(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                str_username= user.getUsername().toString();

/*                if (nr_post > 4) {
                    username.setText("top user"+user.getUsername());
                } else {
              //      username.setText(user.getUsername());
                }*/
                Glide.with(getContext()).load(user.getImageurl()).into(image_profile);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         }


/*
 private void userLiked(String publisherid){

     DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userliked").child(firebaseUser.getUid()).child(publisherid);
     reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             int i = 0;
             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            //     Post post= new Post();
             Post post = snapshot.getValue(Post.class);

            //     User user = dataSnapshot.getValue(User.class);
                 likes_count = dataSnapshot.getChildrenCount();

                 User user= dataSnapshot.getValue(User.class);
*/
/*

                        if (firebaseUser.getUid()== post.getPublisher() ) {
                 i++;

                      }
*//*


                 posts.setText(""+likes_count);

                 */
/*posts.setText(""+i);
*//*

             }
         }
         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });

 }
*/




    private void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
      ////////////////////////////////////////////////////////////////////////////////////profileid= firebaseUser.getUid

                    if (post.getPublisher().equals(firebaseUser.getUid())){
                        i++;



                    }
                }

                      posts.setText(""+i);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





    private void myFotos(){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                postList.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    Post post = snapshot.getValue(Post.class);

                    ///////////////////////////////////////////////////////////////profileid= firebaseUser.getUid()
                    if (post.getPublisher().equals(firebaseUser.getUid())){
                        postList.add(post);
                    }
                }
                Collections.reverse(postList);
                myFotosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void mySaves(){
        mySaves = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mySaves.add(snapshot.getKey());
                }
                readSaves();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void readSaves(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList_saves.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                    for (String id : mySaves) {
                        if (post.getPostid().equals(id)) {
                            postList_saves.add(post);
                        }
                    }
                }
                myFotosAdapter_saves.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

//}

