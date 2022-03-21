package com.neraize.jmfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.*
import com.neraize.jmfirstproject.adapers.MainViewPager2Adapter
import com.neraize.jmfirstproject.databinding.ActivityMainBinding
import com.neraize.jmfirstproject.datas.BasicResponse
import com.neraize.jmfirstproject.datas.CountryData
import com.neraize.jmfirstproject.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var mAdpapter:MainViewPager2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

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

        val database = FirebaseDatabase.getInstance()

//        방법1>
//        val myRef = database.getReference("country").child("1").child("name")

//        방법2>
        val myRef = database.getReference("country")
        val mCountryList = ArrayList<CountryData>()


        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val snapshotSize = snapshot.childrenCount -1

//               방법1>
//                val temp =snapshot.value
//                Log.d("확인1", temp.toString())


//                방법2>
//                val id = snapshot.child("0").child("id").value
//                val name = snapshot.child("0").child("name").value
//                val possibility = snapshot.child("0").child("possibility").value
//                val information = snapshot.child("information").value
//                val latitude = snapshot.child("latitude").value
//                val longitude = snapshot.child("longitude").value
//                Log.d("확인2", "${id.toString()},  ${name.toString()}, ${possibility.toString()}")

//              방법3>
//                mCountryList.addAll(snapshot.value as ArrayList<CountryData>)
//                Log.d("확인3", mCountryList[0].toString())
//                Log.d("확인3", mCountryList[1].toString())

//                방법4>
                for(i in 0 .. snapshotSize){

                    val id = snapshot.child(i.toString()).child("id").value
                    val name = snapshot.child(i.toString()).child("name").value
                    val possibility = snapshot.child(i.toString()).child("possibility").value
                    Log.d("확인2", "${id.toString()},  ${name.toString()}, ${possibility.toString()}")
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }
}