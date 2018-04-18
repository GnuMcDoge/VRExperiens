package com.example.gnu.vrexperiens;


import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.net.Socket;
import java.util.ArrayList;

import static com.example.gnu.vrexperiens.MainActivity.MESSAGE_READ;


/**
 * Created by Gnu on 2018-04-16.
 */

public class P2PClient extends Thread{

    private SensorActivity sensorActivity = new SensorActivity();
   private PositionChanger positionChanger = PositionChanger.getPositionChanger();


    P2PSendAndReceive sendReceive= P2PSendAndReceive.getP2PSendAndReceive();
    Socket socket;
    String hostAdd;
    private int[] movePsodition = new int[3];
    private String[] stringPOssition = new String[3];


    public P2PClient(InetAddress hostAddress ){
        hostAdd = hostAddress.getHostAddress();
        socket = new Socket();
        this.handler = handler;
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff,0,msg.arg1);
                       sendMovePosition(tempMsg);

                    break;
            }
            return true;
        }
    });
    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(hostAdd,8080),500);
            sendReceive.setSoketAndHandler(socket,handler);
            sendReceive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMovePosition( String position){
        stringPOssition = position.split(",");
        for (int i = 0; i<3;i++){
            movePsodition[i]=Integer.parseInt(stringPOssition[i]);
        }
    positionChanger.setPosition(movePsodition);
    }
}



