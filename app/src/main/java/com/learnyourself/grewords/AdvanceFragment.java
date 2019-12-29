package com.learnyourself.grewords;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.learnyourself.adapter.BasicIAdapter;


/**
 * Created by Nha on 11/29/2015.
 */
public class AdvanceFragment extends Fragment {

    private static final int ADVANCE = 3;
    Context mContext;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private BasicIAdapter basicIAdapter;

    private static AdvanceFragment instance = null;

    public static AdvanceFragment getInstance(){
        if(instance == null){
            instance = new AdvanceFragment();
        }
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Log.d("TAG", "onCreate");
        mContext = context;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("FRIEND_FRAGMENT", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("FRIEND_FRAGMENT", "onCreateView");
        View view = inflater.inflate(R.layout.advance_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_advance);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        basicIAdapter = new BasicIAdapter(mContext, ADVANCE,mRecyclerView);
        mRecyclerView.setAdapter(basicIAdapter);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyDatasetChange();
       // Log.d("TAG", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d("FRIEND_FRAGMENT", "onPaused");
    }
    @Override
    public void onStop() {
        super.onStop();
        //if(gpsHelper!=null)
        // gpsHelper.disconnectToGoogleServer();
        //Log.d("FRIEND_FRAGMENT", "onStop");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
     //   Log.d("FRIEND_FRAGMENT", "onDestroy");
        //if(gpsHelper!=null)
        // gpsHelper.disconnectToGoogleServer();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return true;
    }
    public void notifyDatasetChange(){
        if(basicIAdapter!=null)
            basicIAdapter.notifyDataSetChanged();
    }
}
