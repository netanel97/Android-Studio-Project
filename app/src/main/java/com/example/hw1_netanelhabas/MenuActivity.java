package com.example.hw1_netanelhabas;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MenuActivity extends AppCompatActivity {
    private SwitchMaterial menu_SW_sensor;
    private SwitchMaterial menu_SW_faster;
    private MaterialButton menu_BTN_playgame;
    private MaterialButton menu_BTN_top10;
    private AppCompatImageView road;
    private boolean isSensorOn = false;//play without/with sensor
    private boolean isFasterOn = false;//play faster mode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        Glide.with(this).load(R.drawable.goldminebackground).into(road);
        menu_SW_sensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean sensorOn) {
                isSensorOn = sensorOn;
            }
        });
        menu_SW_faster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean fasterModeOn) {
                isFasterOn = fasterModeOn;
            }
        });

        menu_BTN_playgame.setOnClickListener(V-> {//switching to ActivityGame page
            Intent intent = new Intent(this , MainActivity.class);
            intent.putExtra(MainActivity.KEY_SENSOR,isSensorOn);
            intent.putExtra(MainActivity.KEY_DELAY,isFasterOn);
            startActivity(intent);
            finish();
        });

    }

    private void findViews() {
        road = findViewById(R.id.road);
        menu_SW_sensor = findViewById(R.id.menu_SW_sensor);
        menu_SW_faster = findViewById(R.id.menu_SW_faster);
        menu_BTN_playgame = findViewById(R.id.menu_BTN_playgame);
        menu_BTN_top10 = findViewById(R.id.menu_BTN_top10);
    }

}