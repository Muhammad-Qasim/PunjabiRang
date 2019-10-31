package com.example.myfyp.Adapter;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.myfyp.CommentsActivity;
import com.example.myfyp.MapActivity;
import com.example.myfyp.Model.Post;
import com.example.myfyp.Model.ReportM;
import com.example.myfyp.Model.Suggestion;
import com.example.myfyp.Model.User;
import com.example.myfyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Post> mPosts;
    ShineButton like;
    ProgressDialog pd;

    long likes_count;

    long total_likes;


    String str_username;
    String str_suggest;

    long id= 0;

    static int j=1;

    //   private static final int PERMISSION_REQUEST_CODE= 1000;


    ReportM reportM= new ReportM();

    private FirebaseUser firebaseUser;
    private FirebaseStorage  firebaseStorage= FirebaseStorage.getInstance();
    StorageReference storageReference= firebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/fyp2k19-7fb79.appspot.com/o/posts%2F1552421175717.null?alt=media&token=c5d4da62-80c1-4a4f-b348-8afc8bb03016");

    public PostAdapter(Context context, List<Post> posts){
        this.mContext = context;
        this.mPosts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.ImageViewHolder holder, final int position) {


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post post = mPosts.get(position);


        Glide.with(mContext).load(post.getPostimage())
       .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(holder.post_image);

        if (post.getDescription().equals("")) {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getDescription());
        }

        holder.category.setText("Category: "+ post.getCategory());

        if(post.getCategory().equals("PLACES")){
            holder.location.setVisibility(View.VISIBLE);
        }
/*
        else {
            holder.location.setVisibility(View.GONE);
        }
*/






                    //Calling the functions

        publisherInfo(holder.image_profile, holder.username, holder.publisher, post.getPublisher());
        isLiked(post.getPostid(), holder.like);



 //       unSceenNotifications(post.getPublisher(),post.getPostid());

 //       nrUnsceenNotifications(firebaseUser.getUid(), post.getPostid());

        nrLikes(holder.likes,holder.post_rank, post.getPostid());
        getCommetns(post.getPostid(), holder.comments);
        isSaved(post.getPostid(), holder.save);

        isReport(post.getPostid(), holder.reported);
        nrReports(holder.reports, post.getPostid());

        userLiked(post.getPublisher(), holder.total_count_l);

     //   addSuggestion(firebaseUser.getUid(), post.getPostid());

   //     isSuggested(post.getPostid(),holder.suggestion);

    /*    holder.suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.suggestion.getTag().equals("Suggest")) {
                    FirebaseDatabase.getInstance().getReference().child("Suggest").child(post.getPostid())
                            .setValue(true);

 //                   addSuggestion( post.getPostid());

                    //    .child(firebaseUser.getUid())
            //       addSuggestion(post.getPublisher(), post.getPostid());


                } else {
*//*

                    FirebaseDatabase.getInstance().getReference().child("Suggest").child(firebaseUser.getUid())
                            .child(post.getPostid()).removeValue();
*//*

                    FirebaseDatabase.getInstance().getReference().child("Suggest").child(post.getPostid())
                            .removeValue();

                    //      .child(firebaseUser.getUid())
                    //      deleteReports(reportM.getReportID());
                }
            }


        });
*/


       holder.reported.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (holder.reported.getTag().equals("Report")) {
            FirebaseDatabase.getInstance().getReference().child("Reports").child(post.getPostid())
                    .child(firebaseUser.getUid()).setValue(true);


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Reports").child(firebaseUser.getUid());

            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("reportid", reportM.getReportID());
            hashMap.put("userid", firebaseUser.getUid());
            hashMap.put("text", "Report the Post");
            hashMap.put("postid", post.getPostid());
            Toast.makeText(mContext, "Report Successfully", Toast.LENGTH_SHORT).show();
            reference.push().setValue(hashMap);


        } else {
            FirebaseDatabase.getInstance().getReference().child("Reports").child(post.getPostid())
                    .child(firebaseUser.getUid()).removeValue();
            Toast.makeText(mContext, "Un-Reported Successfully ", Toast.LENGTH_SHORT).show();

  //      deleteReports(reportM.getReportID());
        }
    }


});

    holder.location.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           String str_city= post.getCity().toString();
           String str_location= post.getTitleLocation().toString();

           String str_address= str_location+ "," +str_city;

    //        Toast.makeText(mContext, "address"+str_address, Toast.LENGTH_SHORT).show();

            Intent intent= new Intent(mContext, MapActivity.class);
            intent.putExtra("address", str_address);
            mContext.startActivity(intent);
        }
    });


                    //saving Likes info on firebase

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user =new User();

