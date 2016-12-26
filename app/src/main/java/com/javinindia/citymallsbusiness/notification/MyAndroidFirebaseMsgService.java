package com.javinindia.citymallsbusiness.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.activity.LoginActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData().toString());
            String profile = remoteMessage.getData().get("profile");
            String banner = remoteMessage.getData().get("banner");
            String message = remoteMessage.getData().get("message");
            String title = remoteMessage.getData().get("title");
            String num = remoteMessage.getData().get("number");
            String body = remoteMessage.getNotification().getBody();
            sendNotification(profile, banner, message, title, body, num);
        } else {
            remoteMessage.getNotification().getTitle();
            Log.e(TAG, "Message Notification AShish: " + remoteMessage.getNotification().getBody());
            createNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }


    private void sendNotification(String pro, String ban, String msg, String title, String body, String num) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("num", num);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        if (!TextUtils.isEmpty(pro)) {
            notificationBuilder.setLargeIcon(getBitmapfromUrl(pro));
        }
        if (!TextUtils.isEmpty(ban) && !TextUtils.isEmpty(msg)) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapfromUrl(ban)).setSummaryText(msg));
        } else if (!TextUtils.isEmpty(ban) && TextUtils.isEmpty(msg)) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapfromUrl(ban)));
        } else if (TextUtils.isEmpty(ban) && !TextUtils.isEmpty(msg)) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().setSummaryText(msg));
        }else {

        }
        if (!TextUtils.isEmpty(title)) {
            notificationBuilder.setContentTitle(title);
        } else {
            notificationBuilder.setContentTitle(body);
        }
        notificationBuilder.setSmallIcon(R.mipmap.logo);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void createNotification(String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(largeIcon);
        s.setSummaryText(messageBody);

        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotiSmallIcon())
                .setContentTitle("City Stores")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }

    public int getNotiSmallIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.notification_logo);
            return R.mipmap.logo;
        } else {
            // Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.notification_logo);
            return R.mipmap.logo;
        }
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}

