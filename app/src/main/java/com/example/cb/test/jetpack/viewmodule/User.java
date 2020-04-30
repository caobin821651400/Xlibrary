package com.example.cb.test.jetpack.viewmodule;

import androidx.databinding.BaseObservable;

import com.example.cb.test.BR;
//import androidx.databinding.library.baseAdapters.BR;


public class User extends BaseObservable {

    private String name;
    private String passward;

    public User(String name, String passward) {
        this.name = name;
        this.passward = passward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
//        notifyPropertyChanged(BR.name);
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
//        notifyPropertyChanged(BR.passward);
    }
}
