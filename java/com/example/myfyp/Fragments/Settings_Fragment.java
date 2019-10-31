package com.example.myfyp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myfyp.R;


public class Settings_Fragment extends Fragment {

    Spinner color;
    Spinner language;

    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view= inflater.inflate(R.layout.fragment_settings_, container, false);

        color= view.findViewById(R.id.color);
        language=view.findViewById(R.id.language);

        String colors[]={"Black", "Yellow", "Red","White"};
        String languages[]={"English", "Urdu"};

        ArrayAdapter colorAdapter= new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, colors);
        ArrayAdapter languageAdapter= new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, languages);


        color.setAdapter(colorAdapter);
        language.setAdapter(languageAdapter);
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             String selected_item= parent.getItemAtPosition(position).toString();
             if(selected_item== "Black"){
          //       getContext().getApplicationContext().setTheme(R.style.coloring);
             }
                if(selected_item== "Yellow"){
                    Toast.makeText(getContext(),"Yellow is selected", Toast.LENGTH_SHORT).show();
                }
                if(selected_item== "Red"){
                    Toast.makeText(getContext(),"Red is selected", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}
