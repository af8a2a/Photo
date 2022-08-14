package com.example.photo.Entity;

public class Token {
    private int code;
    private String msg;
    private String[] data;
    private int time;

    public Token(int code, String msg, String[] data, int time) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
