package com.example.hw1_netanelhabas.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hw1_netanelhabas.CallBack_List;
import com.example.hw1_netanelhabas.MyDB;
import com.example.hw1_netanelhabas.R;
import com.example.hw1_netanelhabas.Record;


public class ListFragment extends Fragment {

    private ListView listView;
    private CallBack_List callBack_list;


    public void setCallBack_list(CallBack_List callBack_list){
        this.callBack_list = callBack_list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {
        MyDB allrecords = callBack_list.getRecords();
        if(allrecords != null){
            ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(getActivity(), android.R.layout.simple_expandable_list_item_1,allrecords.getResults());
            listView.setAdapter(adapter);

        }

    }

    private void findViews(View view) {
        listView = view.findViewById(R.id.fragmentList_tt);
    }


}
