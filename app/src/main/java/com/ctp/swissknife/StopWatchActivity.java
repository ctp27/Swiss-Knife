package com.ctp.swissknife;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopWatchActivity extends AppCompatActivity implements View.OnClickListener {

    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    final int MSG_RESET_TIMER = 3;
    final int REFRESH_RATE = 60;

    private Stopwatch timer;
    private TextView timeView;
    private Button btnStart, btnStop, btnReset;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        timer = new Stopwatch();
        timeView = (TextView) findViewById(R.id.timeView);
        btnStart = (Button) findViewById(R.id.startBtn);
        btnStop = (Button) findViewById(R.id.stopBtn);
        btnReset = (Button) findViewById(R.id.resetBtn);

        btnReset.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        myHandler = new MyHandler();

    }


    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    timer.resume(); //start timer
                    myHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    timeView.setText("" + timer);
                    myHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER, REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running

                case MSG_STOP_TIMER:
                    myHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    timer.stop();//stop timer
                    timeView.setText("" + timer);
                    break;

                case MSG_RESET_TIMER:
                    timer.reset();
                    timeView.setText("00:00:00:00");
                    break;
                default:

                    break;
            }
        }
    }



    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.startBtn:
                myHandler.sendEmptyMessage(MSG_START_TIMER);
                btnReset.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                break;

            case R.id.stopBtn:
                myHandler.sendEmptyMessage(MSG_STOP_TIMER);
                btnStop.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                break;

            case R.id.resetBtn:
                myHandler.sendEmptyMessage(MSG_RESET_TIMER);
                btnReset.setVisibility(View.INVISIBLE);
                break;

            default:
                break;

        }
    }
}