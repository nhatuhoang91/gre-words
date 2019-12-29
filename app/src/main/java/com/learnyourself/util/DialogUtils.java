package com.learnyourself.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;

import com.learnyourself.grewords.R;

/**
 * Created by Nha on 12/9/2015.
 */
public class DialogUtils {

    public static Dialog createCongratulationDialog(Context context, DialogInterface.OnClickListener positiveButtonListener,
                                                    DialogInterface.OnClickListener negativeButtonListener,
                                                    String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok, positiveButtonListener);
        builder.setNegativeButton(R.string.rate_us,negativeButtonListener);
        builder.setCancelable(false);
        return builder.create();
    }

    public static Dialog createAlertDialog(Context context, DialogInterface.OnClickListener positiveButtonListener,
                                                    String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok, positiveButtonListener);
        return builder.create();
    }

    public static Dialog createResetPartDialog(Context context, DialogInterface.OnClickListener positiveButtonListener,
                                               DialogInterface.OnClickListener negativeButtonListener,
                                                    String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok, positiveButtonListener);
        builder.setNegativeButton(R.string.cancel, negativeButtonListener);
        return builder.create();
    }
}
