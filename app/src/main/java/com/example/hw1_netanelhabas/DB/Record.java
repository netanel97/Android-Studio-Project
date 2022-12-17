package com.example.hw1_netanelhabas.DB;

public class Record {
    private int score = 0;
    private String name ="";
    private double lat = 0.0;
    private double lon = 0.0;


    public Record(){

    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Record setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Record setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Player: ").append(name);
        sb.append("\nScore:").append(score).append(lon);
        return sb.toString();

    }
}
