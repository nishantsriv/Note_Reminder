package com.example.nishant.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.Calendar;

public class Update extends AppCompatActivity {
    EditText titledetail, notedetail;
    ImageView img, add_reminder;
    Button attachbtn;
    DBAdaper dba = new DBAdaper(this);
    TextView remview;
    Button custdate, custtime, custsave, custcancel;
    Calendar cal;
    int yearr, monthh, day, hourr, min, id;
    static final int DIALOG_DATE = 1;
    static final int DIALOG_TIME = 2;
    byte[] picbyte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarupdate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//findView
        attachbtn = (Button) findViewById(R.id.attachbtn);
        remview= (TextView) findViewById(R.id.remviewdetail);
        titledetail = (EditText) findViewById(R.id.titledet);
        notedetail = (EditText) findViewById(R.id.notedet);
        img = (ImageView) findViewById(R.id.imgdet);
        add_reminder = (ImageView) findViewById(R.id.addreminder);
        //load data
        Intent intent = getIntent();
        final String title = intent.getExtras().getString("TITLE");
        final String note = intent.getExtras().getString("NOTE");
        picbyte = intent.getExtras().getByteArray("PICTURE");

        id = intent.getExtras().getInt("ID");
        final String remdetail = intent.getExtras().getString("REMDETAIL");
        remview.setText(remdetail);
        titledetail.setText(title);
        notedetail.setText(note);

        img.setImageBitmap(Utility.getPhoto(picbyte));
        add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

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
        }
    };




    private void update(int id, byte[] picbyte, String title, String note, String remdetails) {
        dba.open();
        long result1 = dba.update(id, picbyte, title, note, remdetails);
        if (result1 > 0) {
            Toast.makeText(this, "UPDATED", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show();
        }
        dba.close();
    }

    private void showDialog() {
        final Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        d.setTitle("ADD REMINDER");

        d.setContentView(R.layout.custom_layout);
        custdate = (Button) d.findViewById(R.id.custdate);
        custtime = (Button) d.findViewById(R.id.custtime);
        custsave = (Button) d.findViewById(R.id.custsavealarm);
        custcancel = (Button) d.findViewById(R.id.custcancelalarm);
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
                remview.setText(custtime.getText().toString());
                new AlarmReciever().setAlarm(Update.this, cal, id);
                d.dismiss();
            }
        });

        custcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlarmReciever().setAlarm(Update.this, cal, id);
                remview.setVisibility(View.GONE);
                d.dismiss();
            }
        });
        //
        d.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                update(id, picbyte, titledetail.getText().toString(), notedetail.getText().toString(), remview.getText().toString());
        }
        return super.onOptionsItemSelected(item);
    }
}
