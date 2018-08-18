package com.zhangjikao.sample.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jikai Zhang
 * @date 2018-08-13
 */
public class Test {
    public static void main(String[] args) {
        List<? extends  Object> list = new ArrayList<String>();
    }
}

class Favorites{
    private Map<Class<?>, Object> favorites = new HashMap<>();
    
    public <T> void put(Class<T> type, T instance) {
        favorites.put(type, type.cast(instance));
    }
    
    public <T> T get(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}