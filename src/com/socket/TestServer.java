// 컴파일 : javac com\socket\TestServer.java -encoding UTF-8
package com.socket;

import java.net.*;
import java.io.*;
import java.util.*;

public class TestServer {
    HashMap clients;

    TestServer() {
        clients = new HashMap();
        /*
        * 왜 동기화를 하는가?
        * 동기화를 지원하는 Collection의 경우 순회하다가 새로운 값이 추가되는 경우 이슈가 발생하지 않는다.
        * 이는 멀티쓰레딩에서 유용한데, 이는 즉 OS에서 배웠던 Critical Section문제를 해결하기 좋기 때문이다. Critical Section Lock을 해주는것.
        * 쓰레드 동기화 : 한 쓰레드가 진행중인 작업을 다른 쓰레드가 간섭하지 못하도록 막는것.
        * 아래의 Collections.synchronizedMap()은 동기화 기능을 가진 Map을 만들어 데이터의 무결성을 보장해준다.
        * http://ooz.co.kr/71
        *  https://madplay.github.io/post/java-collection-synchronize
        * */
        Collections.synchronizedMap(clients);
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("서버시작");

            while (true) {
                socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속하였습니다.");
                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // start()

    void sendToALL(String msg) {
        Iterator it = clients.keySet().iterator();

        while (it.hasNext()) {
            try {
                DataOutputStream out = (DataOutputStream) clients.get(it.next());
                out.writeUTF(msg);
            } catch (IOException e) {}
        } // while
    } // sendToAll

    public static void main(String[] args) {
        new TestServer().start();
    }

    class ServerReceiver extends Thread {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {}
        }

        public void run() {
            String name = "";

            try {
                name = in.readUTF();
                sendToALL("#"+name+"님이 들어오셨습니다.");

                clients.put(name, out);
                System.out.println("현재 서버접속자 수는 "+clients.size()+"입니다.");

                while (in!=null) {
                    sendToALL(in.readUTF());
                }
            } catch (IOException e) {
                // ignore
            } finally {
                sendToALL("#"+name+"님이 나가셨습니다.");
                clients.remove(name);
                System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속을 종료하였습니다.");
                System.out.println("현재 서버접속자 수는 "+clients.size()+"입니다.");
            } // try
        } // run
    } // ReceiverThread
} // class
