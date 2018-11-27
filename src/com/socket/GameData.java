package com.socket;

import java.io.Serializable;

public class GameData implements Serializable {

    //날짜
    private String inputWord; // 입력 단어
    private int score; // 게임 점수

    public GameData() {
        this.score = 0;
    }

    public void setInputWord(String inputWord) {
        this.inputWord = inputWord;
    }

    public String getInputWord() {
        return this.inputWord;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
}
