package com.example.cabway.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.cabway.R;
import com.example.cabway.ui.activities.SplashScreenActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CabWayFCMInstanceService extends FirebaseMessagingService {
    public final String NOTIFICATION_INFO = "Cab way notification";
    public final int NOTIFICATION_ID = 999;

    public static void registerFcmTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> Log.d("FCM", "onComplete"));
    }

    public static void unregisterFcmTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String body = remoteMessage.getNotification().getBody();
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            /*  if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            } else {
                // Handle message within 10 seconds
            }*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        displayNotification(title, body);
    }

    private void displayNotification(String title, String body) {

        Intent notificationIntent = new Intent(this, SplashScreenActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "abc");
        builder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_cabway)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(intent)
                .setContentInfo(NOTIFICATION_INFO);

        Objects.requireNonNull(notificationManager).notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }
}
