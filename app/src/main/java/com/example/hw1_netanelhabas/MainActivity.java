package com.example.hw1_netanelhabas;

import static com.example.hw1_netanelhabas.GameManager.ROCKS_COL;
import static com.example.hw1_netanelhabas.GameManager.ROCK_ROWS;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.hw1_netanelhabas.utils.MySignal;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[][] game_IMG_rocksMatrix;
    private ExtendedFloatingActionButton[] game_BTN_arrows;
    private ShapeableImageView[] game_IMG_miner;
    private MaterialTextView main_TXT_addScore;
    GameManager gameManager;
    private Timer timer;
    private int time = 0;
    private int delay = 700;
    private  AppCompatImageView road_IMG_background;
    String[] typeImage= new String[]{"ic_rock","gold"};
    CoinSound coinSound;
    RockSound rockSound;
    SensorDetector sensorDetector;
    public static final String KEY_SENSOR = "KEY_SENSOR";
    public static final String KEY_DELAY = "KEY_DELAY";
    public static final String KEY_NAME = "KEY_NAME";
    public boolean isSensorOn = false;
    private String name = "";
    private MyDB myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Glide.with(this).load(R.drawable.goldminebackground).into(road_IMG_background);

        /*
        get previous information to delay and playing with sensor(yes/no)
         */
        Intent previousIntent  = getIntent();
        boolean isFasterMode = previousIntent.getExtras().getBoolean(KEY_DELAY);
        isSensorOn= previousIntent.getExtras().getBoolean(KEY_SENSOR);
        name = previousIntent.getExtras().getString(KEY_NAME);
        gameManager = new GameManager(game_IMG_hearts.length,this,name);
        sensorDetector = new SensorDetector(this,callBack_steps);
        initViews();
        setButton();
        setDelay(isFasterMode);

        game_BTN_arrows[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clicked(-1);}
        });
        game_BTN_arrows[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clicked(1);}
        });
    }

    private void setDelay(boolean isFasterMode) {
        if(isFasterMode)
            delay = 350;
        else
            delay = 600;

    }

    private SensorDetector.CallBack_MinerView callBack_steps = new SensorDetector.CallBack_MinerView() {
        @Override
        public void moveMinerBySensor(int index){
            game_IMG_miner[gameManager.getCurrentIndexCar()].setVisibility(View.INVISIBLE);
            gameManager.moveIndexCar(index);
            game_IMG_miner[gameManager.getCurrentIndexCar()].setVisibility(View.VISIBLE);
        }
    };

    private void setButton() {
        if(isSensorOn) {
            game_BTN_arrows[0].hide();
            game_BTN_arrows[1].hide();
            sensorDetector.start();
        }
        else{
            game_BTN_arrows[0].show();
            game_BTN_arrows[1].show();
        }
    }


    private void findViews() {
        road_IMG_background = findViewById(R.id.road_IMG_background);
        main_TXT_addScore = findViewById(R.id.main_TXT_addScore);
        findHearts();
        findRocks();
        findMotorbikes();
        findArrows();
    }


    private void initViews() {
        setHeartVisible();
        setRocksInvisible();
        setMinerVisible();
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
        },0,delay);
    }

    private void setMinerVisible() {
        for (int i = 0; i < game_IMG_miner.length; i++) {
            if(i != gameManager.getCurrentIndexCar())
                game_IMG_miner[i].setVisibility(View.INVISIBLE);//all the bikes except the middle
        }
        game_IMG_miner[ gameManager.getCurrentIndexCar()].setVisibility(View.VISIBLE);//middle bike visible
    }

    private void setRocksInvisible() {
        for (int i = 0; i < ROCK_ROWS; i++) {
            for (int j = 0; j < ROCKS_COL; j++) {
                game_IMG_rocksMatrix[i][j].setVisibility(View.INVISIBLE);//hide the rocks
            }
        }
    }


    private void setHeartVisible() {
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

        game_IMG_miner = new ShapeableImageView[]{
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
                    checkType(gameManager.getMain_type_matrix(i,j));//check the type

                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //coinSound = new CoinSound(this);
        if(isSensorOn)
            sensorDetector.start();

        startGame();
    }



    @Override
    protected void onPause() {
        super.onPause();
//        coinSound.cancel(true);
//        if(isSensorOn)
//            sensorDetector.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
//        coinSound.cancel(true);
        if(isSensorOn)
            sensorDetector.stop();
    }


    private void checkType(int type) {

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
        MySignal.getInstance().vibrate();
    }

    private void removeHealth() {
        gameManager.addWrong();
        if(gameManager.getWrong() == game_IMG_hearts.length) {
            gameOver();
        }

        MySignal.getInstance().vibrate();
        for (int i = 0; i < gameManager.getWrong(); i++) {
            game_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
        MySignal.getInstance().toast("you have " + (game_IMG_hearts.length-gameManager.getWrong()) + " lives");

    }



    private void gameOver() {
        saveRecord();
        changeActivity();
//        setHeartVisible();
//        gameManager.restartGame(game_IMG_hearts.length);
//        timer.cancel();
//        finish();


    }

    private void changeActivity() {

        Intent intent = new Intent(MainActivity.this,ScoreMapActivity.class);
        startActivity(intent);
        finish();
    }
    private void saveRecord() {
        gameManager.save();
    }


    private void clicked(int index) {//moving motorbike left/right
        int currentIndex = gameManager.getCurrentIndexCar();
        int moveLeftRight = gameManager.moveIndexCar(index);
        game_IMG_miner[currentIndex].setVisibility(View.INVISIBLE);
        game_IMG_miner[moveLeftRight].setVisibility(View.VISIBLE);
    }


}


