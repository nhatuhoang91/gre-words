package com.learnyourself.grewords;

import android.app.SearchManager;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.learnyourself.adapter.SearchToolBarCursorAdapter;
import com.learnyourself.appinterface.NotifyDataSetChangedInterface;
import com.learnyourself.appinterface.SetupToolbarInterface;
import com.learnyourself.db.DBHelper;
import com.learnyourself.util.Const;
import com.learnyourself.util.PrefHelper;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SetupToolbarInterface, NotifyDataSetChangedInterface{

    final static int WORD_FRAGMENT=1;
    final static int LIST_PART_FRAGMENT=0;
    final static int SEARCH_RESULT_FRAGMENT=2;
    SearchView searchView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewGroup mainFrameLayout, optionalFrameLayout;
    boolean isTablet;

    DBHelper dbHelper;
    SearchToolBarCursorAdapter cursorAdapter;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MAIN","onCreate()");
        setContentView(R.layout.activity_main);

        dbHelper = App.getInstance().getDbHelper();
        cursorAdapter = new SearchToolBarCursorAdapter(this,null,0);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        setupNavigationView();
        setupToolbar();
       mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2081937251059247/3946640619");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                // beginPlayingGame();
            }
        });

        requestNewInterstitial();
        //setupTablayout();
        if(savedInstanceState!=null){
            Log.d("MAIN","savedStated khac null");
        }else{
            Log.d("MAIN","savedStated null");
            mainFrameLayout =(ViewGroup)findViewById(R.id.main_frame_layout);
            if(mainFrameLayout!=null){
                ListPartFragment listPartFragment = new ListPartFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(mainFrameLayout.getId(), listPartFragment, ListPartFragment.class.getName());
               // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            optionalFrameLayout=(ViewGroup)findViewById(R.id.optional_frame_layout);
            if(optionalFrameLayout!=null){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                EmptyFragment emptyFragment = new EmptyFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(optionalFrameLayout.getId(), emptyFragment, EmptyFragment.class.getName());
                // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                App.getInstance().getPrefsHelper().savePref(PrefHelper.IS_TABLET,true);
                isTablet=true;
            }else{
                isTablet=false;
                App.getInstance().getPrefsHelper().savePref(PrefHelper.IS_TABLET,false);
            }
        }
    }
    private void requestNewInterstitial() {
        Log.d("MAIN","request new ads");
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("EC163E300D776459485AD0510CEDDD8A")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed(){
        Log.d("MAIN", "onBackPressed");
        Random rand = new Random();
        int value = rand.nextInt(2);
        Log.d("MAIN", "value = "+value);
        if(!isTablet)
        {
            Log.d("MAIN", "onBackPressed !isTablet . count : "+getFragmentManager().getBackStackEntryCount());
            if (getFragmentManager().getBackStackEntryCount() > 0){
                Log.d("MAIN", "ad");
                if(value ==0){
                    if (mInterstitialAd.isLoaded()) {
                        Log.d("MAIN", "ad is loaded");
                        mInterstitialAd.show();
                    }else{
                        Log.d("MAIN", "ad is unloaded");
                    }
                }
                getFragmentManager().popBackStack();

            }
            else{
                Log.d("MAIN", "onBackPressed  < 0");
                if(value ==0){
                    if (mInterstitialAd.isLoaded()) {
                        Log.d("MAIN", "ad is loaded");
                        mInterstitialAd.show();
                    }else{
                        Log.d("MAIN", "ad is unloaded");
                    }
                }
                super.onBackPressed();
            }

        }else{
            super.onBackPressed();
        }
    }


   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new onQueryTextListener());
        searchView.setSuggestionsAdapter(cursorAdapter);
        searchView.setOnSuggestionListener(new onSuggestionListener());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationView(){
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_menu_about:
                        drawerLayout.closeDrawers();
                        AboutDialog aboutDialog = new AboutDialog();
                        aboutDialog.show(getSupportFragmentManager(),"about dialog");
                        break;

                    case R.id.nav_menu_share:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT,
                                getResources().getString(R.string.share_app));
                        startActivity(Intent.createChooser(share, "Share"));
                        break;
                    case R.id.nav_menu_rate:
                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +
                                getApplicationContext().getPackageName()));
                        startActivity(rateIntent);
                        break;
                }
                return false;
            }
        });
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setupChildFragmentToolbar(int child, Bundle bundle) {
        switch (child){
            case WORD_FRAGMENT:
                String current_section;
                String passed_section = bundle.getString(Const.SECTION);
                int current_part = bundle.getInt(Const.PART, 0);
                int real_part;
                if(passed_section.equals("basic1")){
                    current_section = "BASIC I";
                    real_part = 10-current_part;
                }else
                if(passed_section.equals("basic2")){
                    current_section = "BASIC II";
                    real_part = 10-current_part;
                }else{
                    current_section = "ADVANCE";
                    real_part = 8-current_part;
                }

                getSupportActionBar().setTitle("Part " + real_part + "- " + current_section);

                if(!isTablet){
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
                    toolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            onBackPressed();
                        }
                    });
                }
                break;
            case LIST_PART_FRAGMENT:
                getSupportActionBar().setTitle("GRE Words");
                toolbar.setNavigationIcon(R.drawable.menu);
                toolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (drawerLayout != null) {
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    }
                });
                break;
            case SEARCH_RESULT_FRAGMENT:
                getSupportActionBar().setTitle("Search Result");
                if(!isTablet){
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
                    toolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            onBackPressed();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if(isTablet){
            Log.d("MAIN_ACTIVITY","through.......");
            FragmentManager fragmentManager = getSupportFragmentManager();
            ListPartFragment listPartFragment = (ListPartFragment)fragmentManager.findFragmentByTag(ListPartFragment.class.getName());
            listPartFragment.notifyDataSetChanged();
        }
    }


