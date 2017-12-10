package com.javabegin.chat.client;

import com.javabegin.chat.network.TCPConnection;
import com.javabegin.chat.network.TCPConnectionListener;

import java.util.Scanner;
import java.io.IOException;


public class ClientWindow implements TCPConnectionListener {

        private static final String IP_ADDR = "192.168.1.14";
        private static final int PORT = 8189;
        private TCPConnection connection;


    private ClientWindow() {
        Scanner nameInput = new Scanner(System.in);
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = nameInput.nextLine();



    }

    public void onConnectionReady(com.javabegin.chat.network.TCPConnection tcpConnection) {

    }

    public void onReceiveString(com.javabegin.chat.network.TCPConnection tcpConnection, String value) {

    }

    public void onDisconnect(com.javabegin.chat.network.TCPConnection tcpConnection) {

    }

    public void onExeption(com.javabegin.chat.network.TCPConnection tcpConnection, Exception e) {

    }

    private synchronized void printMag(String msg) {

    }
}
