package com.example.gnu.vrexperiens;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Gnu on 2018-04-06.
 */

public class SensorActivity  extends AppCompatActivity implements SensorEventListener {

    private LowPassFilter lowPassFilter = new LowPassFilter();
    private ServoController mServoController = new ServoController();
    private PositionChanger positionChanger = PositionChanger.getPositionChanger();



    private Sensor mSensor;
    private Sensor mSensor2;
    private SensorManager mSensorManager;



    private float pulseVerticalWidth = 0;
    private float pulseHorizonWidth = 0;

    private boolean mServo0PwmEnabled;
    private boolean mServo1PwmEnabled;

    private float[] magSensorVals = new float[3];
    private float[] accSensorVals = new float[3];
    private int[] positionMoveTo = new int[3];


    private int[] movePositiondummy = new int[3];

    private static final float ALPHAMag = 0.10f;
    private static final float ALPHAacc= 0.25f;

    private TextView x,y,z;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sensor);

            x = findViewById(R.id.xtex);
            y = findViewById(R.id.ytex);
            z = findViewById(R.id.ztex);
            positionMoveTo[0]=0;
            positionMoveTo[1]=0;
            positionMoveTo[2]=0;


            mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,mSensor2,SensorManager.SENSOR_DELAY_FASTEST);


    }

    public void positionItSholdMOveTo(int[] position) {
        if (position != null) {
            positionMoveTo = position;

        }
    }

    private void moveRobot(){
        mServoController.setPulseWidth(mServo0PwmEnabled ? pulseHorizonWidth : 0f, 0);
        mServoController.setPulseWidth(mServo1PwmEnabled ? pulseVerticalWidth :0f, 1);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magSensorVals = lowPassFilter.filterThis(event.values.clone(), magSensorVals, ALPHAMag);
            pulseHorizonWidth = (10000 + (int)magSensorVals[1]) * 0.0000001f;

        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accSensorVals = lowPassFilter.filterThis(event.values.clone(), accSensorVals,ALPHAacc);
            pulseVerticalWidth= (10000 + (int)accSensorVals[0]) * 0.0000001f;

        }

        if((positionMoveTo[0]!=(int)accSensorVals[0])){
            mServo0PwmEnabled = true;
        }
        else{
            mServo0PwmEnabled=false;

        }
        if (positionMoveTo[1]!= (int)magSensorVals[1]){
            mServo1PwmEnabled = true;

        }
        else {
            mServo1PwmEnabled = false;


        }


        setPosisiton();
        moveRobot();



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void setPosisiton(){
        positionMoveTo = positionChanger.getPosisiton();

        x.setText("X: " + positionMoveTo[0]);
        y.setText("Y: " + positionMoveTo[1]);
        z.setText("Z: " + positionMoveTo[2]);

    }

    private void toastMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}

