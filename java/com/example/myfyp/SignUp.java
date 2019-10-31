package com.example.myfyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText username, email, password, confirmpassword, phonenumber;
    private Button signup, back;

    FirebaseAuth auth;
    DatabaseReference reference, reference1;
    ProgressDialog pd;

    private Spinner country;

    String str_country;
    String str_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.username_et);
        email = (EditText) findViewById(R.id.email_et);
        password = (EditText) findViewById(R.id.password_et);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword_et);
        phonenumber = (EditText) findViewById(R.id.phone_no_et);
        signup = (Button) findViewById(R.id.signup_btn_su);
        back = (Button) findViewById(R.id.back_btn);

        country = (Spinner) findViewById(R.id.country_spin);

        auth= FirebaseAuth.getInstance();

         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SignUp.this, R.array.Country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapter);
        country.setOnItemSelectedListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, Signin.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         //Setup Progress Dialogue

            pd= new ProgressDialog(SignUp.this);
            pd.setMessage("Please wait...");
            pd.show();

        // Storing all the textfield values to String

             String str_username= username.getText().toString();
             String str_email = email.getText().toString();
             String str_password= password.getText().toString();
             String str_phoneNumber= phonenumber.getText().toString();
      //       String str_country= country.getText().toString();
             String str_conPass = confirmpassword.getText().toString();

        //Check Username

                if(username.getText().toString().equals("")){
                    username.setError("Username Required");
                    pd.dismiss();
                }

         //Check Email
                else if(email.getText().toString().equals("")){
                    email.setError("Email Required");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
                    email.setError("Please enter a valid email address");
                    pd.dismiss();
                    email.setText("");
                }

         //Check Password

                if(password.getText().toString().equals("")){
                    password.setError("Password Required");
                    pd.dismiss();
                    confirmpassword.setText("");
                }else if(password.length()<6){
                    password.setError("Password must have atleast characters");
                    pd.dismiss();
                    password.setText("");
                    confirmpassword.setText("");
                }

         //Check ConfirmPassword

                if(confirmpassword.getText().toString().equals("")){
                    confirmpassword.setError("ConfirmPassword Required");
                    pd.dismiss();

                }

         //Match Password And Confirm Password

                else if(!str_password.equals(str_conPass)){
                   password.setError("Password and ConfirmPassword do not matched");
                   confirmpassword.setError("Password and ConfirmPassword do not matched");
                    pd.dismiss();
                    password.setText("");
                    confirmpassword.setText("");

                }
/*
                //Check Country

                if(country. parent.getItemAtPosition(position).toString()){
                    country.setError("Country Required  ");
                }*/

         //Check PhoneNo

                if(phonenumber.getText().toString().equals("")){
                    phonenumber.setError("PhoneNumber Required");
                }

/*

                if (username.getText().toString().equals("") || email.getText().toString().equals("") ||
                        password.getText().toString().equals("") || country.getText().toString().equals("")
                        || phonenumber.getText().toString().equals("") || confirmpassword.getText().toString().equals("")) {

                    Toast.makeText(SignUp.this, "Please fill all the Fields", Toast.LENGTH_SHORT).show();
                }
*/
/*

         //Check pasword must have 6 characters

                else if(password.length()< 6) {
                    Toast.makeText(SignUp.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    password.setText("");
                    confirmpassword.setText("");
*/

         //Check password and confirm password do match

//                }
                else{
                    register(str_username, str_email,str_password, str_phoneNumber, str_country);


                    Date date= new Date();
                    Date newDate= new Date(date.getTime()+ (604800000L * 2) + (24 * 60 * 60));
                    SimpleDateFormat df= new SimpleDateFormat("dd-MM-yyyy");
                    str_date= df.format(newDate);

                }


                }
        });
    }

    //Register Method

    private void register(final String userName, final String email, final String password, final String phoneNumber, final String country ){

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userID = firebaseUser.getUid();
                    auth.getCurrentUser().sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(SignUp.this, "Registered successfully. Please check your email for verification", Toast.LENGTH_LONG).show();
                                        reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(userID);

                                        HashMap<String, Object> hashMap = new HashMap();
                                        hashMap.put("id", userID);
                                        hashMap.put("username", userName);
                                        hashMap.put("Email", email.toLowerCase());
                                        hashMap.put("Password", password);
                                        hashMap.put("Phoneno", phoneNumber);
                                        hashMap.put("Counter", str_country);
                                        hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/fyp2k19-7fb79.appspot.com/o/placeholder.png?alt=media&token=da74a891-40af-48e4-979a-726cc900e43b");
                                        hashMap.put("date", str_date);


                                        ////Duplication of values

                                        reference1 = FirebaseDatabase.getInstance().getReference().child("UsersDuplicate")
                                                .child(userID);

                                        //HashMap<String, Object> hashMap1=new HashMap();
                                        hashMap.put("id", userID);
                                        hashMap.put("username", userName);
                                        hashMap.put("Email", email.toLowerCase());
                                        hashMap.put("Password", password);
                                        hashMap.put("Phoneno", phoneNumber);
                                        hashMap.put("Counter", country);
                                        hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/fyp2k19-7fb79.appspot.com/o/placeholder.png?alt=media&token=da74a891-40af-48e4-979a-726cc900e43b");


                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    pd.dismiss();
                                                    Intent intent = new Intent(SignUp.this, Home.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    //                startActivity(intent);
                                                }
                                            }
                                        });

                                        reference1.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    pd.dismiss();
                                                    Intent intent = new Intent(SignUp.this, Home.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                            }
                                        });

                                    }
                                }
                            });

            }else {
                    pd.dismiss();
                    Toast.makeText(SignUp.this, "You Cannot get Registered with this email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Check Country

        str_country = parent.getItemAtPosition(position).toString();

        }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}





