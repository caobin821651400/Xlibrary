package com.cb.xlibrary.bean;

import java.io.Serializable;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/18
 * desc   :
 */
public class BottomPopupBean implements Serializable {
    private static final long serialVersionUID = -2343880474044382518L;

    private String title;//显示文字
    private Object value;//

    public BottomPopupBean() {
    }

    public BottomPopupBean(String title, Object value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
