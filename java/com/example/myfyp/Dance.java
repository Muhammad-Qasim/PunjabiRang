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
import com.example.myfyp.Adapter.DanceAdapter;
import com.example.myfyp.Adapter.FoodAdapter;
import com.example.myfyp.Model.DanceM;
import com.example.myfyp.Model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dance extends AppCompatActivity {

    private  final String JSON_URL="https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/Dances/danceapi.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<DanceM> listdance;
    private RecyclerView d_recyclerView;

    RecyclerView.LayoutManager layoutmanager;

    ProgressBar pb;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dance);

        pb= findViewById(R.id.progressDance);

    listdance = new ArrayList<>();

    d_recyclerView = findViewById(R.id.recycler_view_Dance);
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
                       DanceM dance = new DanceM();
                        dance.setName(jsonObject.getString("names"));
                        dance.setImage_url(jsonObject.getString("images"));
                        dance.setDescription(jsonObject.getString("descriptions"));
                        dance.setModels(jsonObject.getString("models"));
                        dance.setAvrgrating("Ratings " +jsonObject.getString("avrgrating"));

                        listdance.add(dance);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(listdance);

                pb.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dance.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue = Volley.newRequestQueue(Dance.this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(List<DanceM> listdance)
    {
        DanceAdapter danceAdapter = new DanceAdapter(this , listdance);
      //  d_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutmanager = new GridLayoutManager(this ,2);
        d_recyclerView.setLayoutManager(layoutmanager);
        d_recyclerView.setAdapter(danceAdapter);

    }


    }

