package com.example.gnu.vrexperiens;


import android.media.MediaPlayer;
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

    private P2PSendAndReceive p2PSendAndReceive = P2PSendAndReceive.getP2PSendAndReceive();


    private TextView mXtext, mYtext, mZtext,mXfiltext,mYfiltext,mZfiltext;
    private Sensor mSensor;
    private Sensor mSensor2;
    private SensorManager mSensorManager;




    private int[] movePosition = new int[3];
    private int[] movePosition2 = new int[3];

    private float[] magSensorVals = new float[3];
    private float[] accSensorVals = new float[3];
    private String positionString="";
    private static final float ALPHAMag = 0.10f;
    private static final float ALPHAacc= 0.25f;

    private boolean moveDone=true;
    private boolean gnu=false;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testspace);
        movePosition[0] =0;
        movePosition[1] =0;
        movePosition[2] =0;
        movePosition2=movePosition;
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,mSensor2,SensorManager.SENSOR_DELAY_FASTEST);






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
            gnu=true;
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accSensorVals = lowPassFilter.filterThis(event.values.clone(), accSensorVals,ALPHAacc);
        }



        mXtext.setText("X: " +(int) event.values[0]);
        mYtext.setText("Y: " +(int) event.values[1]);
        mZtext.setText("Z: " +(int) event.values[2]);

        mXfiltext.setText("X: " +(int) accSensorVals[0]);
        mYfiltext.setText("Y: " +(int) magSensorVals[1]);
        mZfiltext.setText("Z: " +(int) magSensorVals[2]);


        if((movePosition[0]+3)<accSensorVals[0]||movePosition[0]-3>accSensorVals[0]){

            movePosition[0] = (int) accSensorVals[0];
            if (magSensorVals!=null) {
                movePosition[1] = (int) magSensorVals[1];
                movePosition[2] = (int) magSensorVals[2];
            }else
            {
                movePosition[1] = 0;
                movePosition[2] = 0;
            }
            sendMovePosition(movePosition);

        }




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    private void sendMovePosition(int[] position){

        if(moveDone) {
            moveDone=false;
            positionString = positionString + (Integer.toString(movePosition[0])) + ",";
            positionString = positionString + (Integer.toString(movePosition[1])) + ",";
            positionString = positionString + (Integer.toString(movePosition[2]));

            p2PSendAndReceive.write(positionString.getBytes());
            positionString = "";
            moveDone=true;
        }



    }



    private void toastMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
