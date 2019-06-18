package com.example.cb.test.bean;

/**
 * 描述：
 * 作者：曹斌
 * date:2018/3/12 09:27
 */
public class UploadBean {


    /**
     * code : 0
     * msg : 成功
     * data : 9540K95HT3KV
     */

    private String code;
    private String msg;
    private String data;

    public UploadBean() {
    }

    public UploadBean(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