//                String postidj= post.getPostid()+j++;

                if (holder.like.getTag().equals("like")) {
                 FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);


                    FirebaseDatabase.getInstance().getReference().child("userliked").child(post.getPublisher())
                            .child(post.getPostid()+(firebaseUser.getUid())).setValue(true);

                  //  child(firebaseUser.getUid()).
                addNotification(post.getPublisher(), post.getPostid());
          //      unSceenNotifications(post.getPublisher(),post.getPostid());

                } else {

                    int i=1;
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();

                        FirebaseDatabase.getInstance().getReference().child("userliked").child(post.getPublisher())
                            .child(post.getPostid()+(firebaseUser.getUid())).removeValue().toString();

                        //  .child(firebaseUser.getUid())
                }
            }
        });


        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("publisherid", post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("publisherid", post.getPublisher());
                mContext.startActivity(intent);
            }
        });
        holder.post_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu=new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.edit_option:
                                editPost(post.getPostid());
                                return true;
                            /*ase R.id.report_option:

                                report(post.getPublisher(), post.getPostid());


                                Toast.makeText(mContext, "Report clicked", Toast.LENGTH_SHORT).show();
                                return true;
*/
                            case R.id.delete_option:
                                final String id= post.getPostid();
                                final AlertDialog.Builder alert= new AlertDialog.Builder(mContext);
                                alert.setTitle("Alerat!!");
                                alert.setMessage("Are you sure you want to delete!!");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        FirebaseDatabase.getInstance().getReference("Posts")
                                                .child(post.getPostid()).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            deleteNotification(id, firebaseUser.getUid());
                                                        }
                                                    }
                                                });

                                    }

                                });
                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alert.show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });

                popupMenu.inflate(R.menu.options);
                if(!post.getPublisher().equals(firebaseUser.getUid()));{
//                popupMenu.getMenu().findItem(R.id.edit_option).setVisible(false);
                    //               popupMenu.getMenu().findItem(R.id.delete_option).setVisible(false);
                }
                popupMenu.show();
                return true;
            }
        });




        holder.option_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(post.getPublisher()!= firebaseUser.getUid()){
                 getItemId(R.id.delete_option);
                }*/

                PopupMenu popupMenu=new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.edit_option:
                                editPost(post.getPostid());
                                return true;
                         /*   case R.id.report_option:
                                report(post.getPublisher(), post.getPostid());
                                Toast.makeText(mContext, "Successfully Reported", Toast.LENGTH_SHORT).show();
                                return true;
                         */   case R.id.download_option:
                                getDownloadableLink(post.getPostid(),post.getPostimage());



                                return true;

                            case R.id.delete_option:
                                final String id= post.getPostid();
                                final AlertDialog.Builder alert= new AlertDialog.Builder(mContext);
                                alert.setTitle("Alerat!!");
                                alert.setMessage("Are you sure you want to delete!!");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        FirebaseDatabase.getInstance().getReference("Posts")
                                                .child(post.getPostid()).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            deleteNotification(id, firebaseUser.getUid());
                                                        }
                                                    }
                                                });
                                    }

                                });
                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alert.show();
                                    return true;
                            default:
                                return false;
                        }

                    }
                });

                popupMenu.inflate(R.menu.options);
                if(!post.getPublisher().equals(firebaseUser.getUid())){
                popupMenu.getMenu().findItem(R.id.edit_option).setVisible(false);
                popupMenu.getMenu().findItem(R.id.delete_option).setVisible(false);
            }
        /*    if(post.getPublisher().equals(firebaseUser.getUid())){
                popupMenu.getMenu().findItem(R.id.report_option).setVisible(false);
            }
        */    popupMenu.show();
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.save.getTag().equals("save")){
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).setValue(true);
                    Toast.makeText(mContext, "Saved to profile Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).removeValue();
                    Toast.makeText(mContext, "Removed from profile Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void deleteReports(String postid) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Reports").child(postid);
        reference.removeValue();
    }


    public void getDownloadableLink(String postid, String postimage) {


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post  post= dataSnapshot.getValue(Post.class);
                String link=  post.getPostimage().toString();

                new DownloadImage().execute(link);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    //Download Image using Async Task


    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView image;
        Button button;
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(mContext);
            // Set progressdialog title
            mProgressDialog.setTitle("Downloading Image");
            // Set progressdialog message
            mProgressDialog.setMessage("Please Wait...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
         //   image.setImageBitmap(result);
            // Close progressdialog

            saveImageToExternalStorage(result);
            mProgressDialog.dismiss();
            Toast.makeText(mContext, "Successfully Downloaded", Toast.LENGTH_SHORT).show();

        }
    }

    //Save Image to External Storage

    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/PunjabiRung");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(mContext, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

    }


