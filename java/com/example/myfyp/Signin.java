package com.example.myfyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    private Button login, signup, guest,forgetPassword;
    private EditText email, password;

    FirebaseAuth auth;
    ProgressDialog pd;
    FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent= new Intent(Signin.this, Home.class );
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email= (EditText)findViewById(R.id.email_et);
        password= (EditText)findViewById(R.id.password_et);

        signup=(Button)findViewById(R.id.signup_btn);
        login=(Button)findViewById(R.id.login_btn);
        guest=(Button)findViewById(R.id.guest_btn);
        forgetPassword= (Button)findViewById(R.id.forget_password_btn);



        auth= FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Signin.this, SignUp.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd=new ProgressDialog(Signin.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_email= email.getText().toString();
                String str_password= password.getText().toString();

                if(TextUtils.isEmpty(str_email)){
                    email.setError("Email Required");
                    pd.dismiss();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
                    email.setError("Please enter a valid email address");
                    pd.dismiss();
                    email.setText("");
                }

                else if(TextUtils.isEmpty(str_password)){
                    password.setError("Password Requird");
                    pd.dismiss();
                }

                else {
                    auth.signInWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(Signin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        if(auth.getCurrentUser().isEmailVerified()){
                                     /*   DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("UsersDuplicate")
                                                .child(auth.getCurrentUser().getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
*/

                                                pd.dismiss();
                                                Intent intent = new Intent(Signin.this, Home.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                            }else {
                                            Toast.makeText(Signin.this, "Please Verify your email", Toast.LENGTH_SHORT).show();
                                        }
/*
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }

                                        });
  */                                      }
                                    else {
                                        pd.dismiss();
                                        Toast.makeText(Signin.this, "Email OR Password Incorrect", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Signin.this, Home.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email= email.getText().toString();
                Intent intent=new Intent(Signin.this, ForgetPasswordActivity.class);
                intent.putExtra("Email", Email);
                startActivity(intent);
            }
        });
    }

}
