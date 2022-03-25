package com.neraize.jmfirstproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.R
import com.neraize.jmfirstproject.SplashActivity
import com.neraize.jmfirstproject.adapers.MyCountryListAdapter
import com.neraize.jmfirstproject.databinding.FragmentDomesticAndForeignQuarantineBinding

class DomesticAndForeignQuarantineFragment:BaseFragment() {

    lateinit var binding:FragmentDomesticAndForeignQuarantineBinding

    lateinit var mAdapter: MyCountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_domestic_and_foreign_quarantine, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        mAdapter = MyCountryListAdapter(mContext, R.layout.my_country_list_item, SplashActivity.mCountryDomesticAndForeignList)
        binding.myCountryListView.adapter = mAdapter

        // 어댑터 새로고침
        //mAdapter.notifyDataSetChanged()
    }
}