////////////////////


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image, like, comment, save, option_menu, reported, suggestion , location;
        public TextView username, likes, publisher, description, comments, reports, category;
        public TextView post_rank;

        public TextView total_count_l;

        TextView link;

        ImageView notification_item;

        public ImageViewHolder(View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            post_image = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);
            likes = itemView.findViewById(R.id.likes);
  //          publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            comments = itemView.findViewById(R.id.comments);
            option_menu = itemView.findViewById(R.id.options);

            reported= itemView.findViewById(R.id.report);
            reports= itemView.findViewById(R.id.reports);

            category = itemView.findViewById(R.id.Category);
            location= itemView.findViewById(R.id.location);
       //    link= itemView.findViewById(R.id.Link);

            post_rank= itemView.findViewById(R.id.post_rank);
            total_count_l= itemView.findViewById(R.id.total_count);

   //         suggestion= itemView.findViewById(R.id.suggestion);

            notification_item= itemView.findViewById(R.id.notification_item);
        }

    }

    private void report(String userid, String postid) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Flag").child(userid);

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "Report the Post");
        hashMap.put("postid", postid);
        Toast.makeText(mContext, "In hashMAp", Toast.LENGTH_SHORT).show();
        reference.push().setValue(hashMap);


    }
    private void addNotification(String userid, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);

        String notificationid= reference.push().getKey();


        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("notificationid", notificationid);

        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "liked your post");
        hashMap.put("postid", postid);
        hashMap.put("ispost", true);

  //     hashMap.put("str_Viewed", "false");
        hashMap.put("viewed",false);

        if(likes_count >=2){
        hashMap.put("rank","your image gets the top rank");
        }

        reference.child(postid).setValue(hashMap);


    }



    private void UNSCEENNOTIFICATIONLIKED(String postid, ImageView imageView){

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Unsceen2").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                }else{
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void nrUnSCEENNotification(String postid){

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Unsceen2").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               long count= dataSnapshot.getChildrenCount();
                Toast.makeText(mContext, "unsceenNotification"+count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void unSceenNotifications(String userid, String postid){

     //   final TextView textView

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UnSceenNotifications").child(userid);


        final String pushid= reference.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "liked your post");
        hashMap.put("postid", postid);
        hashMap.put("ispost", true);
/*
        if(likes_count >=2){
            hashMap.put("rank","your image gets the top rank");
        }*/

        reference.child(pushid).setValue(hashMap);


        //reference.push().setValue(hashMap);


        //  private void isLiked(final String postid, final ImageView imageView){

   /*         final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("UnsceenNotifications").child(postid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(firebaseUser.getUid()).exists()){


                        textView.setTag("View");
                    } else{


                        textView.setTag("Viewed");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
   */     }



   private void nrUnsceenNotifications(final String userid, final String postId){
       FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

       FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
       DatabaseReference referencedel = FirebaseDatabase.getInstance().getReference().child("UnSceenNotifications").child(firebaseUser.getUid())/*.child(postId)*/;
       referencedel.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               long no_of_reports= dataSnapshot.getChildrenCount();
           //    reports.setText(dataSnapshot.getChildrenCount()+" reposts");

               Toast.makeText(mContext, "Notifications"+ dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
             /*  if(no_of_reports >= 2){

                   DatabaseReference delpost = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
                   delpost.removeValue();


               }
           */}

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


   }

    private void addSuggestion(String postid){

     //   String userid,

        Suggestion suggestion= new Suggestion();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Suggest").child(postid);

    //    .child(userid)

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("postid", postid);
                            hashMap.put("text", "Post You may Like");
                            hashMap.put("ispost", true);

                            reference.setValue(hashMap);
                        }

          public  void  deleteSuggestion(String postid){
              FirebaseDatabase.getInstance().getReference("Suggest").child(postid).removeValue();

          }

                        /*
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
*//*
//    }



*/
    private void deleteNotifications(final String postid, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("postid").getValue().equals(postid)){
                        snapshot.getRef().removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //Information of poster's Image and description

    private void publisherInfo(final ImageView image_profile, final TextView username, final TextView publisher, final String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                str_username= user.getUsername().toString();

                username.setText(user.getUsername());
//                publisher.setText(user.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    ///Like the Post

    private void isLiked(final String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()){


                   imageView.setImageResource(R.drawable.ic_liked);

                    imageView.setTag("liked");
                } else{

                   imageView.setImageResource(R.drawable.ic_like);

                    imageView.setTag("like");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


 private void userLiked(String publisherid, TextView total_count_l){

     DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userliked").child(publisherid);
             //.child(postid);
     reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             int i = 0;
             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                 total_likes = dataSnapshot.getChildrenCount();

//                 total_count.setText("total likes : "+ likes_count);

                  if(total_likes >= 4){

                      total_count_l.setText("  Platinum User");
                      total_count_l.setCompoundDrawablesWithIntrinsicBounds(R.drawable.platinum,0,0,0);
                      total_count_l.setVisibility(View.VISIBLE);
                  }
                  else if(total_likes >= 3){
                      total_count_l.setText("  Silver User");
                      total_count_l.setCompoundDrawablesWithIntrinsicBounds(R.drawable.silver,0,0,0);
                      total_count_l.setVisibility(View.VISIBLE);


                  }
                  else if(total_likes >=2){
                      total_count_l.setText("  Bronze User");
                      total_count_l.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bronze,0,0,0);
                      total_count_l.setVisibility(View.VISIBLE);

                  }
                  if(total_likes <2){
                      total_count_l.setVisibility(View.GONE);

                  }
             }
         }
         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });

 }


    //Number of Likes

    private void nrLikes(final TextView likes,TextView postRank, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user= dataSnapshot.getValue(User.class);
                Post post= dataSnapshot.getValue(Post.class);
                likes_count = dataSnapshot.getChildrenCount();
                String likes_txt= likes_count+" likes";
                likes.setText(likes_txt);


            //    int counter =40;

                if(likes_count >= 4){
                        postRank.setText("  Dimond Post");
                        postRank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dimond,0,0,0);
                        postRank.setVisibility(View.VISIBLE);

                        addSuggestion( postId);

                    // username.setText("Top Post "+ str_username );
                }

                else if(likes_count >= 3){
                    postRank.setText("  Gold Post");
                    postRank.setVisibility(View.VISIBLE);
                    postRank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.gold_dimond,0,0,0);
                    deleteSuggestion(postId);

                    // username.setText("Top Post "+ str_username );
                }
         /*      else if(likes_count >= 2){
                    postRank.setText("Silver Post");
                    postRank.setVisibility(View.VISIBLE);
                    // username.setText("Top Post "+ str_username );
                }
         */
         if(likes_count <= 2 ){
                    postRank.setVisibility(View.GONE);
                }

