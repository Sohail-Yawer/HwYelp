package com.example.hwyelp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private String id;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String id)
    {
        super(fragmentActivity);
        this.id = id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new page1(this.id);
            case 1:
                return  new page2(this.id);
            case 2:
                return  new page3(this.id);
            default:
                return  new page1(this.id);
        }
    }
    @Override
    public int getItemCount() {return 3; }
}
