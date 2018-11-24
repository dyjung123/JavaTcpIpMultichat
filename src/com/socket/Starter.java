package com.socket;

import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;


class EventFireGui extends JFrame {
//    static final int FRAME_WIDTH = 1024;
//    static final int FRAME_HEIGHT = 768;

    JLabel[] playerName = new JLabel[4];
    JTextField inputField = new JTextField(8);
    JTextArea display = new JTextArea();

    public EventFireGui() {
        super("Input word game");
//        setLayout(null);
        setBounds(100,100,1024,768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = this.getContentPane();
        JPanel pane = new JPanel();
        pane.setLayout(null);    //   컴포넌트들의 위치를 setBounds로 자유롭게 조정하기 위해 필요.
//        JButton button = new JButton("Test");

        /* Dimension은 width와 height, Point는 x와 y,  Rectangle은 x, y, width, height */
//        button.setMnemonic('S');
//        button.setBounds((this.getSize().width-this.getSize().width/8)/2,(this.getSize().height-this.getSize().height/8)/2,this.getSize().width/8,this.getSize().height/8);

        for (int i=0; i<playerName.length; ++i) {
            playerName[i] = new JLabel("player" + (i+1));
            playerName[i].setBounds(i*this.getWidth()/playerName.length + (this.getWidth()/playerName.length-100)/2,6*this.getHeight()/7,100,100);
        }
        inputField.setBounds(50,playerName[0].getY() - 50,150,30);
        display.setBounds((this.getWidth()-(int)(this.getWidth()*0.2))/2,(int)(this.getHeight()*0.07),(int)(this.getWidth()*0.2), (int)(this.getHeight()*0.2));
        display.setEnabled(false);

        for (int i=0; i<playerName.length; ++i) {
            pane.add(playerName[i]);
        }
        pane.add(display);
        pane.add(inputField);
        contentPane.add(pane);

        /* listener add */
        this.addComponentListener(EventHandling.getResizeListener(this));
        inputField.addKeyListener(EventHandling.getKeyhandleListener(inputField));
        /**/

        setVisible(true);
    }
}

public class Starter {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(8080);
            System.out.println(getTime() + "서버가 준비되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Socket socket = serverSocket.accept();
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new EventFireGui();
    }

    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}