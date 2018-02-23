package com.aiora.trendy;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * This file is part of the Universal template
 * For license information, please check the LICENSE
 * file in the root of this project
 *
 * @author Sherdle
 *         Copyright 2017
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //OneSignal Push
        if (!TextUtils.isEmpty(getString(R.string.onesignal_app_id)))
            OneSignal.init(this, getString(R.string.onesignal_google_project_number), getString(R.string.onesignal_app_id), new NotificationHandler());

    }

    // This fires when a notification is opened by tapping on it or one is received while the app is running.
    private class NotificationHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            try {
                //JSONObject data = result.notification.payload.additionalData;
                //String webViewUrl = (data != null) ? data.optString("url", null) : null;

                OSNotificationAction.ActionType actionType = result.action.type;
                String body = result.notification.payload.body;
                if (actionType == OSNotificationAction.ActionType.ActionTaken) {

                    switch (result.action.actionID) {
                        case "true":
                            generateNotification("Correct", body);

                            break;
                        case "false":
                            generateNotification("Wrong", body);
                            break;
                        case "close":
                            generateNotification("Pretty Close", body);
                            break;
                    }
                }

                /*String browserUrl = result.notification.payload.launchURL;
                if (webViewUrl != null || browserUrl != null) {
                    if (webViewUrl != null){
                        HolderActivity.startWebViewActivity(App.this, webViewUrl, false, false, null, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {
                        HolderActivity.startWebViewActivity(App.this, browserUrl, true, false, null, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                } else if (!result.notification.isAppInFocus) {
                    Intent mainIntent;
                    mainIntent = new Intent(App.this, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);
                }*/

            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

    public void generateNotification(String message, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.article)
                        .setContentTitle("Quiz of the Day Answer")
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentText(message);
        if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[0]);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(body);

        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert mNotificationManager != null;
        mNotificationManager.notify(5678, mBuilder.build());
    }

}