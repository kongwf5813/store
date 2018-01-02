package com.patsnap.magic.store.common;

public enum ResponseCode {

    SUCCESS(200,"SUCCESS"),
    ERROR(601,"ERROR"),
    NEED_LOGIN(602,"NEED_LOGIN"),
    INVALID_AUTHORITY(603, "INVALID_AUTHORITY"),
    ILLEGAL_ARGUMENT(604,"ILLEGAL_ARGUMENT");


    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
