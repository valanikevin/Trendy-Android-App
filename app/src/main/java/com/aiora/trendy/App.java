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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSNotificationPayload;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
                List<OSNotificationPayload.ActionButton> ans = result.notification.payload.actionButtons;

                String correctAnswer = "";
                for (int i = 0; i < ans.size(); i++) {
                    if (ans.get(i).id.equals("true")) {
                        correctAnswer = ans.get(i).text;
                    }
                }

                if (actionType == OSNotificationAction.ActionType.ActionTaken) {

                    String body = result.notification.payload.body;

                    switch (result.action.actionID) {
                        case "true":
                            generateNotification("Your Answer Is Correct", body, correctAnswer);
                            updateDatabase(correctAnswer, ans.get(0).text,
                                    ans.get(1).text, ans.get(2).text, body, result.action.actionID);
                            break;
                        case "false":
                            generateNotification("Better Luck Next Time", body, correctAnswer);
                            updateDatabase(correctAnswer, ans.get(0).text,
                                    ans.get(1).text, ans.get(2).text, body, result.action.actionID);
                            break;
                        case "close":
                            generateNotification("No, But Very Close", body, correctAnswer);
                            updateDatabase(correctAnswer, ans.get(0).text,
                                    ans.get(1).text, ans.get(2).text, body, result.action.actionID);
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

    public void generateNotification(String message, String body, String answer) {

        String title = "Quiz of the Day Result:";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationHelper notificationHelper = new NotificationHelper(this);
            Notification.Builder builder = notificationHelper.getNotification1(title, body, message, answer);
            if (builder != null) {
                notificationHelper.notify(1001, builder);
            }

        } else {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.article)
                            .setContentTitle(title)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setContentText(message);
            if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[100]);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(message + "\n\n" + body + "\n" + answer);

            mBuilder.setStyle(bigText);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            assert mNotificationManager != null;
            mNotificationManager.notify(5678, mBuilder.build());
        }
    }

    public void updateDatabase(String ans, String op1, String op2, String op3, String ques, String identity) {

        final String answer = ans;
        final String optionOne = op1;
        final String optionTwo = op2;
        final String optionThree = op3;
        final String question = ques;
        final String id = identity;

        final Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(date);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("quiz");
        //reference.getParent().child("test").setValue(Calendar.getInstance().getTimeInMillis());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(formattedDate).exists()) {
                    Integer t = dataSnapshot.child(formattedDate).child("result").child("true").getValue(Integer.class);
                    Integer f = dataSnapshot.child(formattedDate).child("result").child("false").getValue(Integer.class);
                    Integer c = dataSnapshot.child(formattedDate).child("result").child("close").getValue(Integer.class);
                    /*reference.child(formattedDate).child("answer").setValue(answer);
                    reference.child(formattedDate).child("option1").setValue(optionOne);
                    reference.child(formattedDate).child("option2").setValue(optionTwo);
                    reference.child(formattedDate).child("option3").setValue(optionThree);
                    reference.child(formattedDate).child("question").setValue(question);*/

                    Toast.makeText(App.this, "" + t + f + c, Toast.LENGTH_SHORT).show();

                    if (t != null || f != null || c != null)

                        switch (id) {
                            case "true":
                                reference.child(formattedDate).child("result").child("true").setValue(t + 1);
                                break;
                            case "false":
                                reference.child(formattedDate).child("result").child("false").setValue(f + 1);
                                break;
                            case "close":
                                reference.child(formattedDate).child("result").child("close").setValue(c + 1);
                                break;
                        }
                } else {
                    reference.child(formattedDate).child("answer").setValue(answer);
                    reference.child(formattedDate).child("option1").setValue(optionOne);
                    reference.child(formattedDate).child("option2").setValue(optionTwo);
                    reference.child(formattedDate).child("option3").setValue(optionThree);
                    reference.child(formattedDate).child("question").setValue(question);
                    switch (id) {
                        case "true":
                            reference.child(formattedDate).child("result").child("true").setValue(1);
                            reference.child(formattedDate).child("result").child("false").setValue(0);
                            reference.child(formattedDate).child("result").child("close").setValue(0);
                            break;
                        case "false":
                            reference.child(formattedDate).child("result").child("true").setValue(0);
                            reference.child(formattedDate).child("result").child("false").setValue(1);
                            reference.child(formattedDate).child("result").child("close").setValue(0);
                            break;
                        case "close":
                            reference.child(formattedDate).child("result").child("true").setValue(0);
                            reference.child(formattedDate).child("result").child("false").setValue(0);
                            reference.child(formattedDate).child("result").child("close").setValue(1);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}