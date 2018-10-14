package com.example.kico.botnet;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class StartActivityOnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, MyService.class);
            context.startService(serviceIntent);
        }
    }

}