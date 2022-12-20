package com.example.hw1_netanelhabas.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorDetector {
    private Context context;
    private SensorManager mSensorManager;
    private Sensor sensor;
    long timeStamp = 0;
    private final int ResponseTime = 300;
    public interface CallBack_MinerView {
        void moveMinerBySensor(int index);
        void changeSpeedBySensor(int speed);
    }
    private CallBack_MinerView callBack_minerView;
    public SensorDetector(Context context,CallBack_MinerView callBack_minerView) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_minerView = callBack_minerView;
    }



    /**
     * register to the sensors
     */
    public void startX() {
        mSensorManager.registerListener(sensorEventListenerX, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
//     public void startY() {
//            mSensorManager.registerListener(sensorEventListenerY, sensor, SensorManager.SENSOR_DELAY_NORMAL);
//        }

    /**
     * unregister to the sensors
     */
    public void stopX() {
        mSensorManager.unregisterListener(sensorEventListenerX);
    }
//    public void stopY() {
//        mSensorManager.unregisterListener(sensorEventListenerY);
//    }


//    private SensorEventListener sensorEventListenerY = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//            float y = event.values[1];
//                calculateStepY(y);
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int i) {
//
//        }
//    };

    private SensorEventListener sensorEventListenerX = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            calculateStepX(x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateStepX(float x) {

        if (x > 3.0) {//left
            if (System.currentTimeMillis() - timeStamp > ResponseTime) {
                timeStamp = System.currentTimeMillis();
                callBack_minerView.moveMinerBySensor(-1);



            }
        }
        if (x < -3.0) {//right
            if (System.currentTimeMillis() - timeStamp > ResponseTime) {
                timeStamp = System.currentTimeMillis();
                callBack_minerView.moveMinerBySensor(1);



            }
        }
    }

//    private void calculateStepY(float y){
//        if (y < 0.0) {
//            if (System.currentTimeMillis() - timeStamp > ResponseTime) {
//                timeStamp = System.currentTimeMillis();//SLOW
//                callBack_minerView.changeSpeedBySensor(600);
//            }
//            Log.e("pttt", "" + y);
//        }
//        if (y > 9.0) {
//            if (System.currentTimeMillis() - timeStamp > ResponseTime) {
//                timeStamp = System.currentTimeMillis();//FAST
//                callBack_minerView.changeSpeedBySensor(200);
//            }
//            Log.e("pttt", "" + y);
//        }
//    }
}
