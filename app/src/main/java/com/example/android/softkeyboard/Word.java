package com.example.android.softkeyboard;

import java.util.Comparator;

public class Word{
    String str;
    Integer frequency;

    public Word(String str, int frequency) {
        this.str = str;
        this.frequency = frequency;
    }

    public static class WordFrequencyComparator implements Comparator<Word> {

        @Override
        public int compare(Word word1, Word word2) {
            return ((word2.frequency).compareTo(word1.frequency));
        }
    }
}
