package com.learnyourself.grewords;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.learnyourself.adapter.BasicIAdapter;

/**
 * Created by Nha on 11/29/2015.
 */
public class BasicIFragment extends Fragment {
 //final static String TAG = "BASIC_I_FRAGMENT";
    private static final int BASIC_I = 1;
    Context mContext;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BasicIAdapter basicIAdapter;

    private static BasicIFragment instance = null;

    public static BasicIFragment getInstance() {
       if (instance == null) {
            instance= new BasicIFragment();
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
       // Log.d("TAG", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("TAG", "onCreateView");
        View view = inflater.inflate(R.layout.basic1_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_basic1);
        linearLayoutManager = new LinearLayoutManager(mContext);

        basicIAdapter = new BasicIAdapter(getContext(), BASIC_I, mRecyclerView);
        mRecyclerView.setAdapter(basicIAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
       // Log.d("TAG", "onPaused");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.d("TAG", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     //   Log.d("TAG", "onDestroy");
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
