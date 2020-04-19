package com.example.cb.test.mvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cb.test.R

/**
 * @author bin
 */
class MvvmActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_mvvm)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun change(view: View) {
    }
}
