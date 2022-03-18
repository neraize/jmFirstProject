package com.neraize.jmfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

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

        // 임시처리 - 자동로그인 전까지
        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({
            val myIntent = Intent(mContextt, SignInActivity::class.java)
            startActivity(myIntent)
            finish()

        },1000)
    }
}