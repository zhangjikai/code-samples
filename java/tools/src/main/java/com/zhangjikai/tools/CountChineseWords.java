package com.zhangjikai.tools;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 统计文件里中文字符的个数
 *
 * @author Jikai Zhang
 * @date 2018-01-22
 */
@Slf4j
public class CountChineseWords {
    
    public static int count(String filePath) {
        int total = 0;
        String ignoreLineTag = "%";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    if (line.startsWith(ignoreLineTag)) {
                        continue;
                    }
                    if (isChineseChar(c) || isChinesePunctuation(c)) {
                        total++;
                    }
                }
            }
            
        } catch (IOException e) {
            log.error("", e);
        }
        return total;
    }
    
    /**
     * 判断是否为中文字符
     *
     * @param c 要判断的字符
     * @return true: 中文字符 <br> false: 非中文字符
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        return sc == Character.UnicodeScript.HAN;
    }
    
    /**
     * 判断是否为中文标点
     *
     * @param c 要判断的字符
     * @return true：中文标点 <br> false: 非中文标点
     */
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS;
    }
}
