package com.ctp.swissknife;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.widget.TimePicker;

/**
 * Created by CTP on 2/5/2017.
 */

public class AlarmDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = MyApplication.getContext();
        final AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.alarm_dialog, null));
        builder.setTitle("Set the Alarm")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
                        TimePicker timePicker = (TimePicker) d.findViewById(R.id.timePicker);
                        int hour=timePicker.getHour();
                        int min= timePicker.getMinute();
                        setAlarm(hour,min);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    private void setAlarm(int hours,int min){
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, min);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
        startActivity(intent);
    }
}