/*    private void setupTablayout(){
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(tabPageAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
*/

    private class onQueryTextListener implements SearchView.OnQueryTextListener{

        @Override
        public boolean onQueryTextSubmit(String query) {
            searchView.clearFocus();
            Bundle bundle = new Bundle();
            bundle.putString(Const.QUERY, query);
            SearchResultFragment searchResultFragment = new SearchResultFragment();
            searchResultFragment.setArguments(bundle);
            boolean tablet = App.getInstance().getPrefsHelper().getPref(PrefHelper.IS_TABLET);
            if(tablet){
                //tablet
                Log.d("MAIN","tabletkjhkjhlhlk");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.optional_frame_layout, searchResultFragment, SearchResultFragment.class.getName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }else{
                Log.d("MAIN","phone");
                getSupportFragmentManager().beginTransaction()
                        .replace(mainFrameLayout.getId(), searchResultFragment, SearchResultFragment.class.getName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
            return true;
        }
        @Override
        public boolean onQueryTextChange(String newText) {
            //Log.d("OnQueryTextListener",""+newText);
            Cursor c = dbHelper.getSuggestWord(newText);
            cursorAdapter.changeCursor(c);
            return true;
        }
    }

    private class onSuggestionListener implements SearchView.OnSuggestionListener{

        @Override
        public boolean onSuggestionSelect(int position) {
            return false;
        }

        @Override
        public boolean onSuggestionClick(int position) {
            //Log.d("SUCGESTCLICK","position : "+position);
            searchView.clearFocus();
            Cursor c = cursorAdapter.getCursor();
           // Log.d("SUCGESTCLICK","count : "+c.getCount());
            c.moveToPosition(position);
            Bundle bundle = new Bundle();
            bundle.putString(Const.QUERY, c.getString(c.getColumnIndex("name")));
            SearchResultFragment searchResultFragment = new SearchResultFragment();
            searchResultFragment.setArguments(bundle);
            boolean tablet = App.getInstance().getPrefsHelper().getPref(PrefHelper.IS_TABLET);
            if(tablet){
                //tablet
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.optional_frame_layout, searchResultFragment, SearchResultFragment.class.getName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }else{
                getSupportFragmentManager().beginTransaction()
                        .replace(mainFrameLayout.getId(), searchResultFragment, SearchResultFragment.class.getName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
            return true;
        }
    }
}
