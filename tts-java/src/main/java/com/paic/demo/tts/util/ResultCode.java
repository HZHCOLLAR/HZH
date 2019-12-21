package com.paic.demo.tts.util;

public enum  ResultCode {
    SUCCESS("00", "成功"),
    EXCEPTION("01", "异常");

    private String code;

    private String msg;

    ResultCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
