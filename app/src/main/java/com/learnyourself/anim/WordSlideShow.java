package com.learnyourself.anim;

import android.os.Handler;
import android.widget.TextSwitcher;

import com.learnyourself.object.Word;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by Nha on 12/29/2015.
 */
public class WordSlideShow extends TimerTask{
    private Handler handler;
    private ArrayList<String> word_list;
    private TextSwitcher textSwitcher;

    Random random;
    int i=0;
    public WordSlideShow(TextSwitcher textSwitcher, ArrayList<String> word_list){
        this.textSwitcher = textSwitcher;
        this.word_list = new ArrayList<String>();
        this.word_list = word_list;
        handler = new Handler();
        random = new Random();
    }

    @Override
    public void run() {
        handler.post(new Runnable() {
            @Override
            public void run() {
               // i = random.nextInt(word_list.size());
                if(i==word_list.size()){
                    i=0;
                }
                textSwitcher.setText(word_list.get(i));
                i++;
            }
        });
    }
}
