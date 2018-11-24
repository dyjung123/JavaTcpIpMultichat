package com.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

public class GameClient {
    public static void main(String[] args) {
        try {
            String serverIp = "192.168.0.8";

            Socket socket = new Socket(serverIp, 8080);

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);

        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
