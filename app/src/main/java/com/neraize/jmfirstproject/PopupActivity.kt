package com.neraize.jmfirstproject

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.api.FirebaseDbConnect
import com.neraize.jmfirstproject.databinding.ActivityPopupBinding
import com.neraize.jmfirstproject.datas.CountryData

class PopupActivity : BaseActivity() {

    lateinit var binding:ActivityPopupBinding

    val mUserIdReplaceDotToStar = MainActivity.mUserIdReplaceDotToStar

    var isAlarmSet=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_popup)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        // 마커 선택된 나라의 정보 -> 팝업창에 뿌려주기
        isAlarmSet = intent.getBooleanExtra("isAlarmSet", false)

        Log.d("PopupActivity_팝업체크유무", isAlarmSet.toString())
        if(isAlarmSet){
            binding.imgAlarBell.setImageResource(R.drawable.notification_ring_red_icon)
        }
        else{
            binding.imgAlarBell.setImageResource(R.drawable.notification_ring_empty_icon)
        }

        val selectedCountry = intent.getSerializableExtra("selectedCountry") as CountryData
        binding.txtTitle.text = selectedCountry.name
        binding.txtPossibility.text = selectedCountry.possibility
//        binding.txtInfomation.text = selectedCountry.information

        when(selectedCountry.possibility){
            "여행가능" -> binding.txtTitle.setBackgroundResource(R.color.blue_bottom)
            "국내격리" -> binding.txtTitle.setBackgroundResource(R.color.green_bottom)
            "국내/국외격리" -> binding.txtTitle.setBackgroundResource(R.color.yellow2_bottom)
            "입국금지" -> binding.txtTitle.setBackgroundResource(R.color.red_bottom)
        }

        //타이틀바 없애기
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setTitle(null)

        // 창닫기
        binding.txtClosePopup.setOnClickListener {
            finish()
        }


        // 알람 온오프
        binding.imgAlarBell.setOnClickListener {

            FirebaseDbConnect.setMyAlarmList(mUserIdReplaceDotToStar,selectedCountry.name, selectedCountry.possibility, isAlarmSet)
            isAlarmSet =!isAlarmSet
            if(isAlarmSet){
                isAlarmSet =true
                binding.imgAlarBell.setImageResource(R.drawable.notification_ring_red_icon)
            }
            else{
                isAlarmSet =false
                binding.imgAlarBell.setImageResource(R.drawable.notification_ring_empty_icon)
            }
        }
        
    }
}