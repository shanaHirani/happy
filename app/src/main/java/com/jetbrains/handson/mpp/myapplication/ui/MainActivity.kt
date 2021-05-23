package com.jetbrains.handson.mpp.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.jetbrains.handson.mpp.myapplication.R
import com.jetbrains.handson.mpp.myapplication.databinding.ActivityMainBinding

///test1////
///test7////

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main)
    }
}