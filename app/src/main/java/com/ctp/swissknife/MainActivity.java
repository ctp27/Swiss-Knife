package com.ctp.swissknife;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSION_CONTACT = 11;
    private final int MY_PERMISSION_CALL = 12;
    private final int MY_PERMISSIONS = 13;
    private ImageView myImg;
    private ImageView contactBlade;
    private ImageView redialBlade;
    private ImageView calendarBlade;
    private ImageView alarmBlade;
    private ImageView mapsBlade;

    private RelativeLayout relative;

    private Animation rotateanimation;
    private Animation contactBladeAnimate;
    private Animation redialBladeAnimate;
    private Animation calendarBladeAnimate;
    private Animation mapsBladeAnimate;
    private Animation alarmBladeAnimate;

    private Button mapBtn;
    private Button stopwatchBtn;
    private Button contactBtn;
    private Button redialBtn;
    private Button calendarBtn;
    private Button alarmBtn;

    private TextView open;
    private TextView close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setContext(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactBlade = (ImageView) findViewById(R.id.contactblade);
        redialBlade = (ImageView) findViewById(R.id.redialblade);
        calendarBlade = (ImageView) findViewById(R.id.calendarBlade);
        mapsBlade = (ImageView) findViewById(R.id.mapBlade);
        alarmBlade = (ImageView) findViewById(R.id.alarmBlade);
        myImg = (ImageView) findViewById(R.id.bladeOne);

        mapBtn = (Button) findViewById(R.id.mapBtn);
        stopwatchBtn = (Button) findViewById(R.id.stopwatchBtn);
        contactBtn = (Button) findViewById(R.id.contactBtn);
        redialBtn = (Button) findViewById(R.id.redialBtn);
        calendarBtn = (Button) findViewById(R.id.calendarBtn);
        alarmBtn = (Button) findViewById(R.id.alarmBtn);

        open = (TextView) findViewById(R.id.openBtn);
        close = (TextView) findViewById(R.id.closeBtn);
        relative = (RelativeLayout) findViewById(R.id.content_main);

        open.setOnClickListener(openBtnListener);
        close.setOnClickListener(closeBtnListener);

        mapBtn.setOnClickListener(imageListener);
        stopwatchBtn.setOnClickListener(imageListener);
        contactBtn.setOnClickListener(imageListener);
        redialBtn.setOnClickListener(imageListener);
        calendarBtn.setOnClickListener(imageListener);
        alarmBtn.setOnClickListener(imageListener);

        PermissionChecker.checkAllPermissions(this, this, MY_PERMISSIONS);


    }

    View.OnClickListener imageListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.stopwatchBtn:
                    intent = new Intent(MainActivity.this, StopWatchActivity.class);
                    break;

                case R.id.calendarBtn:
                    intent = new Intent(MainActivity.this, CalendarActivity.class);
                    break;

                case R.id.redialBtn:
                    if (PermissionChecker.checkCallPermission(MainActivity.this, MainActivity.this, MY_PERMISSION_CALL))
                        intent = getCallIntent();
                    break;

                case R.id.contactBtn:
                    if (PermissionChecker.checkAllPermissions(MainActivity.this, MainActivity.this, MY_PERMISSION_CONTACT))
                        intent = new Intent(MainActivity.this, ContactsActivity.class);

                    break;
                case R.id.mapBtn:
                    AddressDialog dialog = new AddressDialog();
                    dialog.show(getFragmentManager(), "Address Dialog");
                    break;

                case R.id.alarmBtn:
                    AlarmDialog alarmDialog = new AlarmDialog();
                    alarmDialog.show(getFragmentManager(), "Time picker");
                    break;

            }
            if (intent != null)
                startActivity(intent);
        }
    };

