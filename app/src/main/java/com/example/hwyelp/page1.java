package com.example.hwyelp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class page1 extends Fragment {
    private String id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView fr1, price, phno, st, ctgy, blink;
    ImageView im;
    Button reserve_bt;
    String resn;
    TextView datename,timename;
    DatePickerDialog datePickerDialog;
    EditText eml;
    int year;
    int month;
    int dayOfMonth;
    Button sb,cnn;
    final Calendar calendar = Calendar.getInstance();

    public page1() {
        // Required empty public constructor
    }

    public page1(String id) {
        System.out.println(id);
        this.id = id;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page1.
     */
    // TODO: Rename and change types and number of parameters
    public static page1 newInstance(String param1, String param2) {
        page1 fragment = new page1();
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
        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);
        // Inflate the layout for this fragment
        fr1 = rootView.findViewById(R.id.baddress);
        price = rootView.findViewById(R.id.bcost);
        phno = rootView.findViewById(R.id.bphonenumber);
        st = rootView.findViewById(R.id.bstatus);
        ctgy = rootView.findViewById(R.id.bcategory);
        blink = rootView.findViewById(R.id.blink);
        reserve_bt = rootView.findViewById(R.id.button3);
        im = rootView.findViewById(R.id.imageView);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://hw8backend-sohail.wl.r.appspot.com/getinfo?id=" + id;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Display the first 500 characters of the response string.
                //Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                //Log.d("TAG", "hello");
                System.out.println("Data obtained from page1: " + response);
                try {
                    String a = response.getString("name");
                    price.setText(response.getString("price"));
                    phno.setText(response.getString("display_phone"));
                    resn = response.getString("name");
                    boolean sta = response.getBoolean("is_closed");
                    JSONArray cat1 = response.getJSONArray("categories");
                    String c = "";
                    for (int j = 0; j < cat1.length(); j++) {
                        JSONObject obj1 = cat1.getJSONObject(j);
                        c = c + obj1.getString("title") + "| ";
                        System.out.println(c);
                    }
                    JSONObject bd = response.getJSONObject("location");
                    JSONArray bdval = bd.getJSONArray("display_address");
                    String ba = " ";
                    for (int k = 0; k < bdval.length(); k++) {
                        System.out.println(bdval.get(k).toString());
                        ba = ba + bdval.get(k).toString();
                    }
                    fr1.setText(ba);
                    ctgy.setText(c);
                    if (sta) {
                        st.setText("Closed");
                        st.getResources().getColor(R.color.red);
                    } else {
                        st.setText("Open Now");
                        st.setTextColor(getResources().getColor(R.color.teal_700));
                    }
                    String blin = response.getString("url");
                    blink.setClickable(true);
                    blink.setMovementMethod(LinkMovementMethod.getInstance());
                    String txt = "<a href='" + blin + "'> Business Link </a>";
                    System.out.println(txt);
                    blink.setText(Html.fromHtml(txt, Html.FROM_HTML_MODE_COMPACT));
                    String i1 = "";
                    JSONArray img = response.getJSONArray("photos");
                    for (int lx = 0; lx < img.length(); lx++) {
                        i1 = img.get(lx).toString();
                        Picasso.get().load(i1).into(im);

                    }

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
        System.out.println("String id inside onCreate of page1: " + id);
        reserve_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dia = new Dialog(getActivity());
                dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dia.setContentView(R.layout.reserve_dialog);
                //dia.getWindow().setBackgroundDrawable(new ColorDrawable(0xb3adad));
                //dia.setContentView(view);
                TextView resname = (TextView) dia.findViewById(R.id.res_name);
                datename = (TextView) dia.findViewById(R.id.editTextDate);
                timename = (TextView) dia.findViewById(R.id.editTextTime);
                eml = (EditText)dia.findViewById(R.id.editTextTextEmailAddress);
                sb = (Button)dia.findViewById(R.id.submit_dialog);
                cnn = (Button)dia.findViewById(R.id.cancel_dialog);
                datename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                        datename.setText(day + "-" + (month + 1) + "-" + year);
                                    }
                                }, year, month, dayOfMonth);
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });
                timename.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);

                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                timename.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, false);
                        //mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });
                sb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(datename.getText());
                        String tm = timename.getText().toString();
                        String em = eml.getText().toString();
                        String aa = tm.split(":")[0];
                        String bb = tm.split(":")[1];
                        String tt = aa+bb;

                        String dd = datename.getText().toString();
                        int nn = Integer.parseInt(tt);
                        if(!em.isEmpty() && !(Patterns.EMAIL_ADDRESS.matcher(em).matches())){Toast.makeText(getActivity(), "Invalid email address",
                                Toast.LENGTH_LONG).show();}
                        else if(nn>= 1700 || nn<=1000 ){
                            Toast.makeText(getActivity(), "Time should be between 10 AM and 5 PM",
                                Toast.LENGTH_LONG).show();}
                        else{
                            Toast.makeText(getActivity(), "Reservation Booked",
                                    Toast.LENGTH_LONG).show();
                            editor.putBoolean("show",true);
                            editor.putString("res_name",resn);
                            editor.putString("date",dd);
                            editor.putString("time",tm);
                            editor.putString("email",em);
                            editor.commit();
                            dia.dismiss();

                        }
                        System.out.println(tt);
                        //System.out.println(timename.getText());
                    }
                });
                cnn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dia.dismiss();
                    }
                });

                resname.setText(resn);
                dia.show();

            }
        });
        return rootView;
    }


}