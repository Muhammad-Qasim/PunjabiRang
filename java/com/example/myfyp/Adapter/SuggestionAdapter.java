package com.example.myfyp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfyp.Fragments.PostDetailsFragment;
import com.example.myfyp.Fragments.ProfileFragment;
import com.example.myfyp.Model.Notification;
import com.example.myfyp.Model.Post;
import com.example.myfyp.Model.Suggestion;
import com.example.myfyp.Model.User;
import com.example.myfyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Suggestion> mSuggestion;

    public SuggestionAdapter(Context context, List<Suggestion> suggestion){
        mContext = context;
        mSuggestion = suggestion;
    }

    @NonNull
    @Override
    public SuggestionAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new SuggestionAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionAdapter.ImageViewHolder holder, int position) {

        final Suggestion suggestion = mSuggestion.get(position);

        holder.text.setText(suggestion.getText());

  //      getUserInfo(holder.image_profile, holder.username, suggestion.getUserid());

        if (suggestion.isIspost()) {
            holder.post_image.setVisibility(View.VISIBLE);
            getPostImage(holder.post_image, suggestion.getPostid());
        } else {
            holder.post_image.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (suggestion.isIspost()) {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("postid", suggestion.getPostid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            new PostDetailsFragment()).commit();
                } else {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", suggestion.getUserid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            new ProfileFragment()).commit();
                }
            }
        });


    }




    private void getUserInfo(ImageView imageView, TextView username, String publisherid) {
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

    @Override
    public int getItemCount() {
        return mSuggestion.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image;
        public TextView username, text;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        image_profile = itemView.findViewById(R.id.image_profile);
        post_image = itemView.findViewById(R.id.post_image);
        username = itemView.findViewById(R.id.username);
        text = itemView.findViewById(R.id.comment);
    }
    }

    private void getPostImage(final ImageView post_image, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Posts").child(postid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Post post = dataSnapshot.getValue(Post.class);
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
}
