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
import com.neraize.jmfirstproject.MyProfileActivity
import com.neraize.jmfirstproject.R
import com.neraize.jmfirstproject.api.FirebaseDbConnect
import com.neraize.jmfirstproject.datas.CountryData
import com.neraize.jmfirstproject.datas.MyAlarmData

class MyAlarmListAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:ArrayList<MyAlarmData>
):ArrayAdapter<MyAlarmData>(mContext, resId, mList) {

    var isAlarmSet =true

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


        // 알림신청 버튼클릭 이벤트
        btnSetAlarm.setOnClickListener {

            FirebaseDbConnect.setMyAlarmList(MainActivity.mUserIdReplaceDotToStar, txtName.text.toString(), txtPossibility.text.toString(), isAlarmSet)

            btnSetAlarm.text ="알림OFF"
            btnSetAlarm.tag ="false"

//            it.visibility=View.GONE
//            txtName.visibility =View.GONE
//            txtPossibility.visibility=View.GONE
            Log.d("MyCountryListAdapter","알림OFF")
        }
        return  row
    }

}