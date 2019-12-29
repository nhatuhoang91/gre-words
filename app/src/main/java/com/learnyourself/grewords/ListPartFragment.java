package com.learnyourself.grewords;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnyourself.adapter.TabPageAdapter;
import com.learnyourself.appinterface.OpenDrawerInterface;
import com.learnyourself.appinterface.SetupToolbarInterface;
import com.learnyourself.appinterface.StartWordFragmentInterface;
import com.learnyourself.db.DBHelper;
import com.learnyourself.util.Const;
import com.learnyourself.util.PrefHelper;

/**
 * Created by Nha on 1/25/2016.
 */
public class ListPartFragment extends Fragment implements StartWordFragmentInterface{
    static final String TAG="ListPartFragment";

    Context mContext;
    CoordinatorLayout coordinatorLayout;
    TabLayout tabLayout;
    ViewGroup container;
    ViewPager viewPager;
    DBHelper dbHelper;
    SetupToolbarInterface setupToolbarInterface;

    boolean is_tablet;
    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        this.mContext=context;
        setupToolbarInterface=(SetupToolbarInterface)context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "onCreate");
        dbHelper = App.getInstance().getDbHelper();
        this.is_tablet = App.getInstance().getPrefsHelper().getPref(PrefHelper.IS_TABLET);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Listojadoijao++onCreateView");
        View v = inflater.inflate(R.layout.list_part_fragment,container,false);
        this.container= container;
        coordinatorLayout =(CoordinatorLayout)v.findViewById(R.id.coordinatorLayout);
        setupTablayout(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbar();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPaused");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void setupToolbar(){
        setupToolbarInterface.setupChildFragmentToolbar(0,null);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        // Retrieve the SearchView and plug it into SearchManager
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }*/

    private void setupTablayout(View v){
        Log.d(TAG,"setup Tab Layout");
        TabPageAdapter tabPageAdapter = new TabPageAdapter(this.getChildFragmentManager());

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(tabPageAdapter);
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    public void notifyDataSetChanged(){
        if(viewPager!=null){
            Log.d("LIST_PART_FRAGMENT","through.......");
            ((TabPageAdapter)viewPager.getAdapter()).notifyPartChanged();
        }
    }

    @Override
    public void startWordFragment(String section, int part) {
        Bundle bundle = new Bundle();
        bundle.putString(Const.SECTION, section);
        bundle.putInt(Const.PART, part);
        WordFragment wordFragment = new WordFragment();
        wordFragment.setArguments(bundle);
        if(is_tablet){
            //tablet
            getFragmentManager().beginTransaction()
                    .replace(R.id.optional_frame_layout, wordFragment, WordFragment.class.getName())
                    .commit();
        }else{
            getFragmentManager().beginTransaction()
                    .replace(container.getId(), wordFragment, WordFragment.class.getName())
                    .addToBackStack(null).commit();
        }
    }
}
