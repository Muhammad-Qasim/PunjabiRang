package com.example.myfyp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfyp.Adapter.NotificationAdapter;
import com.example.myfyp.Adapter.PostAdapter;
import com.example.myfyp.Adapter.SuggestionAdapter;
import com.example.myfyp.Model.Notification;
import com.example.myfyp.Model.Post;
import com.example.myfyp.Model.Suggestion;
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

public class SuggestionFragment extends Fragment {


    private RecyclerView recyclerView;

    private SuggestionAdapter suggestionAdapter;
    private List<Suggestion> suggestionList;

    private List<Post> mPosts;
   // Post post= new Post();
  Post post;
   Suggestion suggestion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_suggestion, container, false);


        recyclerView = view.findViewById(R.id.suggestion_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        suggestionList = new ArrayList<>();
        suggestionAdapter = new SuggestionAdapter(getContext(), suggestionList);
        recyclerView.setAdapter(suggestionAdapter);

//        Post post= new Post();

        readSuggestion();


        return view;
    }

    private void readSuggestion() {
      //  final Suggestion suggestion = new Suggestion();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Suggest");

      //  .child(firebaseUser.getUid())

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

               //       post= dataSnapshot.getValue(Post.class);
                       suggestion = snapshot.getValue(Suggestion.class);
                        suggestionList.add(suggestion);
                }
                    Collections.reverse(suggestionList);
                   suggestionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    }




