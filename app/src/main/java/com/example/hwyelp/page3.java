package com.example.hwyelp;

import static java.lang.Double.parseDouble;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class page3 extends Fragment {
    private String id;
    TextView fr1,fr2,fr3;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page3() {
        // Required empty public constructor
    }
    public page3(String id) {
        this.id = id;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page3.
     */
    // TODO: Rename and change types and number of parameters
    public static page3 newInstance(String param1, String param2) {
        page3 fragment = new page3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        System.out.println("String id inside onCreate of page3: "+id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_page3, container, false);
        // Inflate the layout for this fragment
        fr1 = rootView.findViewById(R.id.review1);
        fr2 = rootView.findViewById(R.id.review2);
        fr3 = rootView.findViewById(R.id.review3);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://hw8backend-sohail.wl.r.appspot.com/getreviews?id="+id;
        JsonArrayRequest stringRequest1 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responsearr) {
                System.out.println("Data : " + responsearr);
                try {
                    for (int i=0;i< responsearr.length();i++)
                    {
                        if (i ==0 ){
                            JSONObject bObject = responsearr.getJSONObject(i);
                            System.out.println("Review data for: " +i+" is: "+bObject);
                            String d1 = "\n";
                            JSONObject user = bObject.getJSONObject("user");
                            String name = user.getString("name").toString();
                            int rtng = bObject.getInt("rating");
                            String timecr = bObject.getString("time_created").toString().split(" ")[0];

                            String nm = bObject.getString("text").toString();
                            d1 = d1+name+"\n"+"Rating: "+rtng+"/5"+"\n\n"+nm+"\n\n"+timecr;
                            fr1.setText(d1);
                        }
                        else if(i==1){
                            JSONObject bObject = responsearr.getJSONObject(i);
                            System.out.println("Review data for: " +i+" is: "+bObject);
                            String d1 = "\n";
                            JSONObject user = bObject.getJSONObject("user");
                            String name = user.getString("name").toString();
                            int rtng = bObject.getInt("rating");
                            String timecr = bObject.getString("time_created").toString().split(" ")[0];

                            String nm = bObject.getString("text").toString();
                            d1 = d1+name+"\n"+"Rating: "+rtng+"/5"+"\n\n"+nm+"\n\n"+timecr;
                            fr2.setText(d1);

                        }
                        else{
                            JSONObject bObject = responsearr.getJSONObject(i);
                            System.out.println("Review data for: " +i+" is: "+bObject);
                            String d1 = "\n";
                            JSONObject user = bObject.getJSONObject("user");
                            String name = user.getString("name").toString();
                            int rtng = bObject.getInt("rating");
                            String timecr = bObject.getString("time_created").toString().split(" ")[0];

                            String nm = bObject.getString("text").toString();
                            d1 = d1+name+"\n"+"Rating: "+rtng+"/5"+"\n\n"+nm+"\n\n"+timecr;
                            fr3.setText(d1);
                        }

                       /* String s = String.valueOf(i+1);
                        Business b = new Business();
                        Double dist = parseDouble(bObject.getString("distance"));
                        dist = dist/1000;
                        dist = dist/1.6;
                        String d = String.valueOf(dist);
                        b.setBid(s);
                        b.setBname(bObject.getString("name").toString());
                        b.setBrating(bObject.getString("rating").toString());
                        b.setBdistance(d);
                        b.setCoverImageURL(bObject.getString("image_url").toString());
                        b.setBd(bObject.getString("id").toString());

                       // businesses.add(b);*/


                    }

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
        System.out.println("String id inside onCreate of page1: "+id);
        return rootView;
    }
}