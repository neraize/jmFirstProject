package com.neraize.jmfirstproject

import android.os.Bundle
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.databinding.ActivityPopupBinding

class PopupActivity : BaseActivity() {

    lateinit var binding:ActivityPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //타이틀바 없애기??


        binding = DataBindingUtil.setContentView(this, R.layout.activity_popup)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        val title = intent.getStringExtra("title")
        binding.txtTitle.text = title
    }
}