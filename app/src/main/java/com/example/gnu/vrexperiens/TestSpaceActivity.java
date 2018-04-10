package com.example.gnu.vrexperiens;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.widget.Toast;




/**
 * Created by Gnu on 2018-04-06.
 */

public class TestSpaceActivity extends AppCompatActivity implements SensorEventListener{
    private LowPassFilter lowPassFilter = new LowPassFilter();

    private SensorActivity mSensorActivity = new SensorActivity();

    private TextView mXtext, mYtext, mZtext,mXfiltext,mYfiltext,mZfiltext;
    private Sensor mSensor;
    private Sensor mSensor2;
    private SensorManager mSensorManager;


    private int[] movePosition = new int[3];
    private float[] magSensorVals = new float[3];
    private float[] accSensorVals = new float[3];
    private static final float ALPHAMag = 0.10f;
    private static final float ALPHAacc= 0.25f;

    private int[] movePositiondummy = new int[3];


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testspace);


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,mSensor2,SensorManager.SENSOR_DELAY_FASTEST);


        movePositiondummy[0]=2;
        movePositiondummy[1]=9;
        movePositiondummy[2]=0;



        mXtext = (TextView)findViewById(R.id.Xtest);
        mYtext = (TextView)findViewById(R.id.Ytest);
        mZtext = (TextView)findViewById(R.id.Ztest);
        mXfiltext = (TextView)findViewById(R.id.filterX);
        mYfiltext = (TextView)findViewById(R.id.YFiltertest);
        mZfiltext = (TextView)findViewById(R.id.ZFiltertest);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magSensorVals = lowPassFilter.filterThis(event.values.clone(), magSensorVals, ALPHAMag);
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accSensorVals = lowPassFilter.filterThis(event.values.clone(), accSensorVals,ALPHAacc);
        }

        movePosition[0] = (int)accSensorVals[0];
        movePosition[1] = (int)magSensorVals[1];
        movePosition[2] = (int)magSensorVals[2];

        mXtext.setText("X: " +(int) event.values[0]);
        mYtext.setText("Y: " +(int) event.values[1]);
        mZtext.setText("Z: " +(int) event.values[2]);

        mXfiltext.setText("X: " +(int) accSensorVals[0]);
        mYfiltext.setText("Y: " +(int) magSensorVals[1]);
        mZfiltext.setText("Z: " +(int) magSensorVals[2]);


       mSensorActivity.positionItSholdMOveTo(movePositiondummy);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    private void toastMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
