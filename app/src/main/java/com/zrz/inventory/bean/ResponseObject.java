package com.zrz.inventory.bean;

import java.io.Serializable;
import java.util.List;

public class ResponseObject implements Serializable {
    private String code;
    private String message;

    private List<Response> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Response> getList() {
        return list;
    }

    public void setList(List<Response> list) {
        this.list = list;
    }
}
