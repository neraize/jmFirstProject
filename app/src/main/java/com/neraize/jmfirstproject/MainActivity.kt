package com.neraize.jmfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.databinding.ActivityMainBinding
import com.neraize.jmfirstproject.datas.BasicResponse
import com.neraize.jmfirstproject.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        // 홈이미지 버튼 보이기
        imgHome.visibility = View.VISIBLE
        // 로그아웃 버튼보이기
        txtLogOut.visibility = View.VISIBLE

        apiList.getRequestMyInfo(ContextUtil.getLoginUserToken(mContextt)).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if(response.isSuccessful){

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}