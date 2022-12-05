package com.example.hw1_netanelhabas;

import android.util.Log;

import java.util.Random;

public class GameManager {
    private int wrong;
    private int life; //not sure about this
    public static final int ROCK_ROWS = 5;
    public static final int ROCKS_COL = 5;
    private final int TOP_RIGHT = ROCKS_COL - 1;
    Random rand = new Random();//rand for the rocks falling down
    private int currentIndexCar = ROCKS_COL/2;
    private int[][] main_type_matrix;

    public GameManager() {
    }

    public GameManager(int health) {
        this.life = health;
        initMatrixType();
    }

    private void initMatrixType() {
        this.main_type_matrix = new int[ROCK_ROWS][ROCKS_COL];
        for (int i = 0; i < ROCK_ROWS; i++) {
            for (int j = 0; j < ROCKS_COL; j++) {
                main_type_matrix[i][j] = -1;
            }
        }

    }

    public void setMainTypeMatrix(int row,int col, int type) {// put the type of the coin
        main_type_matrix[row][col] = type;
    }


    public int getMain_type_matrix(int row, int col) {
        return main_type_matrix[row][col];
    }

    public int getCurrentIndexCar() {
        return currentIndexCar;
    }

    public int moveIndexCar(int index) {
        if (index == 1 && getCurrentIndexCar() < TOP_RIGHT) //move right until index 2(top right)
            this.currentIndexCar += 1;

        else if (index == -1 && getCurrentIndexCar() > 0)
            this.currentIndexCar -= 1;

        return getCurrentIndexCar();
    }

    public int getWrong() {
        return wrong;
    }

    public void restartGame(int health) {
        this.wrong = 0;
        this.life = health;
    }

    public void addWrong() {
        this.wrong++;
    }

    public int randomViewImage() {
        return rand.nextInt(ROCKS_COL);//gives me a number for the first rock in col 0-ROCKS_COL
    }


    public int randTypeImage() {
        int res = rand.nextInt(5);
        if( res > 3){
            return 1;//return viewType gold
        }
        return 0;//return viewType rock

    }
}



