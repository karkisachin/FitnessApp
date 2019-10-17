package www.sumanmyon.com.fitnessapp.WaterNotification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import www.sumanmyon.com.fitnessapp.MainActivity;

import static android.content.Context.ALARM_SERVICE;

public class AlarmBrodcastReceiver extends BroadcastReceiver {
    Context context;


    @Override
    public void onReceive(Context context, Intent arg1) {
        this.context = context;
       // showNotification(context);
        simpleNotification();
    }

    private void showNotification(Context context) {

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(0)
                .setContentTitle("Fitness")
                .setContentText("Its time to drink water");
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }

    private void simpleNotification() {
        for(int i=0; i<3; i++) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(www.sumanmyon.com.fitnessapp.R.drawable.calorie, 2);
            builder.setContentTitle("Fitness");
            builder.setContentText("Please drink water");
            builder.setPriority(Notification.PRIORITY_MAX);
            builder.setAutoCancel(false);
//            builder.setContentInfo("times "+i+1);  //TODO right hand side ma text lekhxa
            builder.setDefaults(3);     //TODO provide nothing=0, sound=1, vibration=2, sound & vibration=3 4 bata auta repeate hunxa
            //builder.setNumber(i+1);   //right hand side ma number auxa
            builder.setOngoing(false);
            builder.setTicker("Please drink water");  //TODO chainxa//Set the text that is displayed in the status bar when the notification first arrives.
//            builder.setWhen(Calendar.HOUR);   //TODO Set the time that the event occurred. Notifications in the panel are sorted by this time.
//            builder.setWhen(Calendar.MINUTE);
//            builder.setWhen(Calendar.SECOND);
            builder.setContentIntent(p);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = 1;
            manager.notify(notificationId, builder.build());
        }
    }


}