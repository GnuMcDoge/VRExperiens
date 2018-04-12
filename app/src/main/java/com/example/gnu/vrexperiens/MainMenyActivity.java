package com.example.gnu.vrexperiens;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Gnu on 2018-04-04.
 */

public class MainMenyActivity extends AppCompatActivity {

    private Button mConectButton;
    private Button mStreamButton;
    private Button mTestSpaceButton;

    private MediaPlayer mMediaPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmeny);


        mConectButton = (Button) findViewById(R.id.ConectButton);
        mStreamButton = (Button) findViewById(R.id.StreamButton);
        mTestSpaceButton = (Button) findViewById(R.id.TestSpaceButton);



        mMediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mMediaPlayer.setLooping(true);

        mConectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenyActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        mStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("Starting stream ");
            }
        });
        mTestSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

              //  mMediaPlayer.start();
                Intent intent = new Intent(MainMenyActivity.this, SensorActivity.class);
                startActivity(intent);

        }

        });

    }


    private void toastMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
