package com.example.gnu.vrexperiens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gnu on 2018-04-06.
 */

public class TestSpaceActivity extends AppCompatActivity implements SensorEventListener{
    private TextView mXtext, mYtext, mZtext;
    private Sensor mSensor;
    private SensorManager mSensorManager;

    private int startPosition=0;

   private SensorActivity mSensorActivity = new SensorActivity();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testspace);


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_FASTEST);


        mXtext = (TextView)findViewById(R.id.Xtest);
        mYtext = (TextView)findViewById(R.id.Ytest);
        mZtext = (TextView)findViewById(R.id.Ztest);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mXtext.setText("X: "+ event.values[0]);
        mYtext.setText("Y: "+ event.values[1]);
        mZtext.setText("Z: "+ event.values[2]);

        if(mSensorActivity.canMove()) {
            toastMessage("Y value: " + mSensorActivity.setOldPosition((int) event.values[1]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    private void toastMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
