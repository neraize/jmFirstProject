package com.neraize.jmfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.neraize.jmfirstproject.api.ServerAPI
import com.neraize.jmfirstproject.datas.BasicResponse
import com.neraize.jmfirstproject.datas.CountryData
import com.neraize.jmfirstproject.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {

    companion object{
        val mCountryList = ArrayList<CountryData>()
        val mCountryTravelOKList = ArrayList<CountryData>()
        val mCountryDomesticList = ArrayList<CountryData>()
        val mCountryDomesticAndForeignList = ArrayList<CountryData>()
        val mCountryProhibitionList = ArrayList<CountryData>()


        fun getFireBaseDB(){

            mCountryList.clear()

            // 파이어 베이스 디비연결
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("country")

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val snapshotSize = snapshot.childrenCount-1
                    Log.d("국가수", snapshotSize.toString())
                    for(i in 0 .. snapshotSize){

                        val id = (snapshot.child(i.toString()).child("id").value).toString().toInt()
                        val name = (snapshot.child(i.toString()).child("name").value).toString()
                        val possibility = (snapshot.child(i.toString()).child("possibility").value).toString()
                        val information = (snapshot.child(i.toString()).child("information").value).toString()
                        val latitude = (snapshot.child(i.toString()).child("latitude").value).toString().toDouble()
                        val longitude = (snapshot.child(i.toString()).child("longitude").value).toString().toDouble()

                        mCountryList.addAll(listOf(CountryData(id, name, possibility, information, latitude, longitude)))
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        // 1.5초가 지나면-> 자동로그인 여부 확인하여 -> 상황에 맞는 화면으로  이동
        var isTokenOk = false

        apiList.getRequestMyInfo(ContextUtil.getLoginUserToken(mContextt)).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                if(response.isSuccessful){
                    isTokenOk = true
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) { }
        })

        val myHandler = Handler(Looper.getMainLooper())
        myHandler.postDelayed({
            val userAutoLogin = ContextUtil.getAutoLogin(mContextt)

            val myIntent:Intent

            if(userAutoLogin && isTokenOk){
                myIntent = Intent(mContextt, MainActivity::class.java)
//                Toast.makeText(mContextt, "성공", Toast.LENGTH_SHORT).show()
            }
            else{
                myIntent = Intent(mContextt, SignInActivity::class.java)
//                Toast.makeText(mContextt, "실패", Toast.LENGTH_SHORT).show()
            }
            startActivity(myIntent)
            finish()

        },1500)

        setFireBaseDB()
    }

    fun setFireBaseDB(){
        // 파이어 베이스 디비연결
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("country")

//        val mCountryList = ArrayList<CountryData>()
//        val mCountryTravelOKList = ArrayList<CountryData>()
//        val mCountryDomesticList = ArrayList<CountryData>()
//        val mCountryDomesticAndForeignList = ArrayList<CountryData>()
//        val mCountryProhibitionList = ArrayList<CountryData>()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val snapshotSize = snapshot.childrenCount -1
                Log.d("국가수", snapshotSize.toString())
                for(i in 0 .. snapshotSize){

                    val id = (snapshot.child(i.toString()).child("id").value).toString().toInt()
                    val name = (snapshot.child(i.toString()).child("name").value).toString()
                    val possibility = (snapshot.child(i.toString()).child("possibility").value).toString()
                    val information = (snapshot.child(i.toString()).child("information").value).toString()
                    val latitude = (snapshot.child(i.toString()).child("latitude").value).toString().toDouble()
                    val longitude = (snapshot.child(i.toString()).child("longitude").value).toString().toDouble()

                    mCountryList.addAll(listOf(CountryData(id, name, possibility, information, latitude, longitude)))
//                    mCountryList.add(i.toInt(), CountryData(id, name, possibility, information, latitude, longitude))
//                    Log.d("확인2", "${id.toString()}, ${name.toString()}, ${possibility.toString()}" +
//                            " ,${information.toString()}, ${latitude.toString()}, ${longitude.toString()} +")

                    when(possibility){
                        "여행가능"-> mCountryTravelOKList .addAll(listOf(CountryData(id, name, possibility, information, latitude, longitude)))
                        "국내격리"-> mCountryDomesticList .addAll(listOf(CountryData(id, name, possibility, information, latitude, longitude)))
                        "국내/국외격리"-> mCountryDomesticAndForeignList .addAll(listOf(CountryData(id, name, possibility, information, latitude, longitude)))
                        "입국금지"-> mCountryProhibitionList .addAll(listOf(CountryData(id, name, possibility, information, latitude, longitude)))
                    }
                }

//                mCountryList.forEach { i->
//                    i
//                    Log.d("모두보기", "${i.id}, ${i.name}, ${i.possibility}, ${i.information}, ${i.latitude}, ${i.longitude} ")
//                }
//                mCountryDomesticList.forEach { i->
//                    i
//                    Log.d("국내격리", "${i.id}, ${i.name}, ${i.possibility}, ${i.information}, ${i.latitude}, ${i.longitude} ")
//                }
//                mCountryDomesticAndForeignList.forEach { i->
//                    i
//                    Log.d("국내/국외격리", "${i.id}, ${i.name}, ${i.possibility}, ${i.information}, ${i.latitude}, ${i.longitude} ")
//                }
//                mCountryProhibitionList.forEach { i->
//                    i
//                    Log.d("입국금지", "${i.id}, ${i.name}, ${i.possibility}, ${i.information}, ${i.latitude}, ${i.longitude} ")
//                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}