//컴파일 : javac com\socket\TestClient.java -encoding UTF-8
package com.socket;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("USAGE: java TestClient 대화명");
            System.exit(0);
        }

        try {
            String serverIp = "192.168.0.8";
            // 소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIp, 8080); // socket은 server socket
            System.out.println("서버에 연결되었습니다.");
            Thread sender = new Thread(new ClientSender(socket, args[0])); // 유저 이름을 서버로 전달.
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {}
    } // main

    static class ClientSender extends Thread {
        Socket socket;
        DataOutputStream out;
        String name;

        ClientSender(Socket socket, String name) {
            this.socket = socket; // server socket
            try {
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
            } catch (Exception e) {}
        }

        public void run() {
            Scanner scanner = new Scanner(System.in); // 입력 받기 위한 Scanner <- Swing JTextField에서 입력받게 수정
            try {
                if(out!=null) {       // 맨 처음에 플레이어 이름 전송
                    out.writeUTF(name);
                }

                while (out!=null) {
                    out.writeUTF("["+name+"]"+scanner.nextLine());
                }
            } catch (IOException e) {}
        } // run
    } // ClientSender

    static class ClientReceiver extends Thread {
        Socket socket;
        DataInputStream in;

        ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {}
        }

        public void run() {
            while (in!=null) {
                try {
                    System.out.println(in.readUTF());
                } catch (IOException e) {}
            }
        } // run
    } // ClientReceiver
} // class
