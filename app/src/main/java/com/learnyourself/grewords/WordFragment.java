package com.learnyourself.grewords;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learnyourself.anim.SlideAnimation;
import com.learnyourself.appinterface.NotifyDataSetChangedInterface;
import com.learnyourself.appinterface.SetupToolbarInterface;
import com.learnyourself.customview.TextProgressBar;
import com.learnyourself.db.DBHelper;
import com.learnyourself.object.Word;
import com.learnyourself.util.Const;
import com.learnyourself.util.DialogUtils;
import com.learnyourself.util.PrefHelper;
import com.learnyourself.util.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nha on 1/25/2016.
 */
public class WordFragment extends Fragment{

    Context mContext;
    CardView cardView;
    RelativeLayout wordLayout;
    TextView word;
    TextView view_count;
    TextView word_meaning;
    TextView example;
    TextView synonym;
    // FloatingActionButton collapse_button;
    ImageView collapse_image;
    FloatingActionButton ok_button;
    FloatingActionButton next_button;

    TextProgressBar progressBar_word;

    SlideAnimation slideAnimation;

    SetupToolbarInterface setupToolbarInterface;
    NotifyDataSetChangedInterface notifyDataSetChangedInterface;
    DBHelper dbHelper;
    ArrayList<Word> arrayWord;
    int current_pos;

    ArrayList<Word> arrayWordActive;
    int current_active_pos;

    String current_section;
    int current_part;
    int record;

    long startTime;
    long endTime;
    int numView;

