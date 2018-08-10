package com.zhangjikai.binlog;

import java.io.IOException;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
 * @author zhangjikai
 * Created on 2018-07-20
 */
public class BinlogTest {
    public static void main(String[] args) throws IOException {
        System.out.println(333);
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "testuser", "testuser");
        client.registerEventListener(event -> {
            EventData data = event.getData();
            System.out.println(data);
            if(data instanceof QueryEventData) {
                System.out.println(((QueryEventData) data).getSql());
            } else if(data instanceof TableMapEventData) {
                System.out.println("table: " + ((TableMapEventData) data).getTable());
            } else if(data instanceof UpdateRowsEventData) {
                System.out.println("update: " + data.toString());
            } else if(data instanceof WriteRowsEventData) {
                System.out.println("insert: " + data.toString());
            } else if(data instanceof DeleteRowsEventData) {
                System.out.println("delete: " + data.toString());
            }
        });
        client.connect();
    }
}
