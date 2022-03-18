package com.neraize.jmfirstproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity:AppCompatActivity() {

    lateinit var mContextt:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContextt = this
    }

    abstract fun SetupEvents()
    abstract fun SetValues()
}