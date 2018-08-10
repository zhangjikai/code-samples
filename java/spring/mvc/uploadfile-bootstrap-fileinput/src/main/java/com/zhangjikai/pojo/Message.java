package com.zhangjikai.pojo;

import java.util.ArrayList;

/**
 * Created by zhangjk on 2016/1/15.
 */
public class Message {
    private Status status;
    private String statusMsg = "";
    private ArrayList<Integer> errorKys;
    private String error = "";

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public ArrayList<Integer> getErrorKys() {
        return errorKys;
    }

    public void setErrorKys(ArrayList<Integer> errorKys) {
        this.errorKys = errorKys;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}




