package com.example.hwyelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class booking_details extends AppCompatActivity {
    SharedPreferences pref;
    TextView nobook,sno,resname,bdate,dtime,bemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        setContentView(R.layout.activity_booking_details);
        nobook = findViewById(R.id.nobookings);
        sno = findViewById(R.id.textView_bookingid);
        resname = findViewById(R.id.textView_booking_resname);
        bdate = findViewById(R.id.booking_date);
        dtime = findViewById(R.id.booking_time);
        bemail = findViewById(R.id.booking_email);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(!pref.getBoolean("show", false)){nobook.setText("No Bookings!");}
        else {
            sno.setText("1");
            resname.setText(pref.getString("res_name", null));
            bdate.setText(pref.getString("date",null));
            dtime.setText(pref.getString("time",null));
            bemail.setText(pref.getString("email",null));

            System.out.println(pref.getString("res_name", null));
            System.out.println(pref.getString("date", null));
            System.out.println(pref.getString("time", null));
            System.out.println(pref.getString("email", null));
            editor.clear();
            editor.commit();
        }
        //pref.getString("res_name",null);
    }
}