package com.example.myfyp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfyp.EditProfileActivity;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;


public class Privacy_Fragment extends Fragment {

    MaterialEditText old_password, new_password, confirm_password;
    TextView op, np, cnp;



    FirebaseUser firebaseUser;

    Button save_changes, next;

    ProgressDialog pd;

    FirebaseAuth auth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_privacy_, container, false);

        old_password = view.findViewById(R.id.old_password_fr);
        new_password = view.findViewById(R.id.new_password_fr);
        confirm_password = view.findViewById(R.id.confirm_password_fr);

        op= view.findViewById(R.id.op_tv);
        np= view.findViewById(R.id.np_tv);
        cnp= view.findViewById(R.id.cnp_tv);

        save_changes= view.findViewById(R.id.save_changes);
        next= view.findViewById(R.id.next);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        new_password.setFocusableInTouchMode(false);
        new_password.setFocusable(false);
        confirm_password.setFocusableInTouchMode(false);
        confirm_password.setFocusable(false);



    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                String old_pass= user.getPassword();
                String old_pass_tf = old_password.getText().toString();

                if (old_pass_tf.equals(old_pass)){
                    Toast.makeText(getContext(), "Password Matched", Toast.LENGTH_SHORT).show();

                    op.setVisibility(View.GONE);
                    old_password.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);

                    np.setVisibility(View.VISIBLE);
                    new_password.setFocusableInTouchMode(true);
                    new_password.setFocusable(true);
                    new_password.setVisibility(View.VISIBLE);

                    cnp.setVisibility(View.VISIBLE);
                    confirm_password.setFocusableInTouchMode(true);
                    confirm_password.setFocusable(true);
                    confirm_password.setVisibility(View.VISIBLE);

                    save_changes.setVisibility(View.VISIBLE);
                }
                else if(!old_pass_tf.equals(old_pass)){
                    Toast.makeText(getContext(), "Passwrod is Incorrect", Toast.LENGTH_SHORT).show();
                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
});

save_changes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String str_newPassword= new_password.getText().toString();
                    String str_confirmPassword= confirm_password.getText().toString();

                    if(TextUtils.isEmpty(str_newPassword) || TextUtils.isEmpty(str_confirmPassword)){
                        Toast.makeText(getContext(), "Please Enter Password field", Toast.LENGTH_SHORT).show();

                        new_password.setText("");
                        confirm_password.setText("");
                    }

                    else if(!str_newPassword.equals(str_confirmPassword)){

                        Toast.makeText(getContext(), "password do not match", Toast.LENGTH_SHORT).show();
                        new_password.setText("");
                        confirm_password.setText("");
                    }
                    else{
                        editpass(firebaseUser.toString());
                        new_password.setText("");
                        confirm_password.setText("");
                        Toast.makeText(getContext(), "password change successfully", Toast.LENGTH_SHORT).show();
                        old_password.setText("");
                    }
                }
            });


        return view;
    }
private void editpass(String user){


    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("Password", new_password.getText().toString());

    FirebaseDatabase.getInstance().getReference("Users")
            .child(firebaseUser.getUid()).updateChildren(hashMap);
    }
}
