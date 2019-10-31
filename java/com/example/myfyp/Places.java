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
import com.example.myfyp.Adapter.PlacesAdapter;
import com.example.myfyp.Model.Food;
import com.example.myfyp.Model.PlacesM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Places extends AppCompatActivity {

    private  final String JSON_URL="https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/Places/placeapi.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<PlacesM> listplaces;
    private RecyclerView p_recyclerView;

    RecyclerView.LayoutManager layoutmanager;

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        pb= findViewById(R.id.progressplace);

        listplaces = new ArrayList<>();

        p_recyclerView = findViewById(R.id.recycler_view_Places);
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
                        PlacesM places= new PlacesM();
                        places.setName(jsonObject.getString("names"));
                        places.setImage_url(jsonObject.getString("images"));
                        places.setDescription(jsonObject.getString("descriptions"));
                        places.setModels(jsonObject.getString("models"));
                        places.setAvrgrating("Ratings " +jsonObject.getString("avrgrating"));


                        listplaces.add(places);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(listplaces);

                pb.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Places.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue = Volley.newRequestQueue(Places.this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(List<PlacesM> listplaces)
    {
        PlacesAdapter p_adapter = new PlacesAdapter (this , listplaces);
     //   p_recyclerView.setLayoutManager(new LinearLayoutManager(this));
       layoutmanager = new GridLayoutManager(this ,2);
        p_recyclerView.setLayoutManager(layoutmanager);

        p_recyclerView.setAdapter(p_adapter);

    }

/*        String[] titles = {"Badshahi Mosque" ,"Minare Pakistan", "Iqbal Square",
                "Hiran Minar", "Lahore Fort"};

        int[] images =
                {

                        R.raw.badshahimosque,
                        R.raw.minar_e_pakistan,
                        R.raw.iqbalsquare,
                        R.raw.hiranminar,
                        R.raw.lahoreforte
                        };


        recyclerView = findViewById(R.id.recycler_view_Places);
        layoutmanager = new GridLayoutManager(this ,2);
        recyclerView.setLayoutManager(layoutmanager);
     //   P_adapter = new FoodAdapter(titles, images);
        recyclerView.setAdapter(P_adapter);*/

    }

