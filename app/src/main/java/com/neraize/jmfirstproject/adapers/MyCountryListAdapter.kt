package com.neraize.jmfirstproject.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
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

        txtName.text = data.name

        return  row
    }
}