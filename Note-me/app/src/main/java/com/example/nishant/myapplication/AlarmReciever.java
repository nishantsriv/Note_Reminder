package com.example.nishant.myapplication;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;
import java.util.function.DoubleBinaryOperator;

/**
 * Created by nishant on 1/2/2017.
 */

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager manager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

       //ID
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Reminder")
                .setTicker("Reminder")
                .setContentText("Do your work")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0, mBuilder.build());

        wakeLock.release();
    }

    public void setAlarm(Context context, Calendar cal, int result) {


        //calculating time
        Calendar calendar = Calendar.getInstance();
        long currenttime = calendar.getTimeInMillis();
        long difftime = cal.getTimeInMillis() - currenttime;

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReciever.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, result, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + difftime, pi);
    }

    public void cancel(Context context, long result) {
        Intent intent = new Intent(context, AlarmReciever.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, (int) result, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
