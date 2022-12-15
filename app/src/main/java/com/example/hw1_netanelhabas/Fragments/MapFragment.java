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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.example.hw1_netanelhabas.R;


public class MapFragment extends Fragment implements OnMapReadyCallback{
     GoogleMap googleMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps));
        mapFragment.getMapAsync(this);
        return view;
}

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
     //   this.googleMap = googleMap;


    }
}