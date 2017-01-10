package com.example.nishant.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class Add_activity extends AppCompatActivity implements View.OnClickListener {

    Button pick;
    ImageView img, reminderbtn;
    EditText titledit, notedit;
    private static final int SELECT_SINGLE_PICTURE = 101;
    public static final String IMAGE_TYPE = "image/*";
    Uri selectedImageUri;
    DBAdaper dba = new DBAdaper(this);
    static final int DIALOG_DATE = 1;
    static final int DIALOG_TIME = 2;
    int yearr, monthh, day, hourr, min;
   Button custdate, custtime, custsave, custcancel;
    Calendar cal;
    TextView remview;
    long result;
    BitmapDrawable drawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar_add = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        img = (ImageView) findViewById(R.id.imgadd);
        titledit = (EditText) findViewById(R.id.titleadd);
        notedit = (EditText) findViewById(R.id.noteadd);
        pick = (Button) findViewById(R.id.attachbtn);
        pick.setOnClickListener(this);
        remview = (TextView) findViewById(R.id.reminderdetails);
        drawable = (BitmapDrawable) img.getDrawable();
        reminderbtn = (ImageView) findViewById(R.id.reminderbtn);
        reminderbtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                save(titledit.getText().toString(), notedit.getText().toString(), drawable.getBitmap(), remview.getText().toString());
                finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void save(String s, String s1, Bitmap bitmap, String remdetail) {
        dba.open();
        result = dba.insert(s, s1, bitmap, remdetail);
        if (result > 0) {
            Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NOT SAVED", Toast.LENGTH_SHORT).show();
        }
        dba.close();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                selectedImageUri = data.getData();
                try {
                    img.setVisibility(View.VISIBLE);
                    img.setImageBitmap(new UserPicture(selectedImageUri, getContentResolver()).getBitmap());
                    drawable = (BitmapDrawable) img.getDrawable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // original code
//                String selectedImagePath = getPath(selectedImageUri);
//                selectedImagePreview.setImageURI(selectedImageUri);
            } else {
                // report failure
                Toast.makeText(getApplicationContext(), "msg_failed_to_get_intent_data", Toast.LENGTH_LONG).show();
                Log.d(MainActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
            }
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {

            return new DatePickerDialog(this, dpd, yearr, monthh, day);
        } else if (id == DIALOG_TIME) {
            return new TimePickerDialog(this, tpd, hourr, min, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yearr = year;
            monthh = month;
            day = dayOfMonth;


        }
    };

    private TimePickerDialog.OnTimeSetListener tpd = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourr = hourOfDay;
            min = minute;
            custtime.setText(hourr + ":" + min);
        }
    };


    private void showDialog() {
        final Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        d.setContentView(R.layout.custom_layout);
        d.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);


        custdate = (Button) d.findViewById(R.id.custdate);
        custtime = (Button) d.findViewById(R.id.custtime);
        custsave = (Button) d.findViewById(R.id.custsavealarm);
        custcancel = (Button) d.findViewById(R.id.custcancelalarm);
        remview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //set date
        custdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });

//set time

        custtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_TIME);
            }
        });

        //Calendar
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourr);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, monthh);
        cal.set(Calendar.YEAR, yearr);
        final String mdate = String.valueOf(day) + "/" + String.valueOf(monthh + 1) + "/" + String.valueOf(yearr);
        final String mtime = String.valueOf(hourr) + ":" + String.valueOf(min);
        custdate.setText(mdate);
        custtime.setText(mtime);
        custsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remview.setVisibility(View.VISIBLE);
                remview.setText(custtime.getText().toString());
                new AlarmReciever().setAlarm(Add_activity.this, cal, (int) result);
                d.dismiss();
            }
        });

        custcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlarmReciever().cancel(Add_activity.this, result);
                remview.setVisibility(View.GONE);
                d.dismiss();
            }
        });
        //
        d.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attachbtn:
                Intent intent = new Intent();
                intent.setType(IMAGE_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        getString(R.string.select_picture)), SELECT_SINGLE_PICTURE);
                break;
            case R.id.reminderbtn:
                showDialog();
                break;
        }


    }


}
