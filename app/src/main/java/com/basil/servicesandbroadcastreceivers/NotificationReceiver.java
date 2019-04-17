package com.basil.servicesandbroadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context, "state = " + state +"\n number = " + number, Toast.LENGTH_SHORT).show();

        }else if (intent.getAction().equals("android.intent.action.BATTERY_LOW")) {
            NewMessageNotification.notify(context, "Battery is low", 5);
        }
    }
}
