package com.example.hw1_netanelhabas;

import java.util.ArrayList;

public class MyDB {//list of result
    private ArrayList<Record> results;

    public MyDB(){
        this.results = new ArrayList<>();
    }

    public ArrayList<Record> getResults() {

        results.sort((r1, r2) -> r2.getScore() - r1.getScore());
        return results;
    }

    public MyDB setResults(ArrayList<Record> results) {
        this.results = results;
        return this;
    }
}
