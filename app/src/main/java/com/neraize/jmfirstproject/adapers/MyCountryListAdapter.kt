package com.neraize.jmfirstproject.adapers

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.neraize.jmfirstproject.MainActivity
import com.neraize.jmfirstproject.R
import com.neraize.jmfirstproject.datas.CountryData

class MyCountryListAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:ArrayList<CountryData>
):ArrayAdapter<CountryData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow =  convertView
        if(tempRow == null){
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.my_country_list_item, null)
        }

        val row = tempRow!!

        val data =mList[position]

        val txtName = row.findViewById<TextView>(R.id.txtTitle)
        val btnSetAlarm = row.findViewById<Button>(R.id.btnSetAlarm)

        txtName.text = data.name

        when(data.possibility){
            "여행가능"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.blue_bottom))
            "국내격리"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.green_bottom))
            "국내/국외격리"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.yellow2_bottom))
            "입국금지"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red_bottom))
        }

        // 기존 나의 알림리스트 내역 확인하여 화면에 뿌려주기
        Log.d("MyCountryListAdapter사이즈", "${MainActivity.mAlarmList.size.toString()}")

        MainActivity.mAlarmList.forEach { alarm->
            if(data.name == alarm.pushCountry){
                Log.d("MyCountryListAdapter", "알림OK")
                btnSetAlarm.text ="알림OK"
                btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.black_bottom))
                return@forEach
            }
    }



        // 알림신청 버튼클릭 이벤트
        btnSetAlarm.setOnClickListener {

            if(btnSetAlarm.text == "알림신청"){
                btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.black_bottom))
                btnSetAlarm.text ="알림OK"
            }
            else{

                btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.black_bottom))
                btnSetAlarm.text ="알림신청"
            }
        }
        return  row
    }
}