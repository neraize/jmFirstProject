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
import com.neraize.jmfirstproject.api.FirebaseDbConnect
import com.neraize.jmfirstproject.datas.CountryData
import com.neraize.jmfirstproject.datas.MyAlarmData

class MyAlarmListAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:ArrayList<MyAlarmData>
):ArrayAdapter<MyAlarmData>(mContext, resId, mList) {

    var isAlarmSet =false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow =  convertView
        if(tempRow == null){
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.my_alarm_list_item, null)
        }

        val row = tempRow!!

        val data =mList[position]

        val txtName = row.findViewById<TextView>(R.id.txtTitle)
        val txtPossibility = row.findViewById<TextView>(R.id.txtPossibility)
        val btnSetAlarm = row.findViewById<Button>(R.id.btnSetAlarm)

        txtName.text = data.pushCountry
        txtPossibility.text =data.possibility

//        when(data.possibility){
//            "여행가능"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.blue_bottom))
//            "국내격리"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.green_bottom))
//            "국내/국외격리"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.yellow2_bottom))
//            "입국금지"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red_bottom))
//        }

        // 기존 나의 알림리스트 내역 확인하여 화면에 뿌려주기
        //Log.d("MyCountryListAdapter사이즈", "${MainActivity.mAlarmList.size.toString()}")

//        MainActivity.mAlarmList.forEach { alarm->
//            if(data.name == alarm.pushCountry){
//                //Log.d("MyCountryListAdapter", "알림OK")
//                isAlarmSet = true
//                btnSetAlarm.text ="알림ON"
//                btnSetAlarm.tag="true"
//                btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.black_bottom))
//                return@forEach
//            }
//        }

        // 알림신청 버튼클릭 이벤트
//        btnSetAlarm.setOnClickListener {
//
//            Log.d("MyCountryListAdapter","알람클릭전 ${btnSetAlarm.tag}")
//            if(btnSetAlarm.tag =="true"){
//                isAlarmSet=true
//            }
//            else{
//                isAlarmSet=false
//            }
//
//            FirebaseDbConnect.setMyAlarmList(MainActivity.mUserIdReplaceDotToStar, data.name, isAlarmSet)
////            isAlarmSet =!isAlarmSet
//
//            if(btnSetAlarm.tag =="false"){
//                btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.black_bottom))
//                isAlarmSet=true
//                btnSetAlarm.text ="알림ON"
//                btnSetAlarm.tag ="true"
//                Log.d("MyCountryListAdapter1","알림ON")
//            }
//            else{
//                isAlarmSet=false
//                btnSetAlarm.text ="알림OFF"
//                btnSetAlarm.tag ="false"
//                when(data.possibility){
//                    "여행가능"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.blue_bottom))
//                    "국내격리"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.green_bottom))
//                    "국내/국외격리"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.yellow2_bottom))
//                    "입국금지"-> btnSetAlarm.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red_bottom))
//                }
//                Log.d("MyCountryListAdapter","알림OFF")
//            }
//            Log.d("MyCountryListAdapter","알람클릭후 ${btnSetAlarm.tag}")
//        }
        return  row
    }
}