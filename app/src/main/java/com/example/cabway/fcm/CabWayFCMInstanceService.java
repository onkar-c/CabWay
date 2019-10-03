package com.example.cabway.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.cabway.R;
import com.example.cabway.Utils.AppUtils;
import com.example.cabway.ui.activities.DashBoardActivity;
import com.example.cabway.ui.activities.SplashScreenActivity;
import com.example.database.Utills.AppConstants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class CabWayFCMInstanceService extends FirebaseMessagingService {
    public static final String TAG = "FCM";
    public final String NOTIFICATION_INFO = "Cab way notification";

    public static void registerFcmTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> Log.d(TAG, "onComplete"));
    }

    public static void unregisterFcmTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

    public PendingIntent createPendingIntent() {
        Intent intent = new Intent(this, NotificationIntentSenderBroadcast.class);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    public NotificationCompat.Builder createNotificationBuilder(RemoteMessage remoteMessage) {
        return new NotificationCompat.Builder(this, AppConstants.CHANNEL_ID)
                .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Objects.requireNonNull(remoteMessage.getNotification()).getBody()))
                .setAutoCancel(true)
                .setContentIntent(createPendingIntent())
                .setContentInfo(NOTIFICATION_INFO);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: " + Objects.requireNonNull(remoteMessage.getNotification()).getBody());
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().get("message"));

        NotificationCompat.Builder builder = createNotificationBuilder(remoteMessage);
        builder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_white_notification);
            builder.setLights(Color.YELLOW, Color.YELLOW, Color.YELLOW);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher_cabway);
        }

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(AppConstants.CHANNEL_ID, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }
        Objects.requireNonNull(manager).notify(9999, builder.build());
    }


    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

    public static class NotificationIntentSenderBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent nextIntent;
            if (AppUtils.isAppIsInBackground(context)) {
                nextIntent = new Intent(context, SplashScreenActivity.class);
            } else {
                nextIntent = new Intent(context, DashBoardActivity.class);
            }
            nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(nextIntent);
        }
    }
}
