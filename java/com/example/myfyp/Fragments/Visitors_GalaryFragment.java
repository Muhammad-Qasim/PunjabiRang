package com.example.myfyp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.myfyp.Adapter.PostAdapter;
import com.example.myfyp.Model.Post;
import com.example.myfyp.Model.User;
import com.example.myfyp.R;
import com.example.myfyp.SignUp;
import com.example.myfyp.Upload_Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Visitors_GalaryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    ImageView uploadImage;

    EditText search_bar;

    Context mcontext;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    ProgressDialog pd;
    ProgressBar pr;
 //   private List<Post> postList;

    Spinner spinnersearch;
    String str_searchspinner;
    String str_category;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_visitors__galary, container, false);

        uploadImage= view.findViewById(R.id.upload_Image);
        mcontext = this.getContext();


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        search_bar= view.findViewById(R.id.search_et);

        spinnersearch= view.findViewById(R.id.search_spinner);

        pr=view.findViewById(R.id.progressCircle);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mcontext, R.array.Find_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersearch.setAdapter(adapter);
        spinnersearch.setOnItemSelectedListener(this);

//        postList = new ArrayList<>();


 /*  LinearLayout linearLayout= (LinearLayout)view.findViewById(R.id.linerLayout_VG);
    linearLayout.setBackgroundColor(Color.YELLOW);
   */
        readPosts();



        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Upload_Data.class);
                startActivity(intent);
            }
        });
        return view;

    }

    private void searchUsers(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("description")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    postList.add(post);
                }

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
        private void readPosts(){

         //   pd= new ProgressDialog(getContext());
       // pd.setMessage("Please wait...");
       // pd.show();

            pr.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

       //         if(search_bar.getText().toString().equals("")){

                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Post post = snapshot.getValue(Post.class);

                    str_category= post.getCategory();
                    postList.add(post);
                    pr.setVisibility(View.GONE);
       //                  pd.dismiss();

                }

                postAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        str_searchspinner = parent.getItemAtPosition(position).toString();

        if(str_searchspinner.equals("Filter")){


            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("category");
                           /* .startAt(s)
                            .endAt(s + "\uf8ff");*/

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        if(str_searchspinner.equals("CLOTHES")){


            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("category")
                    .equalTo("CLOTHES");
                           /* .startAt(s)
                            .endAt(s + "\uf8ff");*/

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

       else if(str_searchspinner.equals("FOOD")){


            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("category")
                    .equalTo("FOOD");
                           /* .startAt(s)
                            .endAt(s + "\uf8ff");*/

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(str_searchspinner.equals("MUSICAL INSTRUMENTS")){


            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("category")
                    .equalTo("MUSICAL INSTRUMENTS");
                           /* .startAt(s)
                            .endAt(s + "\uf8ff");*/

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(str_searchspinner.equals("DANCE")){


            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("category")
                    .equalTo("DANCE");
                           /* .startAt(s)
                            .endAt(s + "\uf8ff");*/

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(str_searchspinner.equals("PLACES")){


            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("category")
                    .equalTo("PLACES");
                           /* .startAt(s)
                            .endAt(s + "\uf8ff");*/

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(str_searchspinner.equals("SPORTS")){


            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("category")
                    .equalTo("SPORTS");
                           /* .startAt(s)
                            .endAt(s + "\uf8ff");*/

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
