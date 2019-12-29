package com.learnyourself.object;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import com.learnyourself.adapter.BasicIAdapter;
import com.learnyourself.db.DBHelper;
import com.learnyourself.grewords.WordActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nha on 12/29/2015.
 */
public class HandleFragmentII extends HandleFragment {

    SparseArray<ArrayList<String>> list;
    Random random;
    public HandleFragmentII(Context context, DBHelper dbHelper){
        super(context,dbHelper);
        list = new SparseArray<>();
        random = new Random();
    }

    @Override
    public void handle(final BasicIAdapter.ViewHolder viewHolder, final int position, final String section, final int part) {
        if(section.equals("basic2")) {
            if (position +1 > part) {
                showLock(viewHolder);
            }else{
                setViewInActive(viewHolder,position,section,part);
            }
        }else if (section.equals("basic1")) {
            showLock(viewHolder);
        } else {
            setViewInActive(viewHolder,position,section,part);
        }

        viewHolder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (section.equals("basic2")) {
                    if (position + 1 > part) {
                        String title = "Locked Part";
                        String message = "You must complete previous part before move to this part";
                        createCongratulationDialog(title, message);
                    } else {
                        //WordActivity.start(mContext, "basic2",9-position);
                        startWordFragmentInterface.startWordFragment("basic2",9-position);
                    }
                } else if (section.equals("basic1")) {
                    String title = "Locked Part";
                    String message = "You must complete previous part before move to this part";
                    createCongratulationDialog(title,message);
                }else{
                    //WordActivity.start(mContext,"basic2",9-position);
                    startWordFragmentInterface.startWordFragment("basic2",9-position);
                }
            }
        });
    }

    private void setViewInActive(final BasicIAdapter.ViewHolder viewHolder,final int position, final String section,int part){
        showActive(viewHolder);
        viewHolder.reset_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Reset Part " + (position + 1) + " can not undo";
                String sect = "basic2" + (9 - position);
                createResetPartDialog(viewHolder, sect, message);
            }
        });

        if(list.get(position)==null){
            ArrayList<String> word_list = dbHelper.getWordName("basic2"+(9-position));
            list.put(position,word_list);
        }
        setProgressBar(dbHelper, viewHolder, "basic2" + (9 - position));
        if(position+1 == part && section.equals("basic2")){
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.textSwitcher.setVisibility(View.GONE);
            viewHolder.image_next_word.setVisibility(View.GONE);
            // WordSlideShow wordSlideShow = new WordSlideShow(viewHolder.textSwitcher, list.get(position));
        }else{
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.textSwitcher.setVisibility(View.VISIBLE);
            viewHolder.image_next_word.setVisibility(View.VISIBLE);
            viewHolder.textSwitcher.setText(list.get(position).get(random.nextInt(50)));
            viewHolder.image_next_word.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.textSwitcher.setText(list.get(position).get(random.nextInt(50)));
                }
            });
        }
    }
}
