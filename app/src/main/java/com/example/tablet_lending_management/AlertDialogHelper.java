package com.example.tablet_lending_management;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.example.tablet_lending_management.Models.LoanWithDetails;

// Dialog Template 
public class AlertDialogHelper {


    public static void showDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText,
                                  DialogInterface.OnClickListener positiveButtonListener, DialogInterface.OnClickListener negativeButtonListener){


        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positiveButtonListener)
                .setNegativeButton(negativeButtonText, negativeButtonListener);

        builder.setCancelable(false);

        // Show the dialog
        builder.create().show();

    }





}
