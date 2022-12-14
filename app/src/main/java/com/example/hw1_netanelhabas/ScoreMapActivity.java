package com.example.hw1_netanelhabas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.hw1_netanelhabas.utils.MySPV;
import com.google.gson.Gson;


public class ScoreMapActivity extends AppCompatActivity {
   private ListFragment listFragment;
   private Button exit;

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

        listFragment = new ListFragment();
        listFragment.setCallBack_list(callBack_list);

        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list,listFragment).commit();
    }



}