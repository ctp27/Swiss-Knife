package com.ctp.swissknife;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * Created by CTP on 2/6/2017.
 */

public class CallOrMessageDialog extends DialogFragment {

    private Context context;
    private String name;
    private String number;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setData();
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setTitle("Call or Message?")
                .setMessage("Would you like to call or message "+ name+" ?")

                .setPositiveButton("Message", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context,"You want to message",Toast.LENGTH_LONG).show();
                        Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                        messageIntent.setData(Uri.parse("sms:"+number));
                        startActivity(messageIntent);


                    }
                })
                .setNegativeButton("Call", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context,"You want to call",Toast.LENGTH_LONG).show();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+number));
                        startActivity(callIntent);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    private void setData(){
        this.name = MyApplication.getName() ;
        this.number = MyApplication.getNumber();
        this.context = MyApplication.getContactsContext();

    }

}
