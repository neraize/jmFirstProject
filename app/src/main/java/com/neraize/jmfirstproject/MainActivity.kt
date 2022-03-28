package com.neraize.jmfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.*
import com.neraize.jmfirstproject.adapers.MainViewPager2Adapter
import com.neraize.jmfirstproject.api.FirebaseDbConnect
import com.neraize.jmfirstproject.databinding.ActivityMainBinding
import com.neraize.jmfirstproject.datas.BasicResponse
import com.neraize.jmfirstproject.datas.CountryData
import com.neraize.jmfirstproject.datas.MyAlarmData
import com.neraize.jmfirstproject.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var mAdpapter:MainViewPager2Adapter

    companion object{
        lateinit var mUserIdReplaceDotToStar:String

        var mAlarmList = ArrayList<MyAlarmData>()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

        // 로그인한 유저토큰값가져오기
        apiList.getRequestMyInfo(ContextUtil.getLoginUserToken(mContextt)).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br =response.body()!!

                    // 문자열 변환 test@naver.com -> test@naver*com
                    mUserIdReplaceDotToStar = (br.data.user.email.toString()).replace(".","*")

                    // 아이디가 관리자일때,  관리자 아이콘 보여주기
                    if (MainActivity.mUserIdReplaceDotToStar == "neraize@gmail*com"){
                        Log.d("나의 아디",MainActivity.mUserIdReplaceDotToStar.toString() )
                        txtAdministrator.visibility = View.VISIBLE
                        txtProfile.visibility = View.GONE
                    }

                    // 파이어베이스 디비 연결
                    // 디비중에서, 로그인한 이메일주소 기준으로 알람추가한 국가리스트 찾기
                    mAlarmList = FirebaseDbConnect.getMyAlarmList(mUserIdReplaceDotToStar)
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

        // 바텀네비게이션 메뉴 선택시 -> 뷰페이저의 페이지 이동
        binding.mainBotoomNavigation.setOnItemSelectedListener {

            when(it.itemId){
                R.id.travelMapAll -> binding.mainViewPager2.currentItem=0
                R.id.travelOk -> binding.mainViewPager2.currentItem=1
                R.id.domesticQuarantine -> binding.mainViewPager2.currentItem=2
                R.id.domesticAndForeignQuarantine -> binding.mainViewPager2.currentItem=3
                R.id.prohibitionOfEntry -> binding.mainViewPager2.currentItem=4
            }
            return@setOnItemSelectedListener true
        }
        // 뷰페이지의 페이지 변경시 -> 바텀 네비게이션의 메뉴선택도 변경
        binding.mainViewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){

            //  추상메소드 x -> 이벤트처리 함수를 직접 오버라이딩해야함!
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.mainBotoomNavigation.selectedItemId = when(position){
                    0-> R.id.travelMapAll
                    1-> R.id.travelOk
                    2-> R.id.domesticQuarantine
                    3-> R.id.domesticAndForeignQuarantine
                    else->R.id.prohibitionOfEntry
                }
            }
        })

        //
    }

    override fun SetValues() {

        // 홈이미지 버튼 보이기
        imgHome.visibility = View.VISIBLE
        // 로그아웃 버튼보이기
        txtLogOut.visibility = View.VISIBLE

        apiList.getRequestMyInfo(ContextUtil.getLoginUserToken(mContextt)).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if(response.isSuccessful){

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

        //어댑터연결
        binding.mainViewPager2.adapter = MainViewPager2Adapter(this)

    }
}