package com.example.myfyp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myfyp.R;


public class ResetPasswordFragment extends Fragment {

    Button login;
    EditText newPassword, confirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);

        newPassword= view.findViewById(R.id.new_password_et);
        confirmPassword= view.findViewById(R.id.confirm_password_et);

        login= view.findViewById(R.id.login_btn_rp);

    login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


    }
});
        return view;
    }
}
