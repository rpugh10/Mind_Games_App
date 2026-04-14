package com.example.mind_games_app;

public class Card {
    int value;
    boolean faceUp;
    boolean isMatched;

    public Card(int value) {
        this.value = value;
        this.isMatched = false;
        this.faceUp = false;
    }
}
