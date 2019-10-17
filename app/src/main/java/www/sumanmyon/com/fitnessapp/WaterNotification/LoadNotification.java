package www.sumanmyon.com.fitnessapp.WaterNotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class LoadNotification {
    Activity activity;

    AlarmManager manager;
    PendingIntent pendingIntent;

    int interval = 3 * 60 * 60 * 1000;
    boolean isStarted = false;

    public LoadNotification(Activity activity) {
        this.activity = activity;
    }

    public void start(){
        isStarted = true;
        //for 1st notification
        String uniqueString = "com.androidbook.intents.testbc";
        Intent ii = new Intent(uniqueString);
        activity.sendBroadcast(ii);

        //for other notification
        Intent alarmIntent = new Intent(activity, AlarmBrodcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0);;

        manager = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    public void stop(){
        if(isStarted){
            manager.cancel(pendingIntent);
        }
    }
}
