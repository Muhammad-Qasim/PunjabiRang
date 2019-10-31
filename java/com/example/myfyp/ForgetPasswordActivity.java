package com.example.myfyp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myfyp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton email_ver_rb, phone_ver_rb;
    EditText email_ver_txt, phone_ver_txt, verfication_code;
    Button send, done , resend;

    ///////////////////////////////////////


    TextInputEditText editTextCountryCode, editTextPhone;
    AppCompatButton buttonContinue;


    //////////////////////////////////////

String phoneVerificationID;
    String codeSent;

    FirebaseUser firebaseUser;

    FirebaseAuth auth;

   private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificatonCallbacks;

   private PhoneAuthProvider.ForceResendingToken resendToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        radioGroup = findViewById(R.id.radiogroup);

        email_ver_rb = findViewById(R.id.email_rbtn);
        phone_ver_rb = findViewById(R.id.phone_rbtn);

        email_ver_txt = findViewById(R.id.emailVer_et);
 //       phone_ver_txt = findViewById(R.id.phoneVer_et);
       verfication_code = findViewById(R.id.verificationCode);



        send = findViewById(R.id.send_btn);
        done = findViewById(R.id.done);
        resend= findViewById(R.id.re_send_btn);

        auth = FirebaseAuth.getInstance();



////////////////////////////////////////////////////////////


        editTextCountryCode = findViewById(R.id.editTextCountryCode);
        editTextPhone = findViewById(R.id.editTextPhone);
  //      buttonContinue = findViewById(R.id.buttonContinue);



        /////////////////////////////////////////////////////////



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    email_ver_txt.setText(user.getEmail());
                    phone_ver_txt.setText(user.getPhoneno());

                    email_ver_txt.setEnabled(false);
                    phone_ver_txt.setEnabled(false);               }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    else if(firebaseUser == null){

        String Email= getIntent().getExtras().getString("Email");

        if(firebaseUser == null){
           email_ver_txt.setText(Email);

       }
}
/*if(radioGroup.getCheckedRadioButtonId() == R.id.email_rbtn){
    View send= findViewById(R.id.done);
    send.setVisibility(View.GONE);

}*/
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioid = radioGroup.getCheckedRadioButtonId();

                if (radioid == R.id.email_rbtn) {

                    String str_email = email_ver_txt.getText().toString();

                    if (TextUtils.isEmpty(str_email)) {
                        Toast.makeText(ForgetPasswordActivity.this, "Please write a valid email", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.sendPasswordResetEmail(str_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgetPasswordActivity.this, Signin.class));
                                } else {
                                    String message = task.getException().getMessage();
                                    Toast.makeText(ForgetPasswordActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }
                } else if (radioid == R.id.phone_rbtn) {



                }
            }
        });


        done.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){

            verifyCode(v);

            //       verifySignInCode();
    }
    });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCountryCode.getText().toString().trim();
                String number = editTextPhone.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editTextPhone.setError("Valid number is required");
                    editTextPhone.requestFocus();
                    return;
                }

                String phoneNumber = code + number;

                Intent intent = new Intent(ForgetPasswordActivity.this, verificationPhoneActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);

            }
        });
}

  public void onRadioButtonClicked(View view){

        boolean checked= ((RadioButton) view).isChecked();
        switch (view .getId()){
            case R.id.phone_rbtn:
                if(checked){

                    resend.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);



                    break;
                }
            case R.id.email_rbtn:

                resend.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);

                break;
        }



  }

  public boolean checkifPhoneNumberExists(String phoneNo, DataSnapshot dataSnapshot){

        User user= new User();
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            Toast.makeText(this, "Phone Number Exists", Toast.LENGTH_SHORT).show();
        }
        return true;
  }
public void sendcode(View view){
        String phoneNumber= phone_ver_txt.getText().toString();

    setUpVerificationCallbacks();

    PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            verificatonCallbacks);
}
 private void setUpVerificationCallbacks() {
     verificatonCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
         @Override
         public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

             signInWithPhoneAuthCredential(phoneAuthCredential);
         }

         @Override
         public void onVerificationFailed(FirebaseException e) {

             if (e instanceof FirebaseAuthInvalidCredentialsException) {
                 // Invalid request
               /* Log.d(TAG, "Invalid credential: "
                         + e.getLocalizedMessage());
            */ } else if (e instanceof FirebaseTooManyRequestsException) {
                 // SMS quota exceeded
           //      Log.d(TAG, "SMS Quota exceeded.");
             }
         }

         @Override
         public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            // super.onCodeSent(s, forceResendingToken);
            phoneVerificationID = s;
            resendToken= forceResendingToken;

      //       codeSent = s;

         }

     };
 }
 public void  verifyCode(View view){
        String code= verfication_code.getText().toString();

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(phoneVerificationID, code);
        signInWithPhoneAuthCredential(credential);
 }

     private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
         auth.signInWithCredential(credential)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             FirebaseUser user = task.getResult().getUser();

                         } else {
                             if (task.getException() instanceof
                                     FirebaseAuthInvalidCredentialsException) {
                                 // The verification code entered was invalid
                             }
                         }
                     }
                 });
     }

     public void resendCode(View view){
        String phoneNumber= phone_ver_txt.getText().toString();
        setUpVerificationCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificatonCallbacks,
                resendToken
        );
     }

   /* public void verifySignInCode(){

        String code=verfication_code.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);

        signInWithPhoneAuthCredential(credential);
    }
*//*
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(ForgetPasswordActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                      //      getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ResetPasswordFragment()).commit();
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ForgetPasswordActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }*/

//////////////////////////////////////////////////////////////

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }


    //////////////////////////////


}
