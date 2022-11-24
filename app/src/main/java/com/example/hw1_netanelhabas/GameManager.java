package com.example.hw1_netanelhabas;

import java.util.Random;

public class GameManager {
    private int wrong;
    private int life; //not sure about this
    public static final int ROCK_ROWS = 4;
    public static final int ROCKS_COL = 3;
    public final int TOP_RIGHT = ROCKS_COL - 1;
    Random rand = new Random();//rand for the rocks falling down
    private int currentIndexCar = 1;
    public GameManager() {
    }
    public GameManager(int health){
        this.life = health;
    }

    public int getCurrentIndexCar() {
        return currentIndexCar;
    }

    public int moveIndexCar(int index) {
        if (index == 1 && getCurrentIndexCar() < TOP_RIGHT) {//move right until index 2(top right)
            this.currentIndexCar+=1;
            return getCurrentIndexCar();
        }
        else if(index == -1 && getCurrentIndexCar() > 0){
            this.currentIndexCar-=1;
            return getCurrentIndexCar();
        }
        return getCurrentIndexCar();
        }

    public int getWrong() {
        return wrong;
    }

    public void restartGame(int health){this.wrong = 0;
    this.life = health;}
    public void addWrong() {
            this.wrong++;
        }

    public int randomRockInCol(){
        int random = rand.nextInt(ROCKS_COL);//gives me a number for the first rock in col 0-ROCKS_COL
        return random;
    }

    }



