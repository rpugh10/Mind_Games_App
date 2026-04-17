package com.example.mind_games_app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WordData {

    public static class WordItem {
        public String word;
        public String category;

        public WordItem(String word, String category) {
            this.word = word;
            this.category = category;
        }
    }

    private static final List<WordItem> ALL_WORDS = new ArrayList<>(Arrays.asList(

            new WordItem("ELEPHANT", "Animal"),
            new WordItem("GIRAFFE", "Animal"),
            new WordItem("DOLPHIN", "Animal"),
            new WordItem("PENGUIN", "Animal"),
            new WordItem("CHEETAH", "Animal"),
            new WordItem("GORILLA", "Animal"),
            new WordItem("LEOPARD", "Animal"),
            new WordItem("HAMSTER", "Animal"),
            new WordItem("PANTHER", "Animal"),
            new WordItem("OSTRICH", "Animal"),


            new WordItem("CANADA", "Country"),
            new WordItem("BRAZIL", "Country"),
            new WordItem("FRANCE", "Country"),
            new WordItem("JAPAN", "Country"),
            new WordItem("MEXICO", "Country"),
            new WordItem("EGYPT", "Country"),
            new WordItem("TURKEY", "Country"),
            new WordItem("SWEDEN", "Country"),
            new WordItem("GREECE", "Country"),
            new WordItem("POLAND", "Country"),


            new WordItem("BURGER", "Food & Drinks"),
            new WordItem("MANGO", "Food & Drinks"),
            new WordItem("PIZZA", "Food & Drinks"),
            new WordItem("COFFEE", "Food & Drinks"),
            new WordItem("WAFFLE", "Food & Drinks"),
            new WordItem("SUSHI", "Food & Drinks"),
            new WordItem("PRETZEL", "Food & Drinks"),
            new WordItem("LEMONADE", "Food & Drinks"),
            new WordItem("BROWNIE", "Food & Drinks"),
            new WordItem("NACHOS", "Food & Drinks"),


            new WordItem("KEYBOARD", "Technology"),
            new WordItem("MONITOR", "Technology"),
            new WordItem("ROUTER", "Technology"),
            new WordItem("PYTHON", "Technology"),
            new WordItem("ANDROID", "Technology"),
            new WordItem("SERVER", "Technology"),
            new WordItem("BATTERY", "Technology"),
            new WordItem("BROWSER", "Technology"),
            new WordItem("COMPILER", "Technology"),
            new WordItem("NETWORK", "Technology")
    ));


    public static List<WordItem> getRoundWords() {
        List<WordItem> copy = new ArrayList<>(ALL_WORDS);
        Collections.shuffle(copy);
        return copy.subList(0, 10);
    }
}
