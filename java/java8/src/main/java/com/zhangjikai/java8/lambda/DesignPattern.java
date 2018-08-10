package com.zhangjikai.java8.lambda;

/**
 * 实现策略模式
 *
 * @author Jikai Zhang
 * @date 2018-05-01
 */
public class DesignPattern {

}

/**
 * 策略模式
 */
class Validator {
    private final ValidationStrategy strategy;
    
    public Validator(ValidationStrategy v) {
        strategy = v;
    }
    
    public boolean validate(String s) {
        return strategy.execute(s);
    }
    
    public static void main(String[] args) {
        Validator numericValidator = new Validator(s -> s.matches("\\d+"));
        System.out.println(numericValidator.validate("aaaa"));
        Validator lowerCaseValidator = new Validator(s -> s.matches("[a-z]+"));
        System.out.println(lowerCaseValidator.validate("bbbb"));
        
    }
}

interface ValidationStrategy {
    boolean execute(String s);
}