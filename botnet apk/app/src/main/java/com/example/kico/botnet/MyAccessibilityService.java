package com.example.kico.botnet;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    Service mService = null;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {

    }


    @Override
    protected void onServiceConnected() {
    }

    @Override
    public void onCreate() {
        this.mService = this;
    }
}