package com.neraize.jmfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.databinding.ActivityMyProfileBinding

class MyProfileActivity : BaseActivity() {

    lateinit var binding:ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        imgHome.visibility = View.VISIBLE
        txtProfile.visibility =  View.GONE
    }
}