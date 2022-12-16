package com.example.hw1_netanelhabas;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.example.hw1_netanelhabas.utils.MySignal;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import im.delight.android.location.SimpleLocation;

public class MenuActivity extends AppCompatActivity {
    private SwitchMaterial menu_SW_sensor;
    private SwitchMaterial menu_SW_faster;
    private MaterialButton menu_BTN_playgame;
    private MaterialButton menu_BTN_top10;
    private AppCompatImageView road;
    private boolean isSensorOn = false;//play without/with sensor
    private boolean isFasterOn = false;//play faster mode
    private AppCompatEditText menu_TXT_name;
    private double lat = 0.0;
    private double lon = 0.0;
    public static SimpleLocation location;//todo static class?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        location = new SimpleLocation(this);
        findViews();
        Glide.with(this).load(R.drawable.goldminebackground).into(road);

        requestLocationPermission(location);


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

        menu_BTN_playgame.setOnClickListener(V -> {//switching to ActivityGame page
            if (menu_TXT_name.getText().length() != 0) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(MainActivity.KEY_SENSOR, isSensorOn);
                intent.putExtra(MainActivity.KEY_NAME, menu_TXT_name.getText().toString());
                intent.putExtra(MainActivity.KEY_DELAY, isFasterOn);
                intent.putExtra(MainActivity.KEY_LON,lon);
                intent.putExtra(MainActivity.KEY_LAT,lat);
                startActivity(intent);
                finish();
            } else {
                MySignal.getInstance().toast("You Must Fill Name!!");
            }
        });

        menu_BTN_top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, ScoreMapActivity.class);
                startActivity(intent);
            }
        });

    }

    private void requestLocationPermission(SimpleLocation location) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);

        }
        else{//if he doesn't agree he will put 0.0 in lon,lat
            location.beginUpdates();
            this.lat = location.getLatitude();
            this.lon = location.getLongitude();
            System.out.println("lat + lon " + lat + " " + lon);
        }




    }



    private void findViews() {
        road = findViewById(R.id.road);
        menu_TXT_name = findViewById(R.id.menu_TXT_name);
        menu_SW_sensor = findViewById(R.id.menu_SW_sensor);
        menu_SW_faster = findViewById(R.id.menu_SW_faster);
        menu_BTN_playgame = findViewById(R.id.menu_BTN_playgame);
        menu_BTN_top10 = findViewById(R.id.menu_BTN_top10);
    }

}