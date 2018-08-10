package com.zhangjikai.java8.streams;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Jikai Zhang
 * @date 2018-05-01
 */
public class WordCounter {
    private final int counter;
    private final boolean lastSpace;
    
    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }
    
    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new WordCounter(counter, true);
        } else {
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }
    
    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }
    
    public int getCounter() {
        return counter;
    }
    
    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);
        return wordCounter.getCounter();
    }
    
    public static void main(String[] args) {
        String sentence = "11 22 333 44 55 66  77 888888 999 1000001 1010 1100100 1001";
       
        Stream<Character> stream = IntStream.range(0, sentence.length()).mapToObj(sentence::charAt);
        System.out.println(countWords(stream));
        
        
        stream = IntStream.range(0, sentence.length()).mapToObj(sentence::charAt);
        System.out.println(countWords(stream.parallel()));
        
        Spliterator<Character> spliterator = new WordCounterSpliterator(sentence);
        stream = StreamSupport.stream(spliterator, true);
        System.out.println(countWords(stream));
    }
}

/**
 * 自定义在并行流中数据划分的逻辑
 */
class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;
    
    public WordCounterSpliterator(String string) {
        this.string = string;
    }
    
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++));
        return currentChar < string.length();
    }
    
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) {
            return null;
        }
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) {
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }
    
    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }
    
    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
