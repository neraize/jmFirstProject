package com.neraize.jmfirstproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neraize.jmfirstproject.api.APIList
import com.neraize.jmfirstproject.api.ServerAPI

abstract class BaseActivity:AppCompatActivity() {

    lateinit var mContextt:Context

    // 모든화면에서, apiList변수가 있다면 => apiList.서버기능(  )형태로 간단히 코딩 가능
    lateinit var apiList : APIList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContextt = this

        if(supportActionBar !=null){
            setCustomActionBar()
        }

        val retrofit = ServerAPI.getRetrofit()
        apiList = retrofit.create(APIList::class.java)
    }

    abstract fun SetupEvents()
    abstract fun SetValues()

    fun setCustomActionBar(){
        // 커스텀 액션바
        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

        // 액션바 좌우 여백 제거
        val toolbar = defaultActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0,0)
    }
}