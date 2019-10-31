package com.example.myfyp;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DisplayModelActivity extends AppCompatActivity {


    String url= "";

    ImageView Dclose, speach, music;
    int result;
    TextToSpeech tospeach;
    String gtext;
    TextView modeldetail;
    public static final String EXTRA_MESSAGE = null;
    private TextToSpeech tts;
    private String Lang = "ENGLISH";
    Spinner lanspinner;
    private WebView baryani;

    ImageView food_img;
    TextView food_name;

    TextView edit_rating;


    String desc ;
    String model;
    String name;

     RatingBar ratingBar;
     Button submit;
  //   Float float_rating;

     String str_rating;

     private static String URL_REGIST= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_model);

  //      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Dclose = findViewById(R.id.cd_close);
        speach = findViewById(R.id.cd_speach);
        music = findViewById(R.id.cd_music);
        modeldetail = findViewById(R.id.modelinfo);
        lanspinner = findViewById(R.id.spinner);

        baryani = findViewById(R.id.baryani_model);

        food_img = findViewById(R.id.food_img);
        food_name = findViewById(R.id.food_name);


        edit_rating= (TextView)findViewById(R.id.edit_ratings);
        ratingBar= (RatingBar)findViewById(R.id.ratingBar);
        submit = (Button)findViewById(R.id.submit);
        ratingBar.setRating(load_ratings());


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                save_ratings_sp(rating);
            }
        });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        float_rating= ratingBar.getRating();
                        str_rating = Float.valueOf(ratingBar.getRating()).toString();
                        Toast.makeText(DisplayModelActivity.this, "Rating is" + str_rating, Toast.LENGTH_SHORT).show();

                        StringRequest request= new StringRequest(Request.Method.POST, "https://panjabi-rang.000webhostapp.com/Punjabi_Rang_Server/insertrating.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(DisplayModelActivity.this, "Successfull", Toast.LENGTH_SHORT).show();

                                 ratingBar.setVisibility(View.GONE);
                                 submit.setVisibility(View.GONE);
                                 edit_rating.setVisibility(View.VISIBLE);
                                    }
                                }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(DisplayModelActivity.this, "Error Un Successfull"+error, Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String ,String> params = new HashMap<String , String>();
                //                params.put("model_name", name);
                                params.put("modellink", name);
                                params.put("rating", str_rating);
                                return params;


                            }
                        };


                        Volley.newRequestQueue(DisplayModelActivity.this).add(request);
                        //          upload_Rating();
                    }
                });

                edit_rating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_rating.setVisibility(View.GONE);
                        ratingBar.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                    }
                });


                WebSettings webSettings = baryani.getSettings();
        webSettings.setJavaScriptEnabled(true);
        baryani.loadUrl("file:///android_asset/burger.html");
        baryani.setWebViewClient(new WebViewClient());

        //Getting data from server

       name = getIntent().getExtras().getString("name");
        desc = getIntent().getExtras().getString("desc");
        model = getIntent().getExtras().getString("model");
        String image = getIntent().getExtras().getString("img");

        //Setting Data into Elements

        food_name.setText(name);




        //setting image by glide

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
        Glide.with(this).load(image).apply(requestOptions).into(food_img);

        //Setting webview link
        baryani.loadUrl(model);


        new GetNotePadFileFromServer().execute();
        //TExt Convertion

       /* String text = "";
        BufferedReader rd = null;
        try {
            // Open the file for reading.
            rd = new BufferedReader(new FileReader(new File(desc)));

            // Read all contents of the file.
            String inputLine = null;
            while ((inputLine = rd.readLine()) != null)
        */
        //     System.out.println(inputLine);
        /*} catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        } finally {
            // Close the file.
            try {
                rd.close();
            } catch (IOException ex) {
                System.err.println("An IOException was caught!");
                ex.printStackTrace();
            }
*/
/*
        try
        {
            URL url= new URL(desc);
            File fl = new File(String.valueOf(url));
            FileInputStream is = new FileInputStream(fl);
            int size=is.available();
            byte[] buffer =new byte[size];
            is.close();
            text= new String(buffer);
        }
        catch(IOException ex)
        {
            //show toast
            Toast.makeText(this, "Data cannot convert"+ex, Toast.LENGTH_SHORT).show();
        }*/
//text fiel where you have to show the text
        //  modeldetail.setText();


