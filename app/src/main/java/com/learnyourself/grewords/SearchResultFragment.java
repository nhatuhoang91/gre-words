package com.learnyourself.grewords;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnyourself.adapter.SearchResultAdapter;
import com.learnyourself.appinterface.SetupToolbarInterface;
import com.learnyourself.db.DBHelper;
import com.learnyourself.util.Const;

/**
 * Created by Nha on 1/28/2016.
 */
public class SearchResultFragment extends Fragment{
    static final String TAG = "SearchResultFragment";

    Context mContext;
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    DBHelper dbHelper;
    SearchResultAdapter searchResultAdapter;

    SetupToolbarInterface setupToolbarInterface;
    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.search_result_fragment,container,false);
        setupDB();
        setupUI(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbarInterface=(SetupToolbarInterface)getContext();
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
        setupToolbarInterface.setupChildFragmentToolbar(2,null);
    }

    private void setupDB(){
        dbHelper = App.getInstance().getDbHelper();
        String query = getArguments().getString(Const.QUERY);
        Cursor c = dbHelper.getSuggestWord(query);
        searchResultAdapter = new SearchResultAdapter(mContext, c);
    }

    private void setupUI(View v){
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycle_view_search_result_act);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setAdapter(searchResultAdapter);
    }
}
