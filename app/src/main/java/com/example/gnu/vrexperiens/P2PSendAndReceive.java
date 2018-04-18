package com.example.gnu.vrexperiens;


import android.os.Handler;
import android.os.Message;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;



/**
 * Created by Gnu on 2018-04-16.
 */

public class P2PSendAndReceive extends Thread{

    private static P2PSendAndReceive p2PSendAndReceive = new P2PSendAndReceive();
    private static final int MESSAGE_READ = 1;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Handler handler;

    private P2PSendAndReceive(){

    }

    public static  P2PSendAndReceive getP2PSendAndReceive(){
        return p2PSendAndReceive;
    }

    public void setSoketAndHandler(Socket skt, Handler handler){
        socket = skt;
        this.handler = handler;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (socket!=null){

            try {
                bytes = inputStream.read(buffer);
                if(bytes > 0){
                    handler.obtainMessage(MESSAGE_READ,bytes,-1,buffer).sendToTarget();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void write(byte[] bytes){
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}




