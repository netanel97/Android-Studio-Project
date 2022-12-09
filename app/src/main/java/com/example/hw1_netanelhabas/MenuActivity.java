package com.example.hw1_netanelhabas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MenuActivity extends AppCompatActivity {
    private SwitchMaterial menu_SW_sensor;
    private SwitchMaterial menu_SW_sound;
    private MaterialButton menu_BTN_playgame;
    private MaterialButton menu_BTN_top10;
    private boolean isSensorOn = false;//play without/with sensor
    private int DELAY = 700;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}