package com.zhangjikai.util;

import java.io.File;

/**
 * Created by Jikai Zhang on 2017/5/8.
 */
public class Path {
    public static String fullPath(String name) {
        //System.out.println(System.getProperty("user.dir"));
        return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +  "resources" + File.separator + name;
        //return new File(Path.class.getClassLoader().getResource(name).getFile()).getAbsolutePath();
    }
}
