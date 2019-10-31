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
import com.example.myfyp.Adapter.ClothesAdapter;
import com.example.myfyp.Model.Clothe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Clothes extends AppCompatActivity {

    private  final String JSON_URL="https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/Clothes/clothapi.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Clothe> listclothes;
    private RecyclerView c_recyclerView;

    RecyclerView.LayoutManager layoutmanager;

    private final String ratingurl= "https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/Index_Resources/ratingapi.php";
    private  JsonArrayRequest ratingRequest;
    ProgressBar pb;



/*
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutmanager;
    RecyclerView.Adapter C_adapter;
    ImageView tastiBiryani;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);


        pb= findViewById(R.id.progressClothes);

        listclothes = new ArrayList<>();

        c_recyclerView = findViewById(R.id.recycler_view_Clothes);
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
                        Clothe clothes = new Clothe();
                        clothes.setName(jsonObject.getString("names"));
                        clothes.setImage_url(jsonObject.getString("images"));
                        clothes.setDescription(jsonObject.getString("descriptions"));
                        clothes.setModels(jsonObject.getString("models"));
                        clothes.setAvrgrating("Ratings " +jsonObject.getString("avrgrating"));


                        listclothes.add(clothes);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

               setuprecyclerview(listclothes);

                pb.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Clothes.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue = Volley.newRequestQueue(Clothes.this);
        requestQueue.add(request);
    }








    private void setuprecyclerview(List<Clothe> listclothes)
    {
        ClothesAdapter c_adapter = new ClothesAdapter(this , listclothes);
//        c_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        layoutmanager = new GridLayoutManager(this ,2);
        c_recyclerView.setLayoutManager(layoutmanager);

        c_recyclerView.setAdapter(c_adapter);

    }


/*
        String[] titles = {"Kurta" ,"Punjabi Phulkari Kurta", "Ghagra",
                "Suthan"};

        int[] images =
                {

                        R.raw.kurta,
                        R.raw.phulkari,
                        R.raw.ghagra,
                        R.raw.suthan

                };


        recyclerView = findViewById(R.id.recycler_view_Clothes);
        layoutmanager = new GridLayoutManager(this ,2);
        recyclerView.setLayoutManager(layoutmanager);
 //       C_adapter = new FoodAdapter(titles, images);
        recyclerView.setAdapter(C_adapter);
*/

    }

