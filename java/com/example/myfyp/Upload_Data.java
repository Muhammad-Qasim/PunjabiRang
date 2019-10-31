package com.example.myfyp;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfyp.Adapter.PostAdapter;
import com.example.myfyp.Fragments.Help_Fragment;
import com.example.myfyp.Fragments.My_UploadsFragment;
import com.example.myfyp.Fragments.Visitors_GalaryFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Upload_Data extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Uri mImageUri;
    String miUrlOk = "";
    private StorageTask uploadTask;
    StorageReference storageRef;

    ImageView close, image_added;
    TextView post;
    EditText description, title_location;
    String category;
    String  city;
    String likecounter;

    private int REQUEST_CODE = 1;
    private static final int IMAGE_PICK_CODE= 1000;


    private Spinner spinner;
    private Spinner citySpinner;
    private RelativeLayout city_spinner_layout;


   // private static final String[] category = {"Food", "Sports", "Dance", "Places", "Musical Instruments", "Clothes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);

        close = findViewById(R.id.close);
        image_added = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        title_location= findViewById(R.id.title_location);


        spinner = (Spinner) findViewById(R.id.select_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Upload_Data.this, R.array.Upload_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        city_spinner_layout= findViewById(R.id.city_spinner_layout);

        citySpinner = (Spinner) findViewById(R.id.select_city);
        ArrayAdapter<CharSequence> cityadapter = ArrayAdapter.createFromResource(Upload_Data.this, R.array.City, android.R.layout.simple_spinner_item);
        cityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityadapter);
        citySpinner.setOnItemSelectedListener(this);


    storageRef =FirebaseStorage.getInstance().getReference("posts");

        close.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        startActivity(new Intent(Upload_Data.this, Home.class));
        finish();
    }
    });

        post.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        uploadImage_10();
    }
    });

        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_CODE);
*/
        /*

    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult(intent, IMAGE_PICK_CODE);
*/


        CropImage.activity()
                .setAspectRatio(1,1)
                .start(Upload_Data.this);


    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage_10() {

        if (category.equals("---Select Category---")) {

            TextView spinnerError = (TextView) spinner.getSelectedView();
            spinnerError.setError("");
            spinnerError.setTextColor(Color.RED);

            Toast.makeText(this, "Category Required", Toast.LENGTH_SHORT).show();
        }

        else if(category.equals("PLACES") && city.equals("---Select City---")){

            TextView spinnerError2 = (TextView) citySpinner.getSelectedView();
            spinnerError2.setError("");
            spinnerError2.setTextColor(Color.RED);

            Toast.makeText(this, "City Required", Toast.LENGTH_SHORT).show();

        }
        else {

            Date date= new Date();
            Date newDate= new Date(date.getTime()+ (604800000L * 2) + (24 * 60 * 60));
            SimpleDateFormat df= new SimpleDateFormat("dd-MM-yyyy");
            String str_date= df.format(newDate);

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Posting");
            pd.show();
            if (mImageUri != null) {
                final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                uploadTask = fileReference.putFile(mImageUri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            miUrlOk = downloadUri.toString();


                            //          String DownloadLink= fileReference.toString();

                            //              LocalBroadcastManager.getInstance(Upload_Data.this).registerReceiver(receiver, new IntentFilter("postcount"));
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                            String postid = reference.push().getKey();

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("postid", postid);
                            hashMap.put("postimage", miUrlOk);
                            hashMap.put("description", description.getText().toString().toLowerCase());
                            hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            hashMap.put("category", category);
                            hashMap.put("date", str_date);
                            hashMap.put("city", city);
                            hashMap.put("titleLocation", title_location.getText().toString().toLowerCase());



                            //            hashMap.put("postcounts",likecounter);


                            //     hashMap.put("downloadLink", DownloadLink);

                            reference.child(postid).setValue(hashMap);

                            pd.dismiss();

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_upload, new Visitors_GalaryFragment()).commit();

//                            startActivity(new Intent(Upload_Data.this, Home.class));
                            finish();


                        } else {
                            Toast.makeText(Upload_Data.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload_Data.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            } else {
                Toast.makeText(Upload_Data.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();

            image_added.setImageURI(mImageUri);
        }
       else {
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
      //      startActivity(new Intent(Upload_Data.this, Home.class));
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Visitors_GalaryFragment()).commit();

            finish();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(parent.getId()== R.id.select_category) {

                category = parent.getItemAtPosition(position).toString();

                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);

                if (category.equals("---Select Category---")) {
                    city_spinner_layout.setVisibility(View.GONE);
                    title_location.setVisibility(View.GONE);
                }
                if (category.equals("FOOD")) {
                    city_spinner_layout.setVisibility(View.GONE);
                    title_location.setVisibility(View.GONE);
                }
                if (category.equals("CLOTHES")) {
                    city_spinner_layout.setVisibility(View.GONE);
                    title_location.setVisibility(View.GONE);
                }
                if (category.equals("MUSICAL INSTRUMENTS")) {
                    city_spinner_layout.setVisibility(View.GONE);
                    title_location.setVisibility(View.GONE);
                }
                if (category.equals("DANCE")) {
                    city_spinner_layout.setVisibility(View.GONE);
                    title_location.setVisibility(View.GONE);
                }

                if (category.equals("PLACES")) {
                    city_spinner_layout.setVisibility(View.VISIBLE);
                    title_location.setVisibility(View.VISIBLE);
                }
                if (category.equals("SPORTS")) {
                    city_spinner_layout.setVisibility(View.GONE);
                    title_location.setVisibility(View.GONE);
                }

            }
            else if(parent.getId()== R.id.select_city){

                city= parent.getItemAtPosition(position).toString();

                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);

            }
        //          Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();

        }
//    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /*

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            likecounter = intent.getStringExtra("str_count");
          }
    };*/
}
