package com.neraize.jmfirstproject.adapers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neraize.jmfirstproject.fragments.*

class MainViewPager2Adapter(fa:FragmentActivity):FragmentStateAdapter(fa) {
    override fun getItemCount() = 5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> TravelMapAllFragment()
            1-> TravelOkFragment()
            2-> DomesticQuarantineFragment()
            3-> DomesticAndForeignQuarantineFragment()
            else-> ProhibitionOfEntryFragment()
        }
    }
}
