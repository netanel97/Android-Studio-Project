package com.example.hw1_netanelhabas;

import static com.example.hw1_netanelhabas.GameManager.ROCKS_COL;
import static com.example.hw1_netanelhabas.GameManager.ROCK_ROWS;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[][] game_IMG_rocksMatrix;
    private ExtendedFloatingActionButton[] game_BTN_arrows;
    private ShapeableImageView[] game_IMG_motorbikes;
    private MaterialTextView main_TXT_addScore;
    GameManager gameManager;
    private Timer timer;
    private int time = 0;
    private final int DELAY = 600;
    private AppCompatImageView road_IMG_background;
    String[] typeImage= new String[]{"ic_rock","gold"};
    CoinSound coinSound;
    RockSound rockSound;
    SensorDetector sensorDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Glide.with(MainActivity.this).load(R.drawable.goldminebackground).into(road_IMG_background);
        gameManager = new GameManager(game_IMG_hearts.length,this);
        sensorDetector = new SensorDetector(this,gameManager,callBack_steps);
        initViews();
        setButton();
        startGame();


        private SensorDetector.CallBack_MinerView callBack_steps = new SensorDetector.CallBack_MinerView() {
            @Override
            public void hideMinerView(){

            }
        };

        game_BTN_arrows[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clicked(-1);}
        });
        game_BTN_arrows[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clicked(1);}
        });
    }

    private void setButton() {

        game_BTN_arrows[0].hide();
        game_BTN_arrows[1].hide();
        sensorDetector.start();

    }


    private void findViews() {
        road_IMG_background = findViewById(R.id.road_IMG_background);
        main_TXT_addScore = findViewById(R.id.main_TXT_addScore);
        findHearts();
        findRocks();
        findMotorbikes();
        findArrows();
        game_BTN_arrows[0].hide();
        game_BTN_arrows[1].hide();
    }


    private void initViews() {
        setHeartVisible();
        setRocksInvisible();
        setMotorbikesVisible();

    }
    private void startGame() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    play();
                    if(time % 2 != 0) {//call every two sec and put it in the matrix
                        randImage();
                    }
                    time++;

                });
            }
        },0,DELAY);
    }

    private void setMotorbikesVisible() {
        for (int i = 0; i < game_IMG_motorbikes.length; i++) {
            if(i != gameManager.getCurrentIndexCar())
                game_IMG_motorbikes[i].setVisibility(View.INVISIBLE);//all the bikes except the middle
        }
        game_IMG_motorbikes[ gameManager.getCurrentIndexCar()].setVisibility(View.VISIBLE);//middle bike visible
    }

    private void setRocksInvisible() {
        for (int i = 0; i < ROCK_ROWS; i++) {
            for (int j = 0; j < ROCKS_COL; j++) {
                game_IMG_rocksMatrix[i][j].setVisibility(View.INVISIBLE);//hide the rocks
            }
        }
    }


    private void setHeartVisible() {
       // for (int i = 0; i < gameManager.getCurrentIndexCar(); i++) {
        for (int i = 0; i < game_IMG_hearts.length; i++) {

            game_IMG_hearts[i].setVisibility(View.VISIBLE);

        }

    }


    private void findHearts() {
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1)};
    }

    private void findRocks() {
        game_IMG_rocksMatrix = new ShapeableImageView[][]{
                {findViewById(R.id.main_IMG_rock1),findViewById(R.id.main_IMG_rock2),findViewById(R.id.main_IMG_rock3),findViewById(R.id.main_IMG_rock4),findViewById(R.id.main_IMG_rock5)},
                {findViewById(R.id.main_IMG_rock6),findViewById(R.id.main_IMG_rock7),findViewById(R.id.main_IMG_rock8),findViewById(R.id.main_IMG_rock9),findViewById(R.id.main_IMG_rock10)},
                {findViewById(R.id.main_IMG_rock11),findViewById(R.id.main_IMG_rock12),findViewById(R.id.main_IMG_rock13),findViewById(R.id.main_IMG_rock14),findViewById(R.id.main_IMG_rock15)},
                {findViewById(R.id.main_IMG_rock16),findViewById(R.id.main_IMG_rock17),findViewById(R.id.main_IMG_rock18),findViewById(R.id.main_IMG_rock19),findViewById(R.id.main_IMG_rock20)}
        ,{findViewById(R.id.main_IMG_rock21),findViewById(R.id.main_IMG_rock22),findViewById(R.id.main_IMG_rock23),findViewById(R.id.main_IMG_rock24),findViewById(R.id.main_IMG_rock25)}};

    }

    private void findMotorbikes() {

        game_IMG_motorbikes = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_leftMiner),
                findViewById(R.id.main_IMG_leftMidMiner),
                findViewById(R.id.main_IMG_middleMiner),
                findViewById(R.id.main_IMG_rightMidMiner),
                findViewById(R.id.main_IMG_rightMiner)};
    }


    private void findArrows() {
        game_BTN_arrows = new ExtendedFloatingActionButton[]{
                findViewById(R.id.main_BTN_left),
                findViewById(R.id.main_BTN_right)
        };
    }

    private void play() {
        checkCollsion();
        updateMatRock();

    }

    private void randImage() {
        int col = gameManager.randomViewImage();//0
        int type = gameManager.randTypeImage();//1
        setImage(0,col,type);
        game_IMG_rocksMatrix[0][col].setVisibility(View.VISIBLE);
    }

    private void setImage(int row,int col,int type){
        int imageID = getResources().getIdentifier(typeImage[type], "drawable", getPackageName());
        gameManager.setMainTypeMatrix(row,col,type);// 0 == rock 1 == coins
        game_IMG_rocksMatrix[row][col].setImageResource(imageID);

    }


    private void updateMatRock() {
        for (int i = ROCK_ROWS - 1; i >= 0; i--) {
            for (int j = ROCKS_COL - 1; j >= 0; j--) {
                if(game_IMG_rocksMatrix[i][j].getVisibility() == View.VISIBLE && i == ROCK_ROWS-1) {//last row-->Invisible
                    game_IMG_rocksMatrix[i][j].setVisibility(View.INVISIBLE);

                }

                if(game_IMG_rocksMatrix[i][j].getVisibility() == View.VISIBLE){
                    game_IMG_rocksMatrix[i][j].setVisibility(View.INVISIBLE);
                    int type = gameManager.getMain_type_matrix(i,j);//get the type,if type = 0-> rock 1->coin
                    gameManager.setMainTypeMatrix(i,j,-1);
                    setImage(i+1,j,type);
                    gameManager.setMainTypeMatrix(i+1,j,type);// 0 == rock 1 == coins
                    game_IMG_rocksMatrix[i+1][j].setVisibility(View.VISIBLE);
                }
            }

            }


    }


    private void checkCollsion() {

        for (int i = ROCK_ROWS - 1; i > 0; i--) {
            for (int j = ROCKS_COL - 1; j >= 0; j--) {//need to check if its >=
                if (i == ROCK_ROWS - 1 && game_IMG_rocksMatrix[i][j].getVisibility() == View.VISIBLE && j == gameManager.getCurrentIndexCar()) {
                    //checking last row
                    checkType(i,j,gameManager.getMain_type_matrix(i,j));//check the type

                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //coinSound = new CoinSound(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        coinSound.cancel(true);
    }



    private void checkType(int row,int col,int type) {

        if(type == 0) {//0 -->Rock
            removeHealth();
            rockSound = new RockSound(this);
            rockSound.execute();

        }
        else{//Coin
            gameManager.addScore();
            main_TXT_addScore.setText(""+gameManager.getScore());
            coinSound = new CoinSound(this);
            coinSound.execute();
        }
        vibrate();
    }

    private void removeHealth() {
        gameManager.addWrong();
        if(gameManager.getWrong() == game_IMG_hearts.length) {
            gameOver();
            toast("Game Over restart the game.");
            vibrate();
            return;
        }

        vibrate();
        for (int i = 0; i < gameManager.getWrong(); i++) {
            game_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
        toast("you have " + (game_IMG_hearts.length-gameManager.getWrong()) + " lives");
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(400);
        }


    }

    private void gameOver() {
        setHeartVisible();
        gameManager.restartGame(game_IMG_hearts.length);
//        timer.cancel();
//        finish();


    }

    private void toast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


    private void clicked(int index) {//moving motorbike left/right
        int currentIndex = gameManager.getCurrentIndexCar();
        int moveLeftRight = gameManager.moveIndexCar(index);
        game_IMG_motorbikes[currentIndex].setVisibility(View.INVISIBLE);
        game_IMG_motorbikes[moveLeftRight].setVisibility(View.VISIBLE);
    }


}


