package com.self.ddyoung.rice.model;

public class ResultMsg {
    private int status;
    private Object data;
    private String msg;

    public ResultMsg() {
    }

    public ResultMsg(Object data) {
        status = 200;
        this.data = data;
    }

    public ResultMsg(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
