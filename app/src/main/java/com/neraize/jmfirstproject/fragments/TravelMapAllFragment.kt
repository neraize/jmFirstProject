package com.neraize.jmfirstproject.fragments

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.neraize.jmfirstproject.MainActivity
import com.neraize.jmfirstproject.PopupActivity
import com.neraize.jmfirstproject.R
import com.neraize.jmfirstproject.SplashActivity
import com.neraize.jmfirstproject.databinding.FragmentTravelMapAllBinding
import com.neraize.jmfirstproject.datas.CountryData


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

        // 구글맵 검색
        var addresses: List<Address>? = null

        binding.btnMapSearch.setOnClickListener(View.OnClickListener {

            val edtMapSearch= binding.edtMapSearch.text.toString()
            val geocoder = Geocoder(mContext)

            try {
                    addresses = geocoder.getFromLocationName(edtMapSearch, 3)
                }
            catch (e:Exception){ }

            if (addresses != null) {
                if (addresses!!.size == 0) {
                    Toast.makeText(mContext, "해당되는 국가명이 존재하지않습니다", Toast.LENGTH_SHORT).show()
                }
                else {
                    //카메라 이동
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(addresses!!.get(0)!!.latitude, addresses!!.get(0)!!.longitude)))

                    // 알람신청여부 변수설정
                    var isAlarmSet=false

                    // 해당국가명 리스트에 있을시, 팝업페이지 있을시 띄어주기
                    SplashActivity.mCountryList.forEach { country->
                        if(country.name == edtMapSearch){

                            // 선택한 마커의 국가가 => 나의 기존 알람신청리스트에 있는지 여부확인
                            MainActivity.mAlarmList.forEach { myCountry->
                                if (myCountry.pushCountry == country.name){
                                    isAlarmSet =true
                                    Log.d("TravelMapAllFragment_팝업", "${isAlarmSet.toString()}, mAlarmListSize:${MainActivity.mAlarmList.size}")
                                    return@forEach
                                }
                            }

                            val selectedCountry =CountryData(country.id, country.name, country.possibility, country.information, country.latitude, country.longitude)

                            val myIntent = Intent(mContext as MainActivity, PopupActivity::class.java)
                            myIntent.putExtra("selectedCountry", selectedCountry)
                            myIntent.putExtra("isAlarmSet",isAlarmSet)

                            startActivity(myIntent)
                        }
                    }
                }
            }
        })

        
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        SplashActivity.mCountryList.forEach { country->

            val marker = LatLng(country.latitude, country.longitude)

            when(country.possibility){
                "여행가능" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(country.name))
                "국내격리" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(country.name))
                "국내/국외격리" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(country.name))
                "입국금지" -> mMap.addMarker(MarkerOptions().position(marker).title("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(country.name))
            }
            //Log.d("마커확인", "${ country.latitude }, ${ country.longitude }")
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(35.241615, 128.695587)))

        // 마커선택시 나라별 세부카드 페이지 이동
        mMap.setOnMarkerClickListener(object :GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(marker: Marker): Boolean {

                // 알람신청여부 변수설정
                var isAlarmSet=false

                //
                SplashActivity.mCountryList.forEach { country->
                    if(country.name == marker.title){

                        // 선택한 마커의 국가가 => 나의 기존 알람신청리스트에 있는지 여부확인
                        MainActivity.mAlarmList.forEach { myCountry->
                            if (myCountry.pushCountry == country.name){
                                isAlarmSet =true
                                Log.d("TravelMapAllFragment_팝업", "${isAlarmSet.toString()}, mAlarmListSize:${MainActivity.mAlarmList.size}")
                                return@forEach
                            }
                        }

                        val selectedCountry =CountryData(country.id, country.name, country.possibility, country.information, country.latitude, country.longitude)

                        //Toast.makeText(mContext, marker.title, Toast.LENGTH_SHORT).show()
                        val myIntent = Intent(mContext as MainActivity, PopupActivity::class.java)
                        myIntent.putExtra("selectedCountry", selectedCountry)
                        myIntent.putExtra("isAlarmSet",isAlarmSet)

                        startActivity(myIntent)
                    }
                }

//                //Toast.makeText(mContext, marker.title, Toast.LENGTH_SHORT).show()
//                val myIntent = Intent(mContext as MainActivity, PopupActivity::class.java)
//                myIntent.putExtra("title", marker.title)
//                startActivity(myIntent)
//
                return true
            }
        })


        // 맵 스크롤시, 뷰페이저이동 막기
        binding.txtScrollHelp.setOnTouchListener { view, motionEvent ->

            (mContext as MainActivity).binding.mainViewPager2.isUserInputEnabled = false

            return@setOnTouchListener false
        }
    }
}