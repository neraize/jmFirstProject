package com.neraize.jmfirstproject.adapers

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.neraize.jmfirstproject.*
import com.neraize.jmfirstproject.api.FirebaseDbConnect
import com.neraize.jmfirstproject.datas.CountryData
import com.neraize.jmfirstproject.datas.MyAlarmData

class MyAdminListAdapter(
    val mContext:Context,
    val resId:Int,
    val mList: ArrayList<CountryData>
):ArrayAdapter<CountryData>(mContext, resId, mList) {

    var isAlarmSet =true

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow =  convertView
        if(tempRow == null){
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.my_admin_list_item, null)
        }

        val row = tempRow!!

        val data =mList[position]

        val txtName = row.findViewById<TextView>(R.id.txtTitle)
        val txtPossibility = row.findViewById<TextView>(R.id.txtPossibility)
        val btnSave = row.findViewById<Button>(R.id.btnSave)

        txtName.text = data.name
        txtPossibility.text =data.possibility


        txtName.setOnClickListener {

            AdministratorActivity.txtName.text = data.name
            AdministratorActivity.txtName.setTextColor(ContextCompat.getColor(mContext,R.color.red_bottom))
        }


        AdministratorActivity.btnSave.setOnClickListener {

            if(AdministratorActivity.txtName.text != "국가명"){
                FirebaseDbConnect.setUpdateMyCountry(AdministratorActivity.txtName.text.toString(), AdministratorActivity.spnPossibility)
                Toast.makeText(mContext, "${AdministratorActivity.txtName.text} ${AdministratorActivity.spnPossibility}으로 변경완료", Toast.LENGTH_SHORT).show()

//                mList.remove(data)
//                mList.clear()
//                mList.addAll(SplashActivity.mCountryList)
//                notifyDataSetChanged()

                val myHandler = Handler(Looper.getMainLooper())
                myHandler.postDelayed({
                    notifyDataSetChanged()
                    Log.d("MyAdminListAdapter","1초딜레이")
                    Log.d("MyAdminListAdapter","mList사이즈: ${mList.size.toString()}")
                    Log.d("MyAdminListAdapter","mCountryList사이즈: ${SplashActivity.mCountryList.size}")
                },1000)

            }
            else{
                Toast.makeText(mContext, "변경할 국가를 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        return  row
    }
}