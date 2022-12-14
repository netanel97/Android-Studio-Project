package com.example.hw1_netanelhabas;

import android.app.Application;

import com.example.hw1_netanelhabas.utils.MySPV;
import com.example.hw1_netanelhabas.utils.MySignal;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySignal.init(this);
        MySPV.init(this);
    }
}