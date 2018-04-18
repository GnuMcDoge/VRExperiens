package com.example.gnu.vrexperiens;

/**
 * Created by Gnu on 2018-04-17.
 */

public class PositionChanger {
    private static  PositionChanger positionChanger = new PositionChanger();
    private int[] posisiton = new int[3];
    private PositionChanger(){

    }

    public static PositionChanger getPositionChanger(){
        return positionChanger;
    }

    public void setPosition(int[] position){
        posisiton = position;
    }
    public int[] getPosisiton(){
        return posisiton;
    }
}
