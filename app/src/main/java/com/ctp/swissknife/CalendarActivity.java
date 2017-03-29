package com.ctp.swissknife;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class CalendarActivity extends AppCompatActivity implements TimePickerDialog.NoticeDialogListener, DatePickerDialog.DatePickerDialogListener {

    private Button submitBtn;
    private Button cancelBtn;
    private TextView startDateTitle;
    private TextView startDate;
    private TextView startTime;
    private TextView endDateTitle;
    private TextView endDate;
    private TextView endTime;
    private Switch allDay;
    private EditText eventTitle;
    private boolean fullday = false;


    private int startHour=17;
    private int endHour=17;
    private int startMin=0;
    private int endMin=0;
    private String startAPMP;
    private String endAMPM;
    private int startDay=1;
    private int startMonth=2;
    private int startYear=2017;
    private int endMonth=2;
    private int endDay=1;
    private int endYear=2017;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initializeWidgets();
        allDay.setOnCheckedChangeListener(switchListener);
        startTime.setOnClickListener(dateTimeListener);
        endTime.setOnClickListener(dateTimeListener);
        startDate.setOnClickListener(dateTimeListener);
        endDate.setOnClickListener(dateTimeListener);
        submitBtn.setOnClickListener(dateTimeListener);
        cancelBtn.setOnClickListener(dateTimeListener);
        //TODO: Add code to initialize date and time to current day

    }


    View.OnClickListener dateTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startDate:
                    //TODO: Add Code to start date picker, set date on display
                    DatePickerDialog startDatePicker = new DatePickerDialog();
                    startDatePicker.show(getFragmentManager(), "startDate");
                    break;

                case R.id.endDate:
                    //TODO: code to start date picker, set date on display
                    DatePickerDialog endDatePicker = new DatePickerDialog();
                    endDatePicker.show(getFragmentManager(), "endDate");
                    break;

                case R.id.startTime:
                    //TODO: Initialize timepicker, Set Text on display
                    TimePickerDialog startTimePickerDialog = new TimePickerDialog();
                    startTimePickerDialog.show(getFragmentManager(), "startTime");
                    break;

                case R.id.endTime:
                    TimePickerDialog endTimePickerDialog = new TimePickerDialog();
                    endTimePickerDialog.show(getFragmentManager(), "endTime");
                    break;
                case R.id.submitBtn:
                    sendEventIntent();

                    break;
                case R.id.cancelBtn:
                    finish();
                    break;
            }
        }
    };


    private void sendEventIntent() {

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(startYear, startMonth, startDay, startHour, startMin);
        Calendar endTime = Calendar.getInstance();
        endTime.set(endYear, endMonth, endDay, endHour, endMin);

        long stym=beginTime.getTimeInMillis();
        long etym =endTime.getTimeInMillis();
        String title = eventTitle.getText().toString();

        if(etym<stym) {
            Toast.makeText(this,"Change the start time to be before the event",Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, stym)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, etym)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Created using Swiss Knife")
                .putExtra(CalendarContract.Events.ALL_DAY,fullday);
        startActivity(intent);
        finish();

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        String tag = dialog.getTag();
        if(tag.equalsIgnoreCase("startTime") || tag.equalsIgnoreCase("endTime")) {
            Dialog d = dialog.getDialog();
            TimePicker timePicker = (TimePicker) d.findViewById(R.id.timePicker);
            int hour = timePicker.getHour();
            int min = timePicker.getMinute();
            setTime(tag, hour, min);
        }
        else{
            Dialog d = dialog.getDialog();
            DatePicker datePicker = (DatePicker) d.findViewById(R.id.datePicker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            setDate(tag,day,month,year);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private void setDate(String tag, int day, int month, int year){
        if(tag.equalsIgnoreCase("startDate"))
        {
            startDay = day;
            startMonth = month;
            startYear = year;

            startDate.setText(getMonth(month)+" "+day+", "+year);

        }
        else{
            endDay = day;
            endMonth = month;
            endYear = year;

            endDate.setText(getMonth(month)+" "+day+", "+year);
        }
    }

    private void setTime(String tag, int hour, int min){
        String tempH,tempM;
        if(tag.equalsIgnoreCase("startTime"))
        {
            //TODO: Set DisplayTime,Set variables
            startHour = hour;
            startMin = min;
            startAPMP=getAMPM(hour);
            tempH=setHour(hour);
            tempM=setMin(min);

            startTime.setText(tempH+":"+tempM+" "+startAPMP);

        }
        else if(tag.equalsIgnoreCase("endTime"))
        {
            endHour = hour;
            endMin = min;
            endAMPM=getAMPM(hour);
            tempH=setHour(hour);
            tempM=setMin(min);
            endTime.setText(tempH+":"+tempM+" "+endAMPM);
        }
    }

    private String setMin(int min){
        String temp="";
        if(min<10)
            temp="0"+min;
        else
            temp=min+"";

        return temp;
    }

    private String setHour(int hour){
        String temp;
        if(hour>12) {
            temp = hour % 12 + "";

        }
        else if(hour==0){
            temp="12";

        }
        else {
            temp = hour + "";

        }
        return temp;
    }

    private String getAMPM(int hour){
        if(hour>=12)
            return "PM";
        else
            return "AM";
    }

    private String getMonth(int month) {
        switch (month){

            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default:
                return "";
        }
    }



    CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {

                startTime.setVisibility(View.INVISIBLE);
                endTime.setVisibility(View.INVISIBLE);
                startDateTitle.setText("Start Date");
                endDateTitle.setText("End Date");
                fullday=true;
            }
            else
            {
                fullday=false;
                startTime.setVisibility(View.VISIBLE);
                endTime.setVisibility(View.VISIBLE);
                startDateTitle.setText("Start Date and Time");
                endDateTitle.setText("End Date and Time");
            }
        }
    };



    public void initializeWidgets(){
        submitBtn = (Button) findViewById(R.id.submitBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        startDateTitle = (TextView) findViewById(R.id.startDateText);
        startDate = (TextView) findViewById(R.id.startDate);
        startTime = (TextView) findViewById(R.id.startTime);
        endDateTitle = (TextView) findViewById(R.id.EndDateText);
        endDate = (TextView) findViewById(R.id.endDate);
        endTime = (TextView) findViewById(R.id.endTime);
        allDay = (Switch) findViewById(R.id.allDaySwitch);
        eventTitle = (EditText) findViewById(R.id.eventName);
    }



}


