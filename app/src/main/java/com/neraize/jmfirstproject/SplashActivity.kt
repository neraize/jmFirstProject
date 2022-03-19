package com.neraize.jmfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.neraize.jmfirstproject.api.ServerAPI
import com.neraize.jmfirstproject.datas.BasicResponse
import com.neraize.jmfirstproject.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        // 1.5초가 지나면-> 자동로그인 여부 확인하여 -> 상황에 맞는 화면으로  이동
        var isTokenOk = false

        apiList.getRequestMyInfo(ContextUtil.getLoginUserToken(mContextt)).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                if(response.isSuccessful){
                    isTokenOk = true
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) { }
        })

        val myHandler = Handler(Looper.getMainLooper())
        myHandler.postDelayed({
            val userAutoLogin = ContextUtil.getAutoLogin(mContextt)

            val myIntent:Intent

            if(userAutoLogin && isTokenOk){
                myIntent = Intent(mContextt, MainActivity::class.java)
//                Toast.makeText(mContextt, "성공", Toast.LENGTH_SHORT).show()
            }
            else{
                myIntent = Intent(mContextt, SignInActivity::class.java)
//                Toast.makeText(mContextt, "실패", Toast.LENGTH_SHORT).show()
            }
            startActivity(myIntent)
            finish()

        },1500)
    }
}