//Adapter for spinner


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lanspinner.setAdapter(adapter);

        //Spinner
        lanspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                Lang = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        //For Speach conversion
        tospeach = new TextToSpeech(DisplayModelActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {

                    result = tospeach.setLanguage(Locale.ENGLISH);
                } else {
                    Toast.makeText(DisplayModelActivity.this, "Your Phone didn't Support this functionality", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Speach Listener
        speach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(DisplayModelActivity.this, "Your Phone didn't Support this functionality", Toast.LENGTH_SHORT).show();

                }


                if (tospeach.isSpeaking()) {
                    tospeach.stop();
                    speach.setImageResource(R.drawable.ic_voice);
                } else {

                    if (Lang.equals("GERMAN")) {
                        tospeach.setLanguage(Locale.GERMAN);

                    }
                    if (Lang.equals("ENGLISH")) {
                        tospeach.setLanguage(Locale.ENGLISH);
                    }
                    if (Lang.equals("CHINESE")) {
                        tospeach.setLanguage(Locale.CHINESE);
                    }
                    if (Lang.equals("JAPANESE")) {
                        tospeach.setLanguage(Locale.JAPANESE);
                    }
                    if (Lang.equals("FRENCH")) {
                        tospeach.setLanguage(Locale.FRENCH);
                    }
                    if (Lang.equals("KOREAN")) {
                        tospeach.setLanguage(Locale.KOREAN);
                    }
                    if (Lang.equals("ITALIAN")) {
                        tospeach.setLanguage(Locale.ITALIAN);
                    }
                    if (((TextView) findViewById(R.id.modelinfo)).getText().toString().equals("")) {
                        speakOut(((TextView) findViewById(R.id.modelinfo)).getText().toString());
                    } else {
                        speakOut(((TextView) findViewById(R.id.modelinfo)).getText().toString());
                    }
                    speach.setImageResource(R.drawable.ic_mic_on);
                            /*
                            gtext= modeldetail.getText().toString();
                            tospeach.speak(gtext,TextToSpeech.QUEUE_FLUSH ,null);

                            */
                }


            }
        });


        Dclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent= new Intent( DisplayModelActivity.this ,Home.class);
                startActivity(intent);
             */
                finish();
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //         final MediaPlayer player = MediaPlayer.create(DisplayModelActivity.this , R.raw.musictrack);
//
                //              if(player.isPlaying())
                //         {
                //               player.pause();
                //            }

                //          else if(player.isPlaying()){

                //            player.start();
                //      }
            }
        });




    }

public void save_ratings_sp(Float float_ratings){
    SharedPreferences sp= getSharedPreferences("folder", MODE_PRIVATE);
    SharedPreferences.Editor editor= sp.edit();
    editor.putFloat("model_ratings",float_ratings);
    editor.commit();
    }

public float load_ratings(){
        SharedPreferences sp= getSharedPreferences("folder", MODE_PRIVATE);
        float float_ratings= sp.getFloat("model_ratings",0f );
        ratingBar.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        edit_rating.setVisibility(View.VISIBLE);
        return float_ratings;

}

//    @Override
    //  protected void onDestroy() {
    //      super.onDestroy();
//        if(tospeach!=null)
    //       {
    //          tospeach.stop();
    //          tospeach.shutdown();
    //      }
    //   }


    private void speakOut(String text) {
        tospeach.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }





    public class GetNotePadFileFromServer extends AsyncTask<Void, Void, Void> {
        String TextHolder = "", TextHolder2 = "";
        String strurl = "datafatched";

        @Override
        protected Void doInBackground(Void... params) {

            try {


                URL url = new URL(desc);

                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(url.openStream()));

                while ((TextHolder2 = bufferReader.readLine()) != null) {

                    TextHolder += TextHolder2;
                }
                bufferReader.close();

            } catch (MalformedURLException malformedURLException) {

                // TODO Auto-generated catch block
                malformedURLException.printStackTrace();
                TextHolder = malformedURLException.toString();

            } catch (IOException iOException) {

                // TODO Auto-generated catch block
                iOException.printStackTrace();

                TextHolder = iOException.toString();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void finalTextHolder) {

            modeldetail.setText(TextHolder);

            super.onPostExecute(finalTextHolder);
        }

    }

    private void upload_Rating() {
        final String rating = str_rating.trim();
        StringRequest request = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(DisplayModelActivity.this, "Data fetched", Toast.LENGTH_SHORT).show();
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(DisplayModelActivity.this, "Data error"+ e, Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms= new HashMap<>();
                parms.put("rating",rating);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}