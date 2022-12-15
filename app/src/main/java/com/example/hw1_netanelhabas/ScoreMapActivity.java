package com.example.hw1_netanelhabas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.hw1_netanelhabas.Fragments.ListFragment;
import com.example.hw1_netanelhabas.Fragments.MapFragment;
import com.example.hw1_netanelhabas.utils.MySPV;
import com.google.gson.Gson;


public class ScoreMapActivity extends AppCompatActivity {
   private ListFragment listFragment;
   private Button record_BTN_menu;
   private MapFragment mapFragment;
   private AppCompatImageView score_BackGround_Miner;
    CallBack_List callBack_list = new CallBack_List() {
       @Override
       public MyDB getRecords() {
           return new Gson().fromJson(MySPV.getInstance().getStrSP("records",""),MyDB.class);
       }
   };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initView();
        Glide.with(this).load(R.drawable.goldminebackground).into(score_BackGround_Miner);
        listFragment = new ListFragment();
        mapFragment = new MapFragment();
        listFragment.setCallBack_list(callBack_list);

        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list,listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_map,mapFragment).commit();
        record_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreMapActivity.this , MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        score_BackGround_Miner = findViewById(R.id.score_BackGround_Miner);
        record_BTN_menu = findViewById(R.id.record_BTN_menu);
    }


}