/*
                if (likes_count > 4) {


                    username.setText("Top Fan " +str_username);

                }
                else{
                    username.setText(str_username);
                }*/
/*
                Bundle bundle= new Bundle();
                 bundle.putString("like", likes_txt);
                SuggestionFragment suggestionFragment= new SuggestionFragment();
                suggestionFragment.setArguments(bundle);
  */          }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void isSuggested(final String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Suggest").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()){


                    imageView.setImageResource(R.drawable.ic_notification_red);
                    imageView.setTag("Suggested");
                } else{

                    imageView.setImageResource(R.drawable.ic_notification);

                    imageView.setTag("Suggest");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void isReport(final String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Reports").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()){


                    imageView.setImageResource(R.drawable.ic_report_red);

                    imageView.setTag("Reported");
                } else{

                    imageView.setImageResource(R.drawable.ic_report_black);

                    imageView.setTag("Report");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void nrReports(final TextView reports, String postId){

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

        DatabaseReference referencedel = FirebaseDatabase.getInstance().getReference().child("Reports").child(postId);
        referencedel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long no_of_reports= dataSnapshot.getChildrenCount();
                reports.setText(dataSnapshot.getChildrenCount()+" Reports");

                User user = dataSnapshot.getValue(User.class);
                Post post= dataSnapshot.getValue(Post.class);
                if(no_of_reports >= 2){

                    DatabaseReference delpost = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
                    delpost.removeValue();
/*

                    String publish= FirebaseDatabase.getInstance().getReference().child("Posts").child(post.getPublisher()).toString();
                    String userdata= FirebaseDatabase.getInstance().getReference().child("User").child(user.getId()).toString();

                    DatabaseReference del= FirebaseDatabase.getInstance().getReference().child("User").child(userdata = publish);

                         del.removeValue();
                    Toast.makeText(mContext, "User Has been deleted", Toast.LENGTH_SHORT).show();
*/

/*
                    firebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(mContext, Signin.class);
                    mContext.startActivity(intent);
*/


                }
               /* if(no_of_reports >= 3){

                    deleteNotification(postId,firebaseUser.getUid());*//*
                    dataSnapshot.getRef().removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                }
                            });*//*
                }
*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


/*
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("postid").getValue().equals(postid)){

                                snapshot.getRef().removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
    }
    public static Bitmap viewToBitmap(View view, int width, int height){
        Bitmap bitmap= Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas= new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;


}
private File getDisc(){
        File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file, "Image Demo");
}
        /*FileOutputStream fileOutputStream;
        try{
            fileOutputStream= mContext.openFileOutput(imageName,Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        }catch (Exception e){
            Log.d("saveImage", "Exception 2, Something went wrong");
            e.printStackTrace();
        }*/
/*
        Bitmap bitmap;

        OutputStream outputStream;
        bitmap= BitmapFactory.decodeResource(mContext.getResources(), R.drawable.placeholder);


            File sdcard = Environment.getExternalStorageDirectory();
            File directory = new File(sdcard.getAbsolutePath() + "/punjabiRang");
            directory.mkdir();

//          String filename = String.format("%d.jpg", System.currentTimeMillis());

            File outfile = new File(directory,"myImage.jpg" );


            try{
            outputStream = new FileOutputStream(outfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            Toast.makeText(mContext, "image saved successfully", Toast.LENGTH_SHORT).show();

                outputStream.flush();
            outputStream.close();

        }catch (FileNotFoundException e){
            System.out.print("File Not Found");

        }catch (IOException e){
            System.out.print("IO Exception");
        }

*/
   // }

/*

    private class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        private String TAG ="Download Image";
        private Bitmap downloadImageBitmap(String surl){
            Bitmap bitmap= null;
            try{

                //Download Image From URl
                InputStream inputStream= new URL(surl).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            }catch (Exception e){
                Log.d(TAG, "Exception 1, Something went wrong");
                e.printStackTrace();

            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImageBitmap(strings[0]);
        }
        protected void onPostExecue(Bitmap result){
            saveImage(mContext, result, "myimages.png");
        }
    }
    public Bitmap loadImageBitmap(Context context, String imageName){
        Bitmap bitmap = null;
        FileInputStream fileInputStream;
        try{
            fileInputStream= context.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        }catch (Exception e){
            Log.d("saveImage", "Exception 3, Something went wrong");
            e.printStackTrace();

        }
        return bitmap;
    }

*/

    public void write(String fileName, Bitmap bitmap) {
        FileOutputStream outputStream;
        try {
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
/*
    private String saveImage(Bitmap image) {
        String savedImagePath = null;

        String imageFileName = "JPEG_" + "FILE_NAME" + ".jpg";
        File storageDir = new File(            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/YOUR_FOLDER_NAME");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(mContext, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
*/
/*
    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }
*/
    public void getDownloadUrl(){
       // DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts").child(firebaseUser.getPhotoUrl());
   //     StorageReference reference= ;
    //    islandRef.child("");


    }

    private void getCommetns(String postId, final TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comments.setText("View All "+dataSnapshot.getChildrenCount()+" Comments");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

/*
    private void downloadImage(){

        AlertDialog dialog= new SpotsDialog(downloadImage.this);
        dialog.show();
        dialog.setMessage("Downloading...");
        String filename= UUID.randomUUID().toString()+".jpg";

        Picasso.with(getBaseContext())
                .load("https://firebasestorage.googleapis.com/v0/b/fyp2k19-7fb79.appspot.com/o/posts%2F1552421175717.null?alt=media&token=c5d4da62-80c1-4a4f-b348-8afc8bb03016")
                .into(new SaveImageHelper(getBaseContext(),
                        dialog,
                        getApplicationContext().getContentResolver(),
                        filename,"ImageDescription"
                ));

    }
*/

    private void editPost(final String postid){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Edit Post");

        final EditText editText = new EditText(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        alertDialog.setView(editText);

        getText(postid, editText);

        alertDialog.setPositiveButton("Edit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("description", editText.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Posts")
                                .child(postid).updateChildren(hashMap);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialog.show();
    }

    private void getText(String postid, final EditText editText){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts")
                .child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editText.setText(dataSnapshot.getValue(Post.class).getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteNotification(final String postid, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("postid").getValue().equals(postid)){
                        AlertDialog.Builder alert=new AlertDialog.Builder(mContext);
                        alert.setTitle("Alert");
                        alert.setMessage("Are you sure you want to delete this post!!!");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                snapshot.getRef().removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void isSaved(final String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Saves").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(postid).exists()){
                    imageView.setImageResource(R.drawable.ic_save_black);
                    imageView.setTag("saved");
                } else{
                    imageView.setImageResource(R.drawable.ic_savee_black);
                    imageView.setTag("save");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}



