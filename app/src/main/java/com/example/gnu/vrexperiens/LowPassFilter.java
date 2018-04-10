package com.example.gnu.vrexperiens;

/**
 * Created by Gnu on 2018-04-09.
 */

public class LowPassFilter {


        public float[] filterThis(float[]input, float[] output, float alpha){

        if (output==null) {
            return input;
        }
        else {
            for(int i=0; i<input.length; i++){

                output[i] =  output[i] = output[i] + alpha * (input[i] - output[i]);
            }
        }


        return output;
    }

}
