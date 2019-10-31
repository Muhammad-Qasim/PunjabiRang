package com.example.myfyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfyp.Adapter.FoodAdapter;
import com.example.myfyp.Adapter.InstrumentsAdapter;
import com.example.myfyp.Model.Food;
import com.example.myfyp.Model.InstrumentsM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Music extends AppCompatActivity {


    private  final String JSON_URL="https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/Instruments/instrapi.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<InstrumentsM> listinstruments;
    private RecyclerView I_recyclerView;

    RecyclerView.LayoutManager layoutmanager;

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        listinstruments = new ArrayList<>();

        pb= findViewById(R.id.progressMusic);

        I_recyclerView = findViewById(R.id.recycler_view_Music);
        jsonrequest();

    }

    private void jsonrequest()
    {

        pb.setVisibility(View.VISIBLE);

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        InstrumentsM instruments = new InstrumentsM();
                        instruments.setName(jsonObject.getString("names"));
                        instruments.setImage_url(jsonObject.getString("images"));
                        instruments.setDescription(jsonObject.getString("descriptions"));
                        instruments.setModels(jsonObject.getString("models"));
                        instruments.setAvrgrating("Ratings " +jsonObject.getString("avrgrating"));


                        listinstruments.add(instruments);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(listinstruments);

                pb.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Music.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue = Volley.newRequestQueue(Music.this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(List<InstrumentsM> listinstruments)
    {
        InstrumentsAdapter adapter = new InstrumentsAdapter(this , listinstruments);
//        I_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutmanager = new GridLayoutManager(this ,2);
        I_recyclerView.setLayoutManager(layoutmanager);

        I_recyclerView.setAdapter(adapter);

    }



        /*

        String[] titles = {"Dhol" ,"Flute", "Dhotara", "Chimta"
                };

        int[] images =
                {

                        R.raw.dholimage,
                        R.raw.fluteimage,
                        R.raw.dhotra,
                        R.raw.chimtaimage,

                };


        recyclerView = findViewById(R.id.recycler_view_Music);
        layoutmanager = new GridLayoutManager(this ,2);
        recyclerView.setLayoutManager(layoutmanager);
   //     M_adapter = new FoodAdapter(titles, images);
        recyclerView.setAdapter(M_adapter);
*/

    }

