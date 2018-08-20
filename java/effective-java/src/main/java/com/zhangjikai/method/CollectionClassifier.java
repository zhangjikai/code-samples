package com.zhangjikai.method;

import java.util.*;

/**
 * @author Jikai Zhang
 * @date 2018-08-20
 */
public class CollectionClassifier {
    public static String classify(Set<?> s) {
        return "set";
    }
    
    public static String classify(List<?> l) {
        return "list";
    }
    
    public static String classify(Collection<?> c) {
        return "unknown collection";
    }
    
    public static void main(String[] args) {
        Collection<?>[] collections = new Collection[] {
                new HashSet(),
                new ArrayList(),
                new HashMap<>().values()
        };
    
        for (Collection<?> collection : collections) {
            System.out.println(classify(collection));
        }
    }
}
