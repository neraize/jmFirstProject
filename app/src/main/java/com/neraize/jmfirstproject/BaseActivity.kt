package com.neraize.jmfirstproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neraize.jmfirstproject.api.APIList
import com.neraize.jmfirstproject.api.ServerAPI

abstract class BaseActivity:AppCompatActivity() {

    lateinit var mContextt:Context

    // 모든화면에서, apiList변수가 있다면 => apiList.서버기능(  )형태로 간단히 코딩 가능
    lateinit var apiList : APIList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContextt = this

        val retrofit = ServerAPI.getRetrofit()
        apiList = retrofit.create(APIList::class.java)
    }

    abstract fun SetupEvents()
    abstract fun SetValues()
}