package com.learnyourself.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learnyourself.anim.SlideAnimation;
import com.learnyourself.grewords.R;

import java.util.ArrayList;

/**
 * Created by Nha on 12/15/2015.
 */
public class SearchResultAdapter extends RecycleViewCursorAdapter<SearchResultAdapter.ViewHolder>{

    public Context mContext;
    public SearchResultAdapter(Context context,Cursor cursor){
        super(context,cursor);
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor mCursor) {
        String currentWord = mCursor.getString(mCursor.getColumnIndex("name"));
        holder.word_name.setText(currentWord);
        String sect = mCursor.getString(mCursor.getColumnIndex("sect"));

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

        //if(holder.word_meaning.isShown()) {
            holder.word_layout.setVisibility(RelativeLayout.GONE);
            holder.word_meaning.setVisibility(TextView.GONE);
            holder.example.setVisibility(TextView.GONE);
            holder.synonym.setVisibility(TextView.GONE);

            holder.part.setVisibility((TextView.VISIBLE));
       // }
        holder.part.setText(part.toString());

        holder.word_meaning.setText(mCursor.getString(mCursor.getColumnIndex("description")));
        holder.example.setText("Ex: " + mCursor.getString(mCursor.getColumnIndex("example")));
        if(!mCursor.getString(mCursor.getColumnIndex("synonym")).equals("") ){
            holder.synonym.setText("Synonym: "+mCursor.getString(mCursor.getColumnIndex("synonym")));
        }else{
            holder.synonym.setText("");
        }


        holder.collapse_img.setImageResource(R.drawable.down_arrow);
        holder.collapse_img.setOnClickListener(new onClickListener(mContext, holder));
    }

    private class onClickListener implements View.OnClickListener{
        private Context mContext;
        private RelativeLayout word_layout;
        private TextView word_meaning;
        private TextView example;
        private TextView synonym;
        private TextView part;
        private ImageView collapse_img;
        SlideAnimation slideAnimation;
        public onClickListener(Context context, ViewHolder viewHolder){
            this.word_layout = viewHolder.word_layout;
            this.word_meaning = viewHolder.word_meaning;
            this.example = viewHolder.example;
            this.synonym = viewHolder.synonym;
            this.part= viewHolder.part;
            this.collapse_img = viewHolder.collapse_img;
            this.mContext = context;
            slideAnimation = new SlideAnimation(this.mContext, this.word_layout,
                    this.word_meaning,this.example,this.synonym);
        }
        @Override
        public void onClick(View v) {
            if(this.word_meaning.isShown()){
                this.word_layout.setVisibility(RelativeLayout.GONE);
                this.word_meaning.setVisibility(TextView.GONE);
                this.example.setVisibility(TextView.GONE);
                this.synonym.setVisibility(TextView.GONE);
                this.collapse_img.setImageResource(R.drawable.down_arrow);
                slideAnimation.slideUp();
                this.part.setVisibility(TextView.VISIBLE);
            }else{
                this.word_layout.setVisibility(RelativeLayout.VISIBLE);
                this.word_meaning.setVisibility(TextView.VISIBLE);
                this.example.setVisibility(TextView.VISIBLE);
                this.synonym.setVisibility(TextView.VISIBLE);

                this.collapse_img.setImageResource(R.drawable.up_arrow);
                slideAnimation.slideDown();
                this.part.setVisibility(TextView.INVISIBLE);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView word_name;
        private TextView part;
        private RelativeLayout word_layout;
        private TextView word_meaning;
        private TextView example;
        private TextView synonym;
        ArrayList<View> listTextView;
        private ImageView collapse_img;

        public ViewHolder(View view){
            super(view);
            this.word_name = (TextView)view.findViewById(R.id.textview_search_result_act_word_name);
            this.part = (TextView)view.findViewById(R.id.textview_search_result_act_part);
            this.word_layout = (RelativeLayout)view.findViewById(R.id.word_layout);
            this.word_meaning = (TextView)view.findViewById(R.id.word_meaning);
            this.example = (TextView)view.findViewById(R.id.example);
            this.synonym = (TextView)view.findViewById(R.id.synonym);
            this.collapse_img = (ImageView)view.findViewById(R.id.imageview_search_result_act_collapse);

            listTextView = new ArrayList<>();
            listTextView.add(this.word_layout);
            listTextView.add(this.word_meaning);
            listTextView.add(this.example);
            listTextView.add(this.synonym);
            listTextView.add(this.part);
        }
    }
}
