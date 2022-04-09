package com.example.Playpalv2.progressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.Playpalv2.R;

public class CustomProgressDialog {

    private Activity activity;
    private AlertDialog dialog;

    public CustomProgressDialog(Activity myActivity){
        activity = myActivity;
    }

    public void initializeProgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_message, null));

        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();

    }

    public void disMiss(){
        dialog.dismiss();
    }
}
