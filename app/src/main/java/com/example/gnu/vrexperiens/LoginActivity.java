package com.example.gnu.vrexperiens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Gnu on 2018-04-03.
 */

public class LoginActivity extends AppCompatActivity {

    private  Button mLoginButton;
    private EditText mPasswordText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mLoginButton = (Button) findViewById(R.id.Loginbutton);
        mPasswordText = (EditText) findViewById(R.id.PasswordeditText);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPasswordText.getText().toString().equals("halsingegord")){
                    Intent intent = new Intent(LoginActivity.this,MainMenyActivity.class);
                    startActivity(intent);
                    toastMessage("You did pass");
                }
                else {
                    toastMessage("you did not pass, you wrote: " + mPasswordText.getText());
                }
            }
        });

    }

    private void toastMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
