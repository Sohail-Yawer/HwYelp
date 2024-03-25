package com.example.hwyelp;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Business> businesses;
    Adapter adapter;
    Button submitButton, clearButton;
    private static final String[] sugges = new String[]{"donuts","Donuts near me","Donuts shops","pizza","pizza near me","pizzeria"};
    private static final List<String> sugg_list = Arrays.asList("donuts","Donuts near me","Donuts shops","pizza","pizza near me","pizzeria");
    EditText distancevalue,locationvalue;
    //EditText keywordvalue;
    AutoCompleteTextView keywordvalue;
    Spinner categoryvalue;
    CheckBox autoDetectLocation;

    boolean allFields;
    double lon;
    double lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = findViewById(R.id.button);
        clearButton = findViewById(R.id.button2);

        keywordvalue = findViewById(R.id.keyword);
        distancevalue = findViewById(R.id.distance);
        locationvalue = findViewById(R.id.editTextTextPersonName);

        categoryvalue = findViewById(R.id.category);

        autoDetectLocation = findViewById(R.id.checkBox);
        recyclerView = findViewById(R.id.businessList);
        businesses = new ArrayList<>();

        //Static Auto-complete
        //ArrayAdapter<String> adptr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,sugg_list);
        //keywordvalue.setAdapter(adptr);

        keywordvalue.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {
                                                    List<String> suggess = new ArrayList<String>();
                                                    System.out.println(editable);
                                                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                                    String uurrll = "https://hw8backend-sohail.wl.r.appspot.com/getauto?keyword=" + editable;
                                                    JsonObjectRequest str123 = new JsonObjectRequest(Request.Method.GET, uurrll, null, new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            System.out.println(response);

                                                            try {
                                                                JSONArray resp_categories = response.getJSONArray("categories");
                                                                JSONArray resp_terms = response.getJSONArray("terms");
                                                                for(int xax = 0; xax<resp_categories.length();xax++){
                                                                    JSONObject respcat = resp_categories.getJSONObject(xax);
                                                                    suggess.add(respcat.getString("title"));
                                                                }
                                                                for(int yay = 0; yay<resp_terms.length();yay++){
                                                                    JSONObject respterm = resp_terms.getJSONObject(yay);
                                                                    suggess.add(respterm.getString("text"));
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            ArrayAdapter<String> adptr = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,suggess);
                                                            keywordvalue.setAdapter(adptr);


                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    });
                                                    queue.add(str123);


                                                }
                                            }
        //ending of add Text change listener
        );




        //LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(llm);
        //recyclerView.setAdapter( adapter );
        //Adapter adap = new Adapter(this,businesses);
        //recyclerView.setAdapter(adap);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));



        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new Adapter(getApplicationContext(), businesses);
        recyclerView.setAdapter(adapter);

        autoDetectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationvalue.setVisibility(TextView.INVISIBLE);
            }
        });




        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywordvalue.setText("");
                distancevalue.setText("");
                if(autoDetectLocation.isChecked()){autoDetectLocation.toggle(); locationvalue.setVisibility(TextView.VISIBLE);}
                else{ locationvalue.setText("");}
                int size = businesses.size();
                businesses.clear();
                adapter = new Adapter(getApplicationContext(), businesses);
                recyclerView.setAdapter( adapter );
               // notifyItemRangeRemoved(0, size);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                allFields = CheckAllFields();

                if(allFields){
                    submitAutoDetect();
                    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    adapter = new Adapter(getApplicationContext(), businesses);
                    recyclerView.setAdapter( adapter );



                }


            }
        });


    }

    private boolean CheckAllFields(){
        if(keywordvalue.length() == 0){ keywordvalue.setError("This field is required");return false;}

        if(distancevalue.length() == 0){ distancevalue.setError("This field is required");return false;}

        if(locationvalue.length() == 0 && (!autoDetectLocation.isChecked())){ locationvalue.setError("This field is required");return false;}



        return true;
    }



    public void submitAutoDetect(){


        // MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

        // Now requesting Yelp API
        if(autoDetectLocation.isChecked()){
            businesses.clear();
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        String url = "https://ipinfo.io/json?token=e3f0c40dbfc2fe";
                        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("Data : " + response);

                                try {
                                    //JSONObject jsonObject = new JSONObject(response);
                                    System.out.println(response.getString("loc"));
                                    lon = parseDouble(response.getString("loc").split(",")[0]);
                                    lat = parseDouble(response.getString("loc").split(",")[1]);

                                    System.out.println(lon);
                                    System.out.println(lat);
                                    String urlyelp = "https://hw8backend-sohail.wl.r.appspot.com/search?keyword=" + keywordvalue.getText() + "&category=" + categoryvalue.getSelectedItem() + "&distance=" + distancevalue.getText() + "&location=" + locationvalue.getText() + "&lat=" + lon+ "&long=" + lat;
                                    System.out.println("yelp request in autodetect method: " + urlyelp);
                                    System.out.println();
                                    JsonArrayRequest stringRequest1 = new JsonArrayRequest(Request.Method.GET, urlyelp, null, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray responsearr) {
                                            System.out.println("Data : " + responsearr);
                                            try {
                                                for (int i=0;i< responsearr.length();i++)
                                                {
                                                    JSONObject bObject = responsearr.getJSONObject(i);
                                                    String s = String.valueOf(i+1);
                                                    Business b = new Business();
                                                    Double dist = parseDouble(bObject.getString("distance"));
                                                    dist = dist/1000;
                                                    dist = dist/1.6;
                                                    int dis1 =(int)Math.round(dist);
                                                    String d = String.valueOf(dis1);
                                                    b.setBid(s);
                                                    b.setBname(bObject.getString("name").toString());
                                                    b.setBrating(bObject.getString("rating").toString());
                                                    b.setBdistance(d);
                                                    b.setCoverImageURL(bObject.getString("image_url").toString());
                                                    b.setBd(bObject.getString("id").toString());
                                                    b.setBurl(bObject.getString("url").toString());

                                                    businesses.add(b);


                                                }

                                                //JSONObject jsonObject = new JSONObject(response);
                                                //System.out.println(response.getString("loc"));
                                                //lon = Double.parseDouble(response.getString("loc").split(",")[0]);
                                                //lat = Double.parseDouble(response.getString("loc").split(",")[1]);
                                                //System.out.println(lon);
                                                //System.out.println(lat);
                                            } catch (Exception e) {
                                                e.printStackTrace();


                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                        }
                                    });

                                    queue.add(stringRequest1);
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });

                        queue.add(stringRequest);

                        //RequestQueue queue = Volley.newRequestQueue(MainActivity.this);


                    }
                    else{
                        businesses.clear();
                        Log.i("Keyword generated", String.valueOf(keywordvalue));
                        System.out.println(keywordvalue.getText());
                        System.out.println(distancevalue.getText());
                        System.out.println(categoryvalue.getSelectedItem());
                        System.out.println(autoDetectLocation.isChecked());
                        //System.out.println("Outside IF: " + lon);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        String urlyelp = "https://hw8backend-sohail.wl.r.appspot.com/search?keyword="+keywordvalue.getText()+"&category="+categoryvalue.getSelectedItem()+ "&distance=" +distancevalue.getText()+"&location="+ locationvalue.getText()+ "&lat=0.0"+"&long=0.0";
                        System.out.println("For yelp request: "+ urlyelp);
                        JsonArrayRequest stringRequest2 = new JsonArrayRequest(Request.Method.GET, urlyelp, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray responsearr) {
                                System.out.println("Data : " + responsearr);
                                try {
                                    for (int i=0;i< responsearr.length();i++)
                                    {
                                        JSONObject bObject = responsearr.getJSONObject(i);
                                        String s = String.valueOf(i+1);
                                        Business b1 = new Business();
                                        Double dist = parseDouble(bObject.getString("distance"));
                                        dist = dist/1000;
                                        dist = dist/1.6;
                                        int dis1 =(int)Math.round(dist);
                                        String d = String.valueOf(dis1);
                                        b1.setBid(s);
                                        b1.setBname(bObject.getString("name").toString());
                                        b1.setBrating(bObject.getString("rating").toString());
                                        b1.setBdistance(d);
                                        b1.setCoverImageURL(bObject.getString("image_url").toString());
                                        b1.setBd(bObject.getString("id").toString());
                                        b1.setBurl(bObject.getString("url").toString());

                                        businesses.add(b1);


                                    }
                                    //JSONObject jsonObject = new JSONObject(response);
                                    //System.out.println(response.getString("loc"));
                                    //lon = Double.parseDouble(response.getString("loc").split(",")[0]);
                                    //lat = Double.parseDouble(response.getString("loc").split(",")[1]);
                                    //System.out.println(lon);
                                    //System.out.println(lat);
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });

                        queue.add(stringRequest2);
                    }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.bookings_icon:
            //count=(TextView)findViewById(R.id.textView);
            //count.setText("Add is clicked");
            System.out.println("Clicking reservation");
            Intent launchNewIntent = new Intent(MainActivity.this,booking_details.class);
            startActivity(launchNewIntent);




            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }


    //submit method for Geocoding location data

}