//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getLastContact(Context context) {
        String number = null;
        number = CallLog.Calls.getLastOutgoingCall(context);
        Log.d("contact", "The number is" + number);

        return number;
    }

    public Intent getCallIntent() {
        String no = getLastContact(MainActivity.this);
        if (no == null || no.equalsIgnoreCase("")) {
            Toast.makeText(MainActivity.this, "No Recently Dialled Numbers Found", Toast.LENGTH_LONG).show();
            return null;
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + no));
        return callIntent;
    }


    View.OnClickListener openBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rotateanimation = new RotateAnimation(0, 35,
                    Animation.RELATIVE_TO_PARENT, 0.5f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            rotateanimation.setDuration(1500);
            rotateanimation.setRepeatCount(0);

            rotateanimation.setFillAfter(true);

            myImg.setAnimation(rotateanimation);
            rotateanimation.start();


            contactBladeAnimate = new RotateAnimation(0, 75,
                    Animation.RELATIVE_TO_PARENT, 0.41f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            contactBladeAnimate.setDuration(1500);
            contactBladeAnimate.setRepeatCount(0);

            contactBladeAnimate.setFillAfter(true);

            contactBlade.setAnimation(contactBladeAnimate);
            contactBladeAnimate.start();


            redialBladeAnimate = new RotateAnimation(0, -70,
                    Animation.RELATIVE_TO_PARENT, 0.41f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            redialBladeAnimate.setDuration(1500);
            redialBladeAnimate.setRepeatCount(0);

            redialBladeAnimate.setFillAfter(true);
            redialBlade.setAnimation(redialBladeAnimate);
            redialBladeAnimate.start();
//            relative.invalidate();

            calendarBladeAnimate = new RotateAnimation(0, 40,
                    Animation.RELATIVE_TO_PARENT, 0.3f,
                    Animation.RELATIVE_TO_PARENT, 0.15f);
            calendarBladeAnimate.setDuration(1500);
            calendarBladeAnimate.setRepeatCount(0);

            calendarBladeAnimate.setFillAfter(true);
            calendarBlade.setAnimation(calendarBladeAnimate);
            calendarBladeAnimate.start();


            mapsBladeAnimate = new RotateAnimation(0, -85,
                    Animation.RELATIVE_TO_PARENT, 0.39f,
                    Animation.RELATIVE_TO_PARENT, 0.15f);
            mapsBladeAnimate.setDuration(1500);
            mapsBladeAnimate.setRepeatCount(0);

            mapsBladeAnimate.setFillAfter(true);
            mapsBlade.setAnimation(mapsBladeAnimate);
            mapsBladeAnimate.start();


            alarmBladeAnimate = new RotateAnimation(0, 85,
                    Animation.RELATIVE_TO_PARENT, 0.39f,
                    Animation.RELATIVE_TO_PARENT, 0.1f);
            alarmBladeAnimate.setDuration(1500);
            alarmBladeAnimate.setRepeatCount(0);

            alarmBladeAnimate.setFillAfter(true);
            alarmBlade.setAnimation(alarmBladeAnimate);
            alarmBladeAnimate.start();
            relative.invalidate();

            mapBtn.setVisibility(View.VISIBLE);
            stopwatchBtn.setVisibility(View.VISIBLE);
            contactBtn.setVisibility(View.VISIBLE);
            redialBtn.setVisibility(View.VISIBLE);
            calendarBtn.setVisibility(View.VISIBLE);
            alarmBtn.setVisibility(View.VISIBLE);

            open.setVisibility(View.INVISIBLE);
            close.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener closeBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rotateanimation = new RotateAnimation(35, 0,
                    Animation.RELATIVE_TO_PARENT, 0.5f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            rotateanimation.setDuration(1500);
            rotateanimation.setRepeatCount(0);

            rotateanimation.setFillAfter(true);
            myImg.setAnimation(rotateanimation);
            rotateanimation.start();


            contactBladeAnimate = new RotateAnimation(75, 0,
                    Animation.RELATIVE_TO_PARENT, 0.41f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            contactBladeAnimate.setDuration(1500);
            contactBladeAnimate.setRepeatCount(0);

            contactBladeAnimate.setFillAfter(true);
            contactBlade.setAnimation(contactBladeAnimate);
            contactBladeAnimate.start();


            redialBladeAnimate = new RotateAnimation(-70, 0,
                    Animation.RELATIVE_TO_PARENT, 0.41f,
                    Animation.RELATIVE_TO_PARENT, 0.5f);
            redialBladeAnimate.setDuration(1500);
            redialBladeAnimate.setRepeatCount(0);

            redialBladeAnimate.setFillAfter(true);
            redialBlade.setAnimation(redialBladeAnimate);
            redialBladeAnimate.start();
            relative.invalidate();

            calendarBladeAnimate = new RotateAnimation(40, 0,
                    Animation.RELATIVE_TO_PARENT, 0.3f,
                    Animation.RELATIVE_TO_PARENT, 0.15f);
            calendarBladeAnimate.setDuration(1500);
            calendarBladeAnimate.setRepeatCount(0);

            calendarBladeAnimate.setFillAfter(true);
            calendarBlade.setAnimation(calendarBladeAnimate);
            calendarBladeAnimate.start();
            relative.invalidate();


            mapsBladeAnimate = new RotateAnimation(-85, 0,
                    Animation.RELATIVE_TO_PARENT, 0.39f,
                    Animation.RELATIVE_TO_PARENT, 0.15f);
            mapsBladeAnimate.setDuration(1500);
            mapsBladeAnimate.setRepeatCount(0);

            mapsBladeAnimate.setFillAfter(true);
            mapsBlade.setAnimation(mapsBladeAnimate);
            mapsBladeAnimate.start();
            relative.invalidate();

            alarmBladeAnimate = new RotateAnimation(85, 0,
                    Animation.RELATIVE_TO_PARENT, 0.39f,
                    Animation.RELATIVE_TO_PARENT, 0.1f);
            alarmBladeAnimate.setDuration(1500);
            alarmBladeAnimate.setRepeatCount(0);

            alarmBladeAnimate.setFillAfter(true);
            alarmBlade.setAnimation(alarmBladeAnimate);
            alarmBladeAnimate.start();
            relative.invalidate();

            mapBtn.setVisibility(View.INVISIBLE);
            stopwatchBtn.setVisibility(View.INVISIBLE);
            contactBtn.setVisibility(View.INVISIBLE);
            redialBtn.setVisibility(View.INVISIBLE);
            calendarBtn.setVisibility(View.INVISIBLE);
            alarmBtn.setVisibility(View.INVISIBLE);

            close.setVisibility(View.INVISIBLE);
            open.setVisibility(View.VISIBLE);
        }
    };


    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSION_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                    startActivity(intent);

                } else {


                }
                return;
            }

            case MY_PERMISSION_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = getCallIntent();
                    startActivity(intent);

                } else {


                }
                return;
            }
        }

    }
}
