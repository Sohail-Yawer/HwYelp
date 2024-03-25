package com.example.hwyelp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class page2 extends Fragment {
    private String id;
    MapView mMapView;
    private GoogleMap googleMap;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page2() {
        // Required empty public constructor
    }
    public page2(String id) {
        this.id = id;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page2.
     */
    // TODO: Rename and change types and number of parameters
    public static page2 newInstance(String param1, String param2) {
        page2 fragment = new page2();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_page2, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }




       //Maps error code

       // Fragment fragment=new page2(id);

       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment);
        //SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
              //  .findFragmentById(R.id.google_map);
        //mapFragment.getMapAsync(this);
        //MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.google_map);
        /*mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        googleMap.addMarker(markerOptions);
                    }
                });

            }
        });*/
        // Inflate the layout for this fragment
        //fr1 = rootView.findViewById(R.id.frg1);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://hw8backend-sohail.wl.r.appspot.com/getinfo?id="+id;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Display the first 500 characters of the response string.
                //Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                //Log.d("TAG", "hello");
                System.out.println("Data obtained from page1: "+response);
                try {
                    String name_b = response.getString("name");
                    JSONObject a = response.getJSONObject("coordinates");
                    Double lat = a.getDouble("latitude");
                    Double lon = a.getDouble("longitude");
                    System.out.println(a);
                    System.out.println("Latitude: "+lat);
                   System.out.println("Longitude: "+lon);

                    mMapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap mMap) {
                            googleMap = mMap;

                            // For showing a move to my location button
                            //googleMap.setMyLocationEnabled(true);

                            // For dropping a marker at a point on the Map
                            LatLng sydney = new LatLng(lat,lon);
                            googleMap.addMarker(new MarkerOptions().position(sydney).title(name_b));

                            // For zooming automatically to the location of the marker
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    });
                    //fr1.setText(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //fr1.setText(getActivity().getApplication().getString(response.));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
        System.out.println("String id inside onCreate of page2: "+id);


        return rootView;
    }

    /*
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }*/
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}