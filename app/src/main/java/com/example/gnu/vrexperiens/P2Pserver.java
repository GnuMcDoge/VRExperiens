package com.example.gnu.vrexperiens;


import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import static com.example.gnu.vrexperiens.MainActivity.MESSAGE_READ;

/**
 * Created by Gnu on 2018-04-16.
 */

public class P2Pserver extends Thread {
    P2PSendAndReceive sendReceive = P2PSendAndReceive.getP2PSendAndReceive();
    Socket socket;
    ServerSocket serverSocket;
   // Handler handler;

    public P2Pserver(){

    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff,0,msg.arg1);
                    break;
            }
            return true;
        }
    });

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            sendReceive.setSoketAndHandler(socket,handler);
            sendReceive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

