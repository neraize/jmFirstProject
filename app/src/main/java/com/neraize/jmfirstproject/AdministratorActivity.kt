package com.neraize.jmfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.databinding.ActivityAdministratorBinding

class AdministratorActivity : BaseActivity() {

    lateinit var binding:ActivityAdministratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_administrator)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        txtProfile.visibility = View.GONE
        txtProfileOut.visibility = View.VISIBLE

        // 관리자 화면 나가기
        txtProfileOut.setOnClickListener {
            finish()
        }
    }
}