package com.learnyourself.object;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;

import com.learnyourself.adapter.BasicIAdapter;
import com.learnyourself.appinterface.StartWordFragmentInterface;
import com.learnyourself.db.DBHelper;
import com.learnyourself.grewords.ListPartFragment;
import com.learnyourself.grewords.R;
import com.learnyourself.util.DialogUtils;

import java.util.List;
import java.util.Timer;

/**
 * Created by Nha on 12/29/2015.
 */
public abstract class HandleFragment {
    protected Context mContext;
    protected DBHelper dbHelper;

    protected StartWordFragmentInterface startWordFragmentInterface;

    protected Timer timer;
    public HandleFragment(Context context, DBHelper dbHelper){
        this.mContext = context;
        this.dbHelper = dbHelper;
        timer = new Timer();

        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        ListPartFragment listPartFragment = (ListPartFragment)fragmentManager.findFragmentByTag(ListPartFragment.class.getName());
        startWordFragmentInterface=(StartWordFragmentInterface)listPartFragment;
    }


    protected abstract void handle(final BasicIAdapter.ViewHolder viewHolder, final int position, final String section, final int part);

    protected void createCongratulationDialog(String title, String message){
        DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        };
        DialogUtils.createAlertDialog(mContext, positiveButtonListener, title, message).show();
    }

    protected void createResetPartDialog(final BasicIAdapter.ViewHolder viewHolder,final String sect, String message){
        String title = "Reset Part";
        DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.resetPart(sect);
                viewHolder.progressBar.setProgress(0);
                viewHolder.progressBar.setText(0 + "/50");
            }
        };

        DialogInterface.OnClickListener negativeButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        };
        DialogUtils.createResetPartDialog(mContext, positiveButtonListener, negativeButtonListener, title, message).show();
    }

    protected void setProgressBar(DBHelper dbHelper, BasicIAdapter.ViewHolder viewHolder, String sect){
        //Log.d("ADAPTER I","start : "+start+"  ;  end : "+(start+49));
        int count = dbHelper.count(sect);
        viewHolder.progressBar.setProgress(count);
        viewHolder.progressBar.setText(count + "/50");
    }

    protected void showLock(BasicIAdapter.ViewHolder viewHolder){
        viewHolder.card_layout.setBackgroundResource(R.drawable.lock_layout);
        // if(viewHolder.progressBar.isShown())
        viewHolder.progressBar.setVisibility(ProgressBar.GONE);
        viewHolder.image_lock.setVisibility(ImageView.VISIBLE);
        viewHolder.reset_part.setVisibility((ImageView.GONE));
        viewHolder.textSwitcher.setVisibility(TextSwitcher.GONE);
        viewHolder.image_next_word.setVisibility(ImageView.GONE);
    }

    protected void showActive(BasicIAdapter.ViewHolder viewHolder){
        viewHolder.card_layout.setBackgroundResource(R.drawable.active_layout);
        viewHolder.image_lock.setVisibility(ImageView.GONE);
        viewHolder.reset_part.setVisibility((ImageView.VISIBLE));
    }
}
