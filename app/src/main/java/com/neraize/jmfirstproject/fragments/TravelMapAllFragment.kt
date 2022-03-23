package com.neraize.jmfirstproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.neraize.jmfirstproject.MainActivity
import com.neraize.jmfirstproject.R
import com.neraize.jmfirstproject.SplashActivity
import com.neraize.jmfirstproject.databinding.FragmentTravelMapAllBinding
import retrofit2.http.Url

class TravelMapAllFragment:BaseFragment(), OnMapReadyCallback{

    lateinit var binding:FragmentTravelMapAllBinding

    private lateinit var mMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel_map_all, container, false)

        //구글맵 실행
        binding.map.onCreate(savedInstanceState);
        binding.map.onResume();
        binding.map.getMapAsync(this);

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

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        SplashActivity.mCountryList.forEach { country->

            val marker = LatLng(country.latitude, country.longitude)

            when(country.possibility){
                "여행가능" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                "국내격리" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                "국내/국외격리" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                "입국금지" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
            }
            //Log.d("마커확인", "${ country.latitude }, ${ country.longitude }")
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(35.241615, 128.695587)))

        // 맵 스크롤시, 뷰페이저이동 막기
        binding.txtScrollHelp.setOnTouchListener { view, motionEvent ->

            (mContext as MainActivity).binding.mainViewPager2.isUserInputEnabled = false

            return@setOnTouchListener false
        }
    }
}