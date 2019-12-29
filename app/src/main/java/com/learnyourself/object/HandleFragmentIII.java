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
public class HandleFragmentIII extends HandleFragment{

    SparseArray<ArrayList<String>> list;
    Random random;
    public HandleFragmentIII(Context context, DBHelper dbHelper){
        super(context, dbHelper);
        list = new SparseArray<>();
        random = new Random();
    }

    @Override
    public void handle(final BasicIAdapter.ViewHolder viewHolder, final int position,final String section,final int part) {
        if(section.equals("a")) {
            if (position +1 > part) {
                showLock(viewHolder);
            }else{
                showActive(viewHolder);
                viewHolder.reset_part.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = "Reset Part " + (position + 1) + " can not undo";
                        String sect = "a" + (7 - position );
                        createResetPartDialog(viewHolder, sect, message);
                    }
                });

                if(list.get(position)==null){
                    ArrayList<String> word_list = dbHelper.getWordName("a"+(7-position));
                    list.put(position,word_list);
                }
                setProgressBar(dbHelper, viewHolder, "a" + (7 - position));
                if(position+1 == part){
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
        } else {
            if (section.equals("basic1") || section.equals("basic2")) {
                showLock(viewHolder);
            }
        }

        viewHolder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (section.equals("a")) {
                    if (position + 1 > part) {
                        String title = "Locked Part";
                        String message = "You must complete previous part before move to this part";
                        createCongratulationDialog(title, message);
                    } else {
                        startWordFragmentInterface.startWordFragment("a",7-position);
                    }
                } else if (section.equals("basic1") || section.equals("basic2")) {
                    String title = "Locked Part";
                    String message = "You must complete previous part before move to this part";
                    createCongratulationDialog(title,message);
                }
            }
        });
    }
}
