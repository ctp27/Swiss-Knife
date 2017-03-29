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
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by CTP on 2/5/2017.
 */

public class AddressDialog extends DialogFragment {

    private Context context;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = MyApplication.getContext();
        final AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.address_dialog, null));
        builder.setTitle("Please Enter Address")

                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
                        EditText editText = (EditText) d.findViewById(R.id.address);
                        String s = editText.getText().toString();
                        openMaps(s,d);

                    }
                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    private void openMaps(String s, Dialog dialog){
        if(s==null || s.equalsIgnoreCase(""))
        {
            Toast.makeText(context,R.string.validate_address,Toast.LENGTH_LONG).show();
        }
        else {
            String urlAddress = "geo:0,0?q=" + s;
            Intent maps = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
            startActivity(maps);
        }
    }





}
