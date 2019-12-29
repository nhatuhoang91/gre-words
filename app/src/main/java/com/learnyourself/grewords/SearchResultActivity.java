package com.learnyourself.grewords;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import com.learnyourself.adapter.SearchResultAdapter;
import com.learnyourself.db.DBHelper;
import com.learnyourself.util.Const;

public class SearchResultActivity extends AppCompatActivity {

    public static void start(Context context, String query){
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(Const.QUERY,query);
        context.startActivity(intent);
    }

    Toolbar toolbar;
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    DBHelper dbHelper;
    SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setupToolbar();
        setupDB();
        setupUI();
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupDB(){
        dbHelper = App.getInstance().getDbHelper();
        String query = getIntent().getStringExtra(Const.QUERY);
        Cursor c = dbHelper.getSuggestWord(query);
        searchResultAdapter = new SearchResultAdapter(this, c);
    }

    private void setupUI(){
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view_search_result_act);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setAdapter(searchResultAdapter);

    }
}
