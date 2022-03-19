package com.neraize.jmfirstproject.adapers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neraize.jmfirstproject.fragments.DomesticAndForeignQuarantineFragment
import com.neraize.jmfirstproject.fragments.DomesticQuarantineFragment
import com.neraize.jmfirstproject.fragments.ProhibitionOfEntryFragment
import com.neraize.jmfirstproject.fragments.TravelOkFragment

class MainViewPager2Adapter(fa:FragmentActivity):FragmentStateAdapter(fa) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> TravelOkFragment()
            1-> DomesticQuarantineFragment()
            2-> DomesticAndForeignQuarantineFragment()
            else-> ProhibitionOfEntryFragment()
        }
    }
}
