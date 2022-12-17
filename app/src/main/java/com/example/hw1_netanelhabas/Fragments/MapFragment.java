package com.example.hw1_netanelhabas.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.hw1_netanelhabas.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment{
    private CallBack_Map callBack_map;
    private GoogleMap googleMap;
//    public void setCallBack_map(CallBack_Map callBack_map) {
//        this.callBack_map = callBack_map;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);
        SupportMapFragment supportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps));
        supportMapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
        });


        return view;
    }

    public void setMapLocation(double lat,double lon,String namePlayer){
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lon)).title(namePlayer));
    }


}