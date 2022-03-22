package com.neraize.jmfirstproject.fragments

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.neraize.jmfirstproject.MainActivity
import com.neraize.jmfirstproject.api.APIList
import com.neraize.jmfirstproject.api.ServerAPI

abstract class BaseFragment:Fragment() {

    lateinit var mContext:Context

    // 모든화면에서, apiList변수가 있다면 => apiList.서버기능(  )형태로 간단히 코딩 가능
    lateinit var apiList:APIList

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mContext = requireContext() // 모든화면정보 -> mContext로 대신 활용

        val retrofit = ServerAPI.getRetrofit()
        apiList = retrofit.create(APIList::class.java)

        (mContext as MainActivity).binding.mainViewPager2.isUserInputEnabled = true
    }

    abstract fun SetupEvents()
    abstract fun SetValues()
}