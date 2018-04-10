package com.example.gnu.vrexperiens;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Gnu on 2018-04-06.
 */

public class SensorActivity  extends AppCompatActivity implements SensorEventListener {

    private LowPassFilter lowPassFilter = new LowPassFilter();

    private Sensor mSensor;
    private Sensor mSensor2;
    private SensorManager mSensorManager;


    private float[] magSensorVals = new float[3];
    private float[] accSensorVals = new float[3];
    private int[] positionMoveTo;

    private float rightSound = 0;
    private float leaftSound = 0;

   private MediaPlayer mMediaPlayer = new MediaPlayer();



    private static final float ALPHAMag = 0.10f;
    private static final float ALPHAacc= 0.25f;

    private int moveHorizontal;
    private int moveVertical;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_testspace);



            mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,mSensor2,SensorManager.SENSOR_DELAY_FASTEST);

    }

    public void positionItSholdMOveTo(int[] position) {

        positionMoveTo = position;
        if (position != null && magSensorVals != null && accSensorVals != null) {

            if (position[0] < (int)accSensorVals[0]) {
                moveVertical =1;
            } else if (position[0] > (int)accSensorVals[0]) {
                moveVertical=-1;
            } else {
                moveVertical=0;
            }

            if (position[1] < (int)magSensorVals[1]) {
                moveHorizontal=1;

            } else if (position[1] < (int)magSensorVals[1]) {
               moveHorizontal=-1;
            } else {
                moveHorizontal=0;
            }

            moveRobot(moveVertical,moveHorizontal);

        }
    }

    private void moveRobot(int moveVertical,int moveHorizontal){

        if (moveVertical!=0){
            rightSound = 1;
            leaftSound = 0;
          //  myAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        }else{
            rightSound = 0;
            leaftSound = 0;

          //  myAudioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        }
        if (moveHorizontal!=0){
            rightSound = 0;
            leaftSound = 1;
          //  myAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        }else{
            rightSound = 0;
            leaftSound = 0;
          //  myAudioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        }
        mMediaPlayer.setVolume(rightSound,leaftSound);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magSensorVals = lowPassFilter.filterThis(event.values.clone(), magSensorVals, ALPHAMag);
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accSensorVals = lowPassFilter.filterThis(event.values.clone(), accSensorVals,ALPHAacc);
        }


        if (positionMoveTo[0] == (int)accSensorVals[0]){
           moveVertical=0;
           moveRobot(moveVertical,moveHorizontal);

        }

        if (positionMoveTo[1]== (int)magSensorVals[1]){
            moveHorizontal=0;
            moveRobot(moveVertical,moveHorizontal);

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