    boolean isTablet;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext=context;
        Log.d("WORD","onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("WORD", "onCreated");
        setupDB();//note setupDB first
        isTablet=App.getInstance().getPrefsHelper().getPref(PrefHelper.IS_TABLET);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.word_fragment,container,false);
        setupUI(v);
        return v;
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d("WORD", "onResume");
        setupToolbarInterface=(SetupToolbarInterface)getContext();
        notifyDataSetChangedInterface=(NotifyDataSetChangedInterface)getContext();
        setupToolbar();
        numView=0;
        startElapseTime();
    }

    @Override
      public void onPause(){
        super.onPause();
        endElapseTime();
    }

    @Override
      public void onStop(){
        super.onStop();
    }

    @Override
      public void onDestroy(){
        super.onDestroy();
    }

    private void setupToolbar(){
        setupToolbarInterface.setupChildFragmentToolbar(1,getArguments());
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupUI(View v){
        cardView = (CardView)v.findViewById(R.id.card_view);
        wordLayout = (RelativeLayout)v.findViewById(R.id.word_layout);

        word = (TextView)v.findViewById(R.id.word);
        view_count = (TextView)v.findViewById(R.id.view_count);
        word_meaning =(TextView)v.findViewById(R.id.word_meaning);
        example =(TextView)v.findViewById(R.id.example);
        synonym = (TextView)v.findViewById(R.id.synonym);

        collapse_image = (ImageView)v.findViewById(R.id.collapse_image);
        ok_button =(FloatingActionButton)v.findViewById(R.id.ok_button);

            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                    numView++;
                    if (word_meaning.isShown()) {
                        slideAnimation.slideUp();
                        collapse_image.setImageResource(R.drawable.down_arrow);
                    }

                    setProgress(progressBar_word, ++record);
                    if (current_pos < arrayWord.size() - 1) {
                        dbHelper.updateView(arrayWordActive.get(current_active_pos).getName());
                        dbHelper.setRemember(arrayWordActive.get(current_active_pos).getName());
                        current_pos++;
                        arrayWordActive.remove(current_active_pos);
                        arrayWordActive.add(arrayWord.get(current_pos));
                        //note :  after remove current word, index will change so we will receive another word, not same.
                        // so don't need check for avoid same word.
                        Random random = new Random();
                        current_active_pos = random.nextInt(arrayWordActive.size());
                        showNextWord();
                    } else {
                        if (arrayWordActive.size() == 1) {
                            dbHelper.updateView(arrayWordActive.get(current_active_pos).getName());
                            dbHelper.setRemember(arrayWordActive.get(current_active_pos).getName());
                            arrayWordActive.remove(current_active_pos);
                            String section = App.getInstance().getPrefsHelper().getPref(PrefHelper.CURRENT_SECTION);
                            int part = App.getInstance().getPrefsHelper().getPref(PrefHelper.CURRENT_PART);
                            boolean isLearnMode;
                            if (section.equals("a")) {
                                isLearnMode = current_part == 8 - part;
                            } else {
                                isLearnMode = current_part == 10 - part;
                            }
                            if (current_section.equals(section) && isLearnMode) {
                                //learn mode
                                switch (section) {
                                    case "basic1":
                                        if (part == 10) {
                                            dbHelper.resetPart("basic10");
                                            setProgress(progressBar_word,0);
                                            if(isTablet){
                                                setupDB();
                                            }
                                            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_SECTION, "basic2");
                                            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_PART, 1);
                                            String title = "Good Job!";
                                            String message = "Congratulation! You passed BASIC I. Now, you unlocked BASIC II, Enjoy!.";
                                            createCongratulationDialog(title, message);
                                            notifyDataSetChangedInterface.notifyDataSetChanged();
                                        } else {
                                            dbHelper.resetPart("basic1" + (10 - part));
                                            setProgress(progressBar_word, 0);
                                            if(isTablet){
                                                setupDB();
                                            }
                                            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_PART, part + 1);
                                            createFinishPartDialog(part);
                                            notifyDataSetChangedInterface.notifyDataSetChanged();
                                        }
                                        break;
                                    case "basic2":
                                        if (part == 10) {
                                            dbHelper.resetPart("basic20");
                                            setProgress(progressBar_word, 0);
                                            if(isTablet){
                                                setupDB();
                                            }
                                            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_SECTION, "a");
                                            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_PART, 1);
                                            String title = "Good Job!";
                                            String message = "Congratulation! You passed BASIC II. Now, you unlocked ADVANCE, Enjoy!.";
                                            createCongratulationDialog(title, message);
                                            notifyDataSetChangedInterface.notifyDataSetChanged();
                                        } else {
                                            dbHelper.resetPart("basic2" + (10 - part));
                                            setProgress(progressBar_word, 0);
                                            if(isTablet){
                                                setupDB();
                                            }
                                            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_PART, part + 1);
                                            createFinishPartDialog(part);
                                            notifyDataSetChangedInterface.notifyDataSetChanged();
                                        }
                                        break;
                                    case "a":
                                        if (part == 8) {
                                            dbHelper.resetPart("a0");
                                            setProgress(progressBar_word, 0);
                                            if(isTablet){
                                                setupDB();
                                            }
                                            String title = "Good Job!";
                                            String message = "Congratulation! You passed all of GRE words. You can review to make sure that you remembered them.";
                                            createCongratulationDialog(title, message);
                                            notifyDataSetChangedInterface.notifyDataSetChanged();
                                        } else {
                                            dbHelper.resetPart("a" + (8 - part));
                                            setProgress(progressBar_word,0);
                                            if(isTablet){
                                                setupDB();
                                            }
                                            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_PART, part + 1);
                                            createFinishPartDialog(part);
                                            notifyDataSetChangedInterface.notifyDataSetChanged();
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                //review mode
                                dbHelper.resetPart("" + current_section + current_part);
                                setProgress(progressBar_word,0);
                                if(isTablet){
                                    setupDB();
                                }
                                String title = "Good Job!";
                                String message = "I hope the review mode be helpful to you.";
                                createCongratulationDialog(title, message);
                            }
                        } else {
                            dbHelper.updateView(arrayWordActive.get(current_active_pos).getName());
                            dbHelper.setRemember(arrayWordActive.get(current_active_pos).getName());
                            arrayWordActive.remove(current_active_pos);
                            Random random = new Random();
                            current_active_pos = random.nextInt(arrayWordActive.size());
                            showNextWord();
                        }
                    }
                    }catch(Exception e){
                        Log.e("WORD_FRAGMENT", e.toString());
                    }
                }
            });

        next_button = (FloatingActionButton)v.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numView++;
                dbHelper.updateView(arrayWordActive.get(current_active_pos).getName());
                if(arrayWordActive.size()==1){
                    return;
                }
                if (word_meaning.isShown()) {
                    slideAnimation.slideUp();
                    collapse_image.setImageResource(R.drawable.down_arrow);
                }

                Random random = new Random();
                int temp_pos = current_active_pos;
                while(temp_pos == current_active_pos){
                    temp_pos = random.nextInt(arrayWordActive.size());
                }
                current_active_pos = temp_pos;
                showNextWord();
            }
        });

        slideAnimation = new SlideAnimation(mContext,wordLayout,word_meaning,example,synonym);

        progressBar_word = (TextProgressBar) v.findViewById(R.id.progressBar_word);
        setProgress(progressBar_word,record);

        collapse_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (word_meaning.isShown()) {
                    slideAnimation.slideUp();
                    collapse_image.setImageResource(R.drawable.down_arrow);
                } else {
                    slideAnimation.slideDown();
                    collapse_image.setImageResource(R.drawable.up_arrow);
                }
            }
        });

        showNextWord();
    }

    private void setupDB(){
        current_section = getArguments().getString(Const.SECTION);
        current_part = getArguments().getInt(Const.PART,-1);
        String section = current_section+current_part;
        dbHelper = App.getInstance().getDbHelper();
        arrayWord = new ArrayList<>();
        arrayWordActive = new ArrayList<>();
        if(current_part !=-1) {
            arrayWord = dbHelper.getWord(section);
            Log.d("WORD","arrayword size:"+arrayWord.size());
            current_pos=0;

            int length = arrayWord.size();
            if(length < 10){
                for(int i = 0; i<length;i++){
                    arrayWordActive.add(arrayWord.get(i));
                }
                current_pos = length-1;
            }else{
                for(int i = 0; i<10; i++){
                    arrayWordActive.add(arrayWord.get(i));
                    current_pos=9;
                }
            }
            current_active_pos = 0;
            record = 50 - arrayWord.size();
        }
    }

    private void showNextWord(){
        word.setText(arrayWordActive.get(current_active_pos).getName());
        int count = dbHelper.getViewCount(arrayWordActive.get(current_active_pos).getName());
        view_count.setText("Viewed: "+count);
        word_meaning.setText(arrayWordActive.get(current_active_pos).getMeaning());
        example.setText("Ex: " + arrayWordActive.get(current_active_pos).getExample());
        if(!arrayWordActive.get(current_active_pos).getSynonym().equals("")){
            synonym.setText("Synonym: "+arrayWordActive.get(current_active_pos).getSynonym());
        }else{
            synonym.setText("");
        }

        if(word_meaning.isShown())
            slideAnimation.slideUp();
    }

    private void createCongratulationDialog(String title, String message){
        DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!isTablet)
                    getActivity().onBackPressed();
            }
        };
        DialogInterface.OnClickListener negativeButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +
                        mContext.getPackageName()));
                startActivity(rateIntent);
                if(!isTablet)
                    getActivity().onBackPressed();
            }
        };
        DialogUtils.createCongratulationDialog(getActivity(), positiveButtonListener, negativeButtonListener, title, message).show();
    }

    private void createFinishPartDialog(int part){
        String title = "Good Job!";
        String message = "Congratulation! You passed part "+part+"." +
                " You unlocked part "+(part+1)+". Enjoy!.";
        createCongratulationDialog(title, message);
    }

    private void setProgress(TextProgressBar progressBar, int progress){
        progressBar.setProgress(progress);
        progressBar.setText(progress + "/50");
    }

    private void startElapseTime(){
        startTime = SystemClock.elapsedRealtime();
    }
    private void endElapseTime(){
        //Log.d("WORD_ACT","endElapseTime function");
        // Log.d("WORD_ACT","startTime: "+startTime);
        endTime = SystemClock.elapsedRealtime();
        // Log.d("WORD_ACT","endTime: "+endTime);
        long elapse = (endTime - startTime)/1000;
        // Log.d("WORD_ACT", "elapseTime: " + elapse);
        String date = TimeUtils.createDate();
        // Log.d("WORD_ACT","date: "+date);
        dbHelper.updateStats(date, elapse, numView);
    }
}
