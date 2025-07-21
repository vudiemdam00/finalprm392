package com.example.finalprm392;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalprm392.Database.RoomDB;
import com.example.finalprm392.Models.Trip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class TripNotificationManager {

    private static final String CHANNEL_ID = "TripReminderChannel";
    private static final int NOTIFICATION_ID = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;

    public static void requestNotificationPermission(AppCompatActivity activity) {
        Log.d("TripNotification", "Checking notification permission...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, "android.permission.POST_NOTIFICATIONS")
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("TripNotification", "Requesting POST_NOTIFICATIONS permission");
                ActivityCompat.requestPermissions(activity,
                        new String[]{"android.permission.POST_NOTIFICATIONS"},
                        PERMISSION_REQUEST_CODE);
            } else {
                Log.d("TripNotification", "Permission already granted, scheduling notifications");
                scheduleAllTripNotifications(activity);
            }
        } else {
            Log.d("TripNotification", "No permission needed (pre-Android 13), scheduling notifications");
            scheduleAllTripNotifications(activity);
        }
    }

    public static void handlePermissionResult(AppCompatActivity activity, int requestCode,
                                              String[] permissions, int[] grantResults) {
        Log.d("TripNotification", "Handling permission result: requestCode=" + requestCode);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Notification permission granted", Toast.LENGTH_SHORT).show();
                scheduleAllTripNotifications(activity);
            } else {
                Toast.makeText(activity, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Trip Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifications for upcoming trips");
            NotificationManagerCompat.from(context).createNotificationChannel(channel);
            Log.d("TripNotification", "Notification channel created");
        }
    }

    public static void scheduleAllTripNotifications(Context context) {
        RoomDB database = RoomDB.getInstance(context);
        List<Trip> trips = database.tripDAO().getAllTrips();
        Log.d("TripNotification", "Scheduling notifications for " + trips.size() + " trips");
        for (Trip trip : trips) {
            scheduleTripNotification(context, trip);
        }
    }

    public static void scheduleTripNotification(Context context, Trip trip) {
        createNotificationChannel(context);

        LocalDate tripDate = trip.date;
        if (tripDate == null) {
            Log.d("TripNotification", "Trip " + trip.name + " has null date, skipping");
            return;
        }

        // For testing: Schedule notification 1 minute from now
        //LocalDateTime notificationTime = LocalDateTime.now().plusMinutes(1);
        // For production: Use 8:00 AM the day before
         LocalDateTime notificationTime = tripDate.minusDays(1).atTime(8, 0);

        long notificationTimeMillis = notificationTime.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        long currentTimeMillis = System.currentTimeMillis();

        Log.d("TripNotification", "Trip: " + trip.name + ", Notification time: " + notificationTime +
                ", Current time: " + LocalDateTime.now() + ", Millis: " + notificationTimeMillis);

        if (notificationTimeMillis <= currentTimeMillis) {
            Log.d("TripNotification", "Notification time is in the past for trip " + trip.name + ", skipping");
            return;
        }

        Intent intent = new Intent(context, TripNotificationReceiver.class);
        intent.putExtra("tripId", trip.id);
        intent.putExtra("tripName", trip.name);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                trip.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    notificationTimeMillis,
                    pendingIntent
            );
            Log.d("TripNotification", "Notification scheduled for trip " + trip.name);
        } else {
            Log.e("TripNotification", "AlarmManager is null, cannot schedule notification");
        }
    }

    public static class TripNotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int tripId = intent.getIntExtra("tripId", -1);
            String tripName = intent.getStringExtra("tripName");
            Log.d("TripNotification", "Notification triggered for tripId: " + tripId + ", name: " + tripName);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.app_icon_two)
                    .setContentTitle("Upcoming Trip: " + tripName)
                    .setContentText("Your trip is tomorrow! Don't forget to check your checklist.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                    ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS")
                            == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(tripId, builder.build());
                Log.d("TripNotification", "Notification displayed for trip: " + tripName);
            } else {
                Log.d("TripNotification", "Notification not displayed: Permission denied");
            }
        }
    }
}