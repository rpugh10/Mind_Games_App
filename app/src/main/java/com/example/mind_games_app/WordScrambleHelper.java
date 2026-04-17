package com.example.mind_games_app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordScrambleHelper {


    public static String scramble(String word) {
        if (word.length() <= 1) return word;

        List<Character> chars = new ArrayList<>();
        for (char c: word.toCharArray()) {
            chars.add(c);
        }

        String scrambled;
        int attempts = 0;
        do {
            Collections.shuffle(chars);
            StringBuilder sb = new StringBuilder();
            for (char c: chars) sb.append(c);
            scrambled = sb.toString();
            attempts++;

        } while (scrambled.equals(word) && attempts < 20);

        return scrambled;
    }


    public static boolean isCorrect(String userAnswer, String actualWord) {
        return userAnswer.trim().equalsIgnoreCase(actualWord);
    }
}
