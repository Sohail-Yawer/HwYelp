package com.example.hwyelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class bDetails extends AppCompatActivity {
    MenuItem mn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         //tx1= findViewById(R.id.frg1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdetails);
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("bname"));
        System.out.println(getIntent().getStringExtra("bid"));
        System.out.println(getIntent().getStringExtra("bname"));
        String id = getIntent().getStringExtra("bid");



        ViewPagerAdapter adapter = new ViewPagerAdapter(this,id);
        viewPager2.setAdapter(adapter);

        //tabLayout.getTabAt(0).setText("Business Details");
        //tabLayout.getTabAt(1).setText("Map Location");
        //tabLayout.getTabAt(2).setText("Reviews");
        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setText("Business Details"  );
                        }
                        else if(position == 1){tab.setText("Map Location"  );}
                        else {tab.setText("Reviews"  );}
                    }
                }).attach();
        //tx1.setText("Details of "+getIntent().getStringExtra("bname"));
        getinfoData();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bdetails, menu);
        return true;
    }
    public void getinfoData(){
        String id = getIntent().getStringExtra("bid");
        System.out.println("ID from getInfoData: "+id);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            //count=(TextView)findViewById(R.id.textView);
            //count.setText("Add is clicked");
            System.out.println("Clicking facebook");
            String burlll = getIntent().getStringExtra("burl");
            String bnameee = getIntent().getStringExtra("bname");
            String fbtaggg = "http://www.facebook.com/sharer/sharer.php?u="+burlll;
            System.out.println(fbtaggg);
            Intent ii = new Intent(Intent.ACTION_VIEW);
            ii.setData(Uri.parse(fbtaggg));
            startActivity(ii);



            return(true);
        case R.id.twit:
            //count=(TextView)findViewById(R.id.textView);
           // count.setText("Nothing is selected");


            System.out.println("Clicking twitter");

            String burll = getIntent().getStringExtra("burl");
            String bnamee = getIntent().getStringExtra("bname");
            String tagg = "https://twitter.com/intent/tweet?text=Check%20" + bnamee+"%20on%20Yelp&url="+burll;
            System.out.println(getIntent().getStringExtra("burl"));
            //mn = (MenuItem)findViewById(R.id.add);
            //mn.setOnMenuItemClickListener(onMenuItemSelected(R.id.add),);
            //mn.setOnMenuItemClickListener(Html.fromHtml(tagg, Html.FROM_HTML_MODE_COMPACT));
            //android.net.Uri.parse(tagg);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(tagg));
            startActivity(i);
            return(true);
        //case R.id.about:
            //Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
           // return(true);
        //case R.id.exit:
           // finish();
           // return(true);

    }
        return(super.onOptionsItemSelected(item));
    }
}