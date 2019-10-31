package com.example.myfyp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.myfyp.Model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FoodActivity extends AppCompatActivity {

    private  final String JSON_URL="https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/Foods/foodapi.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Food> listfood;
    private RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutmanager;
    RecyclerView.Adapter adapter;

    ProgressDialog pd;
    ProgressBar progressBar;

  /*  RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutmanager;
    RecyclerView.Adapter adapter;
    ImageView tastiBiryani;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        progressBar= findViewById(R.id.progressfood);

        listfood = new ArrayList<>();

/*
        pd.setTitle("Please Wait...");
        pd.setMessage("Loading...");
        pd.show();
*/
        recyclerView = findViewById(R.id.recycler_view_Food);
        jsonrequest();

      //    pd.dismiss();
    }

    private void jsonrequest()

    {
            progressBar.setVisibility(View.VISIBLE);

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {




                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Food food = new Food();
                        food.setName(jsonObject.getString("name"));
                        food.setImage_url(jsonObject.getString("images"));
                        food.setDescription(jsonObject.getString("descriptions"));
                        food.setModels(jsonObject.getString("models"));
                        food.setAvrgrating("Ratings " +jsonObject.getString("avrgrating"));


                        listfood.add(food);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(listfood);
    //    pd.dismiss();

                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue = Volley.newRequestQueue(FoodActivity.this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(List<Food> listfood)

    {
            FoodAdapter adapter = new FoodAdapter(this , listfood);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

       layoutmanager = new GridLayoutManager(this ,2);
        recyclerView.setLayoutManager(layoutmanager);

        recyclerView.setAdapter(adapter);

     //   pd.dismiss();
    }



    }

