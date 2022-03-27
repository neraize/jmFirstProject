package com.neraize.jmfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.adapers.MyAlarmListAdapter
import com.neraize.jmfirstproject.databinding.ActivityMyProfileBinding

class MyProfileActivity : BaseActivity() {

    lateinit var binding:ActivityMyProfileBinding

    lateinit var mAdapter:MyAlarmListAdapter

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

        mAdapter = MyAlarmListAdapter(mContextt, R.layout.my_alarm_list_item, MainActivity.mAlarmList)
        binding.myAlarmListView.adapter = mAdapter

        // 어댑터 새로고침
        //mAdapter.notifyDataSetChanged()
    }
}