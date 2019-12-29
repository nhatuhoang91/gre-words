package com.learnyourself.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.learnyourself.grewords.AdvanceFragment;
import com.learnyourself.grewords.BasicIFragment;
import com.learnyourself.grewords.BasicIIFragment;

import java.util.ArrayList;

/**
 * Created by Nha on 11/29/2015.
 */
public class TabPageAdapter extends FragmentPagerAdapter {

   // private final ArrayList<Fragment> listFragment = new ArrayList<>();
   // private final ArrayList<String> listStringTile = new ArrayList<>();
    private BasicIFragment basicIFragment;
    private BasicIIFragment basicIIFragment;
    private AdvanceFragment advanceFragment;
    public TabPageAdapter(FragmentManager fm){
        super(fm);
        Log.d("TABPAGEADAPTER", "create tab page ");
        if(fm==null){
            Log.d("TABPAGEADAPTER","fm == null");
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            Log.d("TABPAGEADAPTER","get BASIC I FRAGMENT");
            return basicIFragment = BasicIFragment.getInstance();
        }else
        if(position == 1){
            Log.d("TABPAGEADAPTER","get BASIC II FRAGMENT");
            return basicIIFragment = BasicIIFragment.getInstance();
        }else
        if(position == 2){
            Log.d("TABPAGEADAPTER","get BASIC III FRAGMENT");
            return advanceFragment = AdvanceFragment.getInstance();
        }
        Log.d("TABPAGEADAPTER","get NULL");
        return null;
    }

    //public void addFrag(Fragment fragment, String title) {
      //  listFragment.add(fragment);
       // listStringTile.add(title);
   // }

    @Override
    public int getCount() {
        Log.d("TABPAGEADAPTER","get count");
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "BASIC I";
        }else
        if(position == 1){
            return "BASIC II";
        }else
        if(position == 2){
            return "ADVANCE";
        }
        return "";
    }

    public void notifyPartChanged(){
        if(basicIFragment!=null)
            basicIFragment.notifyDatasetChange();
        if(basicIIFragment!=null)
            basicIIFragment.notifyDatasetChange();
        if(advanceFragment!=null)
            advanceFragment.notifyDatasetChange();
    }
}
