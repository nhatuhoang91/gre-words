package com.learnyourself.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnyourself.grewords.R;

/**
 * Created by Nha on 12/14/2015.
 */
public class SearchToolBarCursorAdapter extends CursorAdapter{
    private LayoutInflater cursorInflater;

    public SearchToolBarCursorAdapter(Context context, Cursor cursor, int flags){
        super(context,cursor,flags);
        cursorInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = cursorInflater.inflate(R.layout.search_item,parent,false);
        SearchItemHolder holder = new SearchItemHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        SearchItemHolder holder = (SearchItemHolder)view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex("name")));
        String sect = cursor.getString(cursor.getColumnIndex("sect"));

        StringBuilder part = new StringBuilder();
        int numPart = Integer.valueOf(sect.substring(sect.length()-1))+1;

        part.append("Part ");
        part.append(numPart);
        if(sect.length() == 2){
            part.append(" of ADVANCE");
        }else{
            part.append(" of ");
            if(sect.contains("basic1"))
            {
                part.append("BASIC I");
            }else{
                part.append("BASIC II");
            }
        }

        holder.part.setText(part.toString());
    }

    private class SearchItemHolder{
        protected TextView name;
        protected  TextView part;
        public SearchItemHolder (View view){
            name = (TextView)view.findViewById(R.id.search_item_name);
            part = (TextView)view.findViewById(R.id.search_item_part);
        }
    }
}
