package com.example.gnu.vrexperiens;

import android.app.Activity;
import android.content.Context;



/**
 * Created by Gnu on 2018-04-06.
 */

public class SensorActivity {


    private int oldPosition;
    private int oldRpostion;
    private int oldLpostion;
    private int i = 0;
    private boolean firstTime = true;
    private boolean moving = true;
    private boolean canmove= true;


    public SensorActivity() {

    }

    public int setOldPosition(int newPosition) {
        if(firstTime){
            oldPosition = newPosition;
            firstTime = false;
        }
        return headMovement(newPosition);
    }

    public boolean canMove(){
        return canmove;
    }
    private int headMovement(int zPostion) {
        canmove = false;
        while (moving==true) {
            if (zPostion==oldPosition){
                moving= false;
            }
            else if (oldPosition > zPostion) {
                i++;
                zPostion++;


            } else {
                i--;
               zPostion--;
            }

        }
        canmove = true;
        moving = true;
        oldPosition = zPostion;
        return i;
    }
}

