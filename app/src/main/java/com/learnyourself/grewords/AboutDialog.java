package com.learnyourself.grewords;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Nha on 12/21/2015.
 */
public class AboutDialog extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.about_dialog_layout, null);
        FloatingActionButton cancelBtn = (FloatingActionButton)v.findViewById(R.id.about_dialog_button_cancel);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(v);
        return builder.create();
    }
}
