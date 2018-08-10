package com.zhangjikai.utils;

import info.monitorenter.cpdetector.io.*;

import java.io.File;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 可以在下面的链接下载jar包
 * http://download.csdn.net/detail/zhangjk1993/5690601
 *
 * Created by zhangjk on 2016/1/19.
 */
public class Cpdetector {


    /**
     * 使用Cpdetector检测文件编码
     *
     * @param file
     * @return
     */
    public static Charset getFileEncode(File file) {
        try {
            CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
            detector.add(new ParsingDetector(false));
            detector.add(JChardetFacade.getInstance());
            detector.add(ASCIIDetector.getInstance());
            detector.add(UnicodeDetector.getInstance());
            java.nio.charset.Charset charset = null;
            charset = detector.detectCodepage(file.toURI().toURL());
            return charset;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}

