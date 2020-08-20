package com.example.cb.test.weight.loadCallBack


import com.example.cb.test.R
import com.kingja.loadsir.callback.Callback


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}