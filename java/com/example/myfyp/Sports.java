package com.example.myfyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfyp.Adapter.FoodAdapter;
import com.example.myfyp.Adapter.SportsAdapter;
import com.example.myfyp.Model.Food;
import com.example.myfyp.Model.SportsM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sports extends AppCompatActivity {

    private  final String JSON_URL="https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/Sports/sportapi.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<SportsM> listsports;
    private RecyclerView s_recyclerView;

    RecyclerView.LayoutManager layoutmanager;

    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        listsports = new ArrayList<>();

        pb= findViewById(R.id.progresssports);

        s_recyclerView = findViewById(R.id.recycler_view_Sports);
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
                        SportsM sports = new SportsM();
                        sports.setName(jsonObject.getString("name"));
                        sports.setImage_url(jsonObject.getString("images"));
                        sports.setDescription(jsonObject.getString("descriptions"));
                        sports.setModels(jsonObject.getString("models"));
                        sports.setAvrgrating("Ratings " +jsonObject.getString("avrgrating"));


                        listsports.add(sports);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(listsports);

                pb.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sports.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue = Volley.newRequestQueue(Sports.this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(List<SportsM> listsports)
    {
        SportsAdapter s_adapter = new SportsAdapter(this , listsports);
    //    s_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutmanager = new GridLayoutManager(this ,2);
        s_recyclerView.setLayoutManager(layoutmanager);

        s_recyclerView.setAdapter(s_adapter);

    }

}

