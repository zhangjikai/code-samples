package com.zhangjikai.utils;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 获得电脑的ip地址, 可能获得多个
 * Created by zhangjk on 2016/1/19.
 */
public class FtUtils {

    public static List<String> getIps() {

        List<String> ipList = new ArrayList<>();
        Enumeration nets = null;
        try {
            nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : (List<NetworkInterface>) Collections.list(nets)) {
                Enumeration inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : (List<InetAddress>) Collections.list(inetAddresses)) {
                    if (inetAddress instanceof Inet4Address)
                       ipList.add(inetAddress.getHostAddress());
                }

            }
        } catch (SocketException e) {
            ipList.add("127.0.0.1");
            e.printStackTrace();
        }

        for(String s : ipList) {
            System.out.println(s);
        }
        return ipList;
    }


    public static void main(String[] args) {
        getIps();
